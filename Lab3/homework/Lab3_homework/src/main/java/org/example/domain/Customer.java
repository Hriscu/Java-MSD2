package org.example.domain;

public class Customer {
    private Long id;
    private String name;
    private boolean loyal;
    private boolean eligible;

    public Customer(Long id, String name, boolean loyal, boolean eligible) {
        this.id = id;
        this.name = name;
        this.loyal = loyal;
        this.eligible = eligible;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public boolean isLoyal() { return loyal; }
    public boolean isEligible() { return eligible; }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + ", loyal=" + loyal + ", eligible=" + eligible + '}';
    }
}
