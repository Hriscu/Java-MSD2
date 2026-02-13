package org.example.stablematch.dto;

import java.util.List;

public class MatchRequest {
    private List<StudentInput> students;
    private List<CourseInput> courses;

    public List<StudentInput> getStudents() { return students; }
    public void setStudents(List<StudentInput> students) { this.students = students; }
    public List<CourseInput> getCourses() { return courses; }
    public void setCourses(List<CourseInput> courses) { this.courses = courses; }
}