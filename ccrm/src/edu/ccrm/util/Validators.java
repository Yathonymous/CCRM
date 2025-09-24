package edu.ccrm.util;

public final class Validators {
    private Validators() {}

    public static boolean isEmail(String s) {
        return s != null && s.matches("^\\S+@\\S+\\.\\S+$");
    }

    public static boolean notBlank(String s) {
        return s != null && !s.isBlank();
    }
}
