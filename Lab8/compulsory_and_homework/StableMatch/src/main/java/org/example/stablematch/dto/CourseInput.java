package org.example.stablematch.dto;

public class CourseInput {
    private String id;
    private int capacity;

    public CourseInput() {}
    public CourseInput(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}