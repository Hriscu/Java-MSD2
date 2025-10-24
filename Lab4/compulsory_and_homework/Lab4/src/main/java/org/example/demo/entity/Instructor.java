package org.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "instructors")
public class Instructor extends AbstractPerson {
    public Instructor() {}
    public Instructor(String name, String email) {
        this.name = name; this.email = email;
    }
}
