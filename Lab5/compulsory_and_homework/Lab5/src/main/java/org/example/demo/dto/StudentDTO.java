package org.example.demo.dto;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "student")
public class StudentDTO {

    @NotBlank @Size(max = 10)
    private String code;

    @NotBlank @Size(max = 100)
    private String name;

    @Email @Size(max = 100)
    private String email;

    @Min(1) @Max(6)
    private int year;

    public StudentDTO() {}

    public StudentDTO(String code, String name, String email, int year) {
        this.code = code;
        this.name = name;
        this.email = email;
        this.year = year;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getYear() { return year; }

    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setYear(int year) { this.year = year; }
}
