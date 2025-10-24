package org.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student extends AbstractPerson {
    @Column(nullable = false, length = 10)
    private String code;

    @Column(nullable = false)
    private int year;

    public Student() {}
    public Student(String code, String name, String email, int year) {
        this.code = code; this.name = name; this.email = email; this.year = year;
    }

    public String getCode() { return code; }
    public int getYear() { return year; }
    public void setCode(String code) { this.code = code; }
    public void setYear(int year) { this.year = year; }

    @Override public String toString() {
        return "Student{id=" + id + ", code='" + code + "', name='" + name + "', email='" + email + "', year=" + year + "}";
    }
}
