package edu.ccrm.domain;

public class Grade {
    public static final Grade S = new Grade("S", 10.0);
    public static final Grade A = new Grade("A", 9.0);
    public static final Grade B = new Grade("B", 8.0);
    public static final Grade C = new Grade("C", 7.0);
    public static final Grade F = new Grade("F", 0.0);

    private final String letter;
    private final double points;

    private Grade(String letter, double points) {
        this.letter = letter;
        this.points = points;
    }

    public String getLetter() { return letter; }
    public double getPoints() { return points; }

    @Override
    public String toString() { return letter; }
}


