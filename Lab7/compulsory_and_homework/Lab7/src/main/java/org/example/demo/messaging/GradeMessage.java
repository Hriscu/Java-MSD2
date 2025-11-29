package org.example.demo.messaging;

import java.io.Serializable;

public class GradeMessage implements Serializable {
    private String studentCode;
    private String courseCode;
    private Double grade;

    public GradeMessage() {}

    public GradeMessage(String studentCode, String courseCode, Double grade) {
        this.studentCode = studentCode;
        this.courseCode = courseCode;
        this.grade = grade;
    }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public Double getGrade() { return grade; }
    public void setGrade(Double grade) { this.grade = grade; }

    @Override
    public String toString() {
        return "GradeMessage{student='" + studentCode + "', course='" + courseCode + "', grade=" + grade + '}';
    }
}