package org.example.demo.dto;

import javax.validation.constraints.*;

public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 4)
    private String password;

    @NotBlank
    private String role; // "ADMIN", "INSTRUCTOR" sau "STUDENT"

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}
