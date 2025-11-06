package org.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.demo.dto.PreferenceDTO;
import org.example.demo.entity.Preference;
import org.example.demo.service.PreferenceService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    private final PreferenceService service;

    public PreferenceController(PreferenceService service) {
        this.service = service;
    }

    @Operation(summary = "Get ordered preferences for a student and a pack")
    @GetMapping("/{studentId}/{packId}")
    public List<Preference> list(@PathVariable Long studentId, @PathVariable Long packId){
        return service.getPreferences(studentId, packId);
    }

    @Operation(summary = "Create or update one preference")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Preference> save(@Valid @RequestBody PreferenceDTO dto){
        Preference pref = service.saveOrUpdate(dto.getStudentId(), dto.getPackId(), dto.getCourseId(), dto.getRank());
        return ResponseEntity.ok(pref);
    }

    @Operation(summary = "Delete all preferences for a student in one pack")
    @DeleteMapping("/{studentId}/{packId}")
    public ResponseEntity<?> delete(@PathVariable Long studentId, @PathVariable Long packId){
        service.deleteAllForPack(studentId, packId);
        return ResponseEntity.noContent().build();
    }
}
