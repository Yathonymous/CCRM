package edu.ccrm.domain;

public abstract class Person {
    protected final String id;
    protected String fullName;
    protected String email;
    protected boolean active = true;

    public Person(String id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public abstract String getRole();

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public boolean isActive() { return active; }

    public void deactivate() { this.active = false; }

    @Override
    public String toString() {
        return String.format("%s[id=%s, name=%s, email=%s, active=%s]",
                getRole(), id, fullName, email, active);
    }
}
