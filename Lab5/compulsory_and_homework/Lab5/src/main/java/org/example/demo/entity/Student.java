package org.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "students")
public class Student extends AbstractPerson {

    @Column(nullable = false, length = 10)
    private String code;

    @Column(nullable = false)
    private int year;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Enrollment> enrollments = new ArrayList<>();

    @Version
    private Long version;

    public Student() {}

    public Student(String code, String name, String email, int year) {
        this.code = code;
        this.name = name;
        this.email = email;
        this.year = year;
    }

    public String getCode() { return code; }

    public int getYear() { return year; }

    public List<Enrollment> getEnrollments() { return enrollments; }

    public Long getVersion() { return version; }

    public void setCode(String code) { this.code = code; }

    public void setYear(int year) { this.year = year; }

    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }

    public void setVersion(Long version) { this.version = version; }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", year=" + year +
                ", version=" + version +
                '}';
    }
}
