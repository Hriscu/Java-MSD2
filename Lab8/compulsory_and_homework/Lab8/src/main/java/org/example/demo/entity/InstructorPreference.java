package org.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "instructor_preferences")
public class InstructorPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "optional_course_id")
    private Course optionalCourse;

    private String compulsoryAbbr;
    private Double weight;

    public InstructorPreference() {}

    public InstructorPreference(Course optionalCourse, String compulsoryAbbr, Double weight) {
        this.optionalCourse = optionalCourse;
        this.compulsoryAbbr = compulsoryAbbr;
        this.weight = weight;
    }

    public Long getId() { return id; }
    public Course getOptionalCourse() { return optionalCourse; }
    public void setOptionalCourse(Course optionalCourse) { this.optionalCourse = optionalCourse; }
    public String getCompulsoryAbbr() { return compulsoryAbbr; }
    public void setCompulsoryAbbr(String compulsoryAbbr) { this.compulsoryAbbr = compulsoryAbbr; }
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
}