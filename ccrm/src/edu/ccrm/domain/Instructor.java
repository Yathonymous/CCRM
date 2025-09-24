package edu.ccrm.domain;

public class Instructor extends Person {
    private final String department;

    public Instructor(String id, String fullName, String email, String department) {
        super(id, fullName, email);
        this.department = department;
    }

    @Override
    public String getRole() { return "Instructor"; }

    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return super.toString() + " | dept=" + department;
    }
}

