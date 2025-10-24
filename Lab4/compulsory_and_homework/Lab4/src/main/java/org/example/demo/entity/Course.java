package org.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    public enum CourseType { compulsory, optional }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CourseType type;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(length = 20)
    private String abbr;

    @Column(nullable = false, length = 150)
    private String name;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    private Pack pack; // null pentru compulsory

    private Integer group_count;

    @Column(columnDefinition = "text")
    private String description;

    public Course() {}
    public Course(CourseType type, String code, String abbr, String name, Instructor instructor, Pack pack, Integer groupCount, String description) {
        this.type = type; this.code = code; this.abbr = abbr; this.name = name;
        this.instructor = instructor; this.pack = pack; this.group_count = groupCount; this.description = description;
    }

    public Long getId() { return id; }
    public CourseType getType() { return type; }
    public String getCode() { return code; }
    public String getAbbr() { return abbr; }
    public String getName() { return name; }
    public Instructor getInstructor() { return instructor; }
    public Pack getPack() { return pack; }
    public Integer getGroup_count() { return group_count; }
    public String getDescription() { return description; }

    public void setType(CourseType type) { this.type = type; }
    public void setCode(String code) { this.code = code; }
    public void setAbbr(String abbr) { this.abbr = abbr; }
    public void setName(String name) { this.name = name; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }
    public void setPack(Pack pack) { this.pack = pack; }
    public void setGroup_count(Integer group_count) { this.group_count = group_count; }
    public void setDescription(String description) { this.description = description; }
}
