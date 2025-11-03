package org.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "pack_id"})
)
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pack_id")
    private Pack pack;

    public Enrollment() {}

    public Enrollment(Student student, Course course, Pack pack) {
        this.student = student;
        this.course = course;
        this.pack = pack;
    }

    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public Pack getPack() { return pack; }

    public void setStudent(Student student) { this.student = student; }
    public void setCourse(Course course) { this.course = course; }
    public void setPack(Pack pack) { this.pack = pack; }
}
