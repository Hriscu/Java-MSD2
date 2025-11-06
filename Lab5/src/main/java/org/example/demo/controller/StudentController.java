package org.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.demo.dto.StudentDTO;
import org.example.demo.entity.Student;
import org.example.demo.exception.ResourceNotFoundException;
import org.example.demo.mapper.StudentMapper;
import org.example.demo.service.StudentService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService service;
    public StudentController(StudentService service) { this.service = service; }

    // ETag helper
    private static String etagOf(Student s){
        long v = (s.getVersion() == null ? 0L : s.getVersion());
        return "W/\"student-" + s.getId() + "-" + v + "\"";
    }

    @Operation(summary = "List all students (JSON or XML)")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<StudentDTO> all() {
        return service.findAll().stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get one student by id with ETag (supports If-None-Match)")
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<StudentDTO> one(@PathVariable Long id,
                                          @RequestHeader(value = "If-None-Match", required = false) String inm) {

        Student s = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student " + id + " not found"));

        String etag = etagOf(s);

        if (inm != null && inm.equals(etag)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(etag).build();
        }

        return ResponseEntity.ok().eTag(etag).body(StudentMapper.toDTO(s));
    }

    @Operation(summary = "Create a new student")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody StudentDTO dto){
        Student s = StudentMapper.toEntity(dto);
        s = service.save(s);
        return ResponseEntity.created(URI.create("/api/students/" + s.getId()))
                .eTag(etagOf(s))
                .body(StudentMapper.toDTO(s));
    }

    @Operation(summary = "Update a student (full replace)")
    @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody StudentDTO dto,
                                    @RequestHeader(value="If-Match", required=false) String ifMatch){

        Student s = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student " + id + " not found"));

        String current = etagOf(s);

        if (ifMatch != null && !ifMatch.equals(current)) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(Collections.singletonMap("error", "ETag mismatch (use latest ETag)"));
        }

        StudentMapper.copyToEntity(dto, s);
        s = service.save(s);

        return ResponseEntity.ok().eTag(etagOf(s)).body(StudentMapper.toDTO(s));
    }

    @Operation(summary = "Delete a student")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Student> opt = service.findById(id);
        if (!opt.isPresent()) throw new ResourceNotFoundException("Student " + id + " not found");
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
