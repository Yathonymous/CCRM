package edu.ccrm.service;

import edu.ccrm.config.AppConfig;
import edu.ccrm.util.RecursionUtil;

import java.io.IOException;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Copies exported data to a timestamped backup folder (NIO)
 */
public class BackupService {
    private final Path dataRoot = AppConfig.getInstance().getDataFolder();
    private final Path backupsRoot = dataRoot.resolve("backups");

    public void createBackup() {
        try {
            if (!Files.exists(dataRoot)) {
                System.out.println("No exported data found at " + dataRoot + ". Run export first.");
                return;
            }
            Files.createDirectories(backupsRoot);
            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path dest = backupsRoot.resolve("backup_" + ts);
            Files.createDirectories(dest);

            // copy all files recursively
            Files.walk(dataRoot)
                 .filter(Files::isRegularFile)
                 .forEach(src -> {
                     try {
                         Path rel = dataRoot.relativize(src);
                         Path target = dest.resolve(rel);
                         Files.createDirectories(target.getParent());
                         Files.copy(src, target, StandardCopyOption.REPLACE_EXISTING);
                     } catch (IOException e) {
                         System.out.println("Copy failed: " + e.getMessage());
                     }
                 });

            System.out.println("Backup created at: " + dest.toAbsolutePath());
            long total = RecursionUtil.directorySize(dest);
            System.out.println("Backup total size (bytes): " + total);
        } catch (IOException e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }
}
