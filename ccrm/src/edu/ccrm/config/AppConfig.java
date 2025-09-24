package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Singleton application config.
 */
public class AppConfig {
    private static AppConfig instance;
    private Path dataFolder;

    private AppConfig() {}

    public static synchronized AppConfig getInstance() {
        if (instance == null) instance = new AppConfig();
        return instance;
    }

    public void initDefaults() {
        this.dataFolder = Paths.get(System.getProperty("user.dir"), "ccrm-data");
        System.out.println("AppConfig: data folder = " + dataFolder.toAbsolutePath());
    }

    public Path getDataFolder() { return dataFolder; }
}