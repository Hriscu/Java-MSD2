package org.quickgrade.dto;

public class GradeDto {
    private String studentCode;
    private String courseCode;
    private Double grade;

    public GradeDto(String studentCode, String courseCode, Double grade) {
        this.studentCode = studentCode;
        this.courseCode = courseCode;
        this.grade = grade;
    }
    public String getStudentCode() { return studentCode; }
    public String getCourseCode() { return courseCode; }
    public Double getGrade() { return grade; }
}