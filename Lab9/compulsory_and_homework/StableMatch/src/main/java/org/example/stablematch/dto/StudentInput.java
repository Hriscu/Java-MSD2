package org.example.stablematch.dto;

import java.util.List;

public class StudentInput {
    private String id;
    private List<String> preferences;

    public StudentInput() {}
    public StudentInput(String id, List<String> preferences) {
        this.id = id;
        this.preferences = preferences;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public List<String> getPreferences() { return preferences; }
    public void setPreferences(List<String> preferences) { this.preferences = preferences; }
}