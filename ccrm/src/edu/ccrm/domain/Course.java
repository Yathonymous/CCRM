package edu.ccrm.domain;

/**
 * Immutable-like course with Builder pattern.
 */
public class Course {
    private final String code;
    private final String title;
    private final int credits;
    private final Instructor instructor;
    private final Semester semester;
    private final String department;

    private Course(Builder b) {
        this.code = b.code;
        this.title = b.title;
        this.credits = b.credits;
        this.instructor = b.instructor;
        this.semester = b.semester;
        this.department = b.department;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public Instructor getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return String.format("Course[%s | %s | %dcr | %s | %s]",
                code, title, credits, semester, department);
    }

    public static class Builder {
        private final String code;
        private String title = "";
        private int credits = 3;
        private Instructor instructor = null;
        private Semester semester = Semester.SPRING;
        private String department = "General";

        public Builder(String code) { this.code = code; }

        public Builder title(String t) { this.title = t; return this; }
        public Builder credits(int c) { this.credits = c; return this; }
        public Builder instructor(Instructor i) { this.instructor = i; return this; }
        public Builder semester(Semester s) { this.semester = s; return this; }
        public Builder department(String d) { this.department = d; return this; }

        public Course build() {
            // simple validation
            if (code == null || code.isBlank()) throw new IllegalArgumentException("code required");
            return new Course(this);
        }
    }
}
