package org.example.demo.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PreferenceDTO {

    @NotNull
    private Long studentId;

    @NotNull
    private Long packId;

    @NotNull
    private Long courseId;

    @NotNull
    @Min(1)
    private Integer rank;

    public Long getStudentId() { return studentId; }
    public Long getPackId() { return packId; }
    public Long getCourseId() { return courseId; }
    public Integer getRank() { return rank; }

    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public void setPackId(Long packId) { this.packId = packId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public void setRank(Integer rank) { this.rank = rank; }
}
