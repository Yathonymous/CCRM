package edu.ccrm.domain;

import java.time.LocalDateTime;

public class Enrollment {
    private final Student student;
    private final Course course;
    private final LocalDateTime enrolledAt;
    private Grade grade;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrolledAt = LocalDateTime.now();
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }
    public Grade getGrade() { return grade; }

    public void assignGrade(Grade g) { this.grade = g; }

    @Override
    public String toString() {
        return String.format("Enrollment[%s -> %s | grade=%s]",
                student.getRegNo(), course.getCode(), grade == null ? "N/A" : grade.toString());
    }
}

