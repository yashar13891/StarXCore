package org.yashar.teststarxcore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
public class ConfigManager {
    private JavaPlugin plugin;
    private FileConfiguration config;
    private File configFile;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false); // ذخیره فایل config.yml از منابع پلاگین به پوشه داده‌های پلاگین
        }

        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save config to " + configFile);
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}