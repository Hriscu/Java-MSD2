package org.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "preferences",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "pack_id", "course_id"}))
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pack_id")
    private Pack pack;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(nullable = false)
    private Integer rank; // the preference order

    public Preference() {}

    public Preference(Student student, Pack pack, Course course, Integer rank) {
        this.student = student;
        this.pack = pack;
        this.course = course;
        this.rank = rank;
    }

    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public Pack getPack() { return pack; }
    public Course getCourse() { return course; }
    public Integer getRank() { return rank; }

    public void setStudent(Student student) { this.student = student; }
    public void setPack(Pack pack) { this.pack = pack; }
    public void setCourse(Course course) { this.course = course; }
    public void setRank(Integer rank) { this.rank = rank; }
}
