package org.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentCode;
    private String courseCode;
    private Double value;

    public Grade() {}

    public Grade(String studentCode, String courseCode, Double value) {
        this.studentCode = studentCode;
        this.courseCode = courseCode;
        this.value = value;
    }

    public Long getId() { return id; }
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
}