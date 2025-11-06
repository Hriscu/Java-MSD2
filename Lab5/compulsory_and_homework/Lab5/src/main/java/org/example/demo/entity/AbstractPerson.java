package org.example.demo.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @Column
    protected String email;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}
