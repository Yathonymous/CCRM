package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private final String regNo;
    private final LocalDate admissionDate;
    private final List<Enrollment> enrolled = new ArrayList<>();

    public Student(String id, String fullName, String email, String regNo, LocalDate admissionDate) {
        super(id, fullName, email);
        this.regNo = regNo;
        this.admissionDate = admissionDate;
    }

    @Override
    public String getRole() { return "Student"; }

    public String getRegNo() { return regNo; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public List<Enrollment> getEnrolled() { return enrolled; }

    public void addEnrollment(Enrollment e) { enrolled.add(e); }
    public void removeEnrollment(Enrollment e) { enrolled.remove(e); }

    @Override
    public String toString() {
        return super.toString() + String.format(" | regNo=%s | admitted=%s | courses=%d",
                regNo, admissionDate, enrolled.size());
    }
}

