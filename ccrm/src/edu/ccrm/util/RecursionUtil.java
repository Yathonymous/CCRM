package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.*;

/**
 * Recursively compute directory size and list by depth demonstration.
 */
public final class RecursionUtil {
    private RecursionUtil() {}

    public static long directorySize(Path p) {
        try {
            return Files.walk(p).filter(Files::isRegularFile).mapToLong(RecursionUtil::fileSizeSafe).sum();
        } catch (IOException e) {
            return 0L;
        }
    }

    private static long fileSizeSafe(Path p) {
        try {
            return Files.size(p);
        } catch (IOException e) {
            return 0L;
        }
    }
}
