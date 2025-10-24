package org.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "packs")
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;
    private int semester;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

    public Pack() {}
    public Pack(int year, int semester, String name) {
        this.year = year; this.semester = semester; this.name = name;
    }

    public Long getId() { return id; }
    public int getYear() { return year; }
    public int getSemester() { return semester; }
    public String getName() { return name; }
    public List<Course> getCourses() { return courses; }
    public void setYear(int year) { this.year = year; }
    public void setSemester(int semester) { this.semester = semester; }
    public void setName(String name) { this.name = name; }
}
