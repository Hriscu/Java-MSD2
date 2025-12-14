package org.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "instructor_preferences") // Sau instructor_preference
public class InstructorPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "optional_course_id")
    private Course optionalCourse;

    private String compulsoryCourseAbbr;

    private Double weight;

    public InstructorPreference() {}

    public InstructorPreference(Course optionalCourse, String compulsoryCourseAbbr, Double weight) {
        this.optionalCourse = optionalCourse;
        this.compulsoryCourseAbbr = compulsoryCourseAbbr;
        this.weight = weight;
    }

    public Long getId() { return id; }

    public Course getOptionalCourse() { return optionalCourse; }
    public void setOptionalCourse(Course optionalCourse) { this.optionalCourse = optionalCourse; }

    public String getCompulsoryCourseAbbr() { return compulsoryCourseAbbr; }
    public void setCompulsoryCourseAbbr(String compulsoryCourseAbbr) { this.compulsoryCourseAbbr = compulsoryCourseAbbr; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
}