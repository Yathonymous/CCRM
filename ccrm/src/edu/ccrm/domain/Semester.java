package edu.ccrm.domain;

public class Semester {
    public static final Semester SPRING = new Semester("SPRING");
    public static final Semester SUMMER = new Semester("SUMMER");
    public static final Semester FALL   = new Semester("FALL");

    private final String name;
    private Semester(String name) { this.name = name; }
    public String getName() { return name; }
    @Override public String toString() { return name; }
}
