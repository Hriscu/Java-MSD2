package org.example.demo.mapper;

import org.example.demo.dto.StudentDTO;
import org.example.demo.entity.Student;

public class StudentMapper {

    public static StudentDTO toDTO(Student s) {
        return new StudentDTO(s.getCode(), s.getName(), s.getEmail(), s.getYear());
    }

    public static Student toEntity(StudentDTO dto) {
        return new Student(dto.getCode(), dto.getName(), dto.getEmail(), dto.getYear());
    }

    public static void copyToEntity(StudentDTO dto, Student s) {
        s.setCode(dto.getCode());
        s.setName(dto.getName());
        s.setEmail(dto.getEmail());
        s.setYear(dto.getYear());
    }
}
