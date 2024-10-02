package org.yashar.teststarxcore;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SpawnDataManager {
    private final Teststarxcore plugin;
    private File spawnFile;
    private FileConfiguration spawnConfig;

    public SpawnDataManager(Teststarxcore plugin) {
        this.plugin = plugin;
        this.spawnFile = new File(plugin.getDataFolder(), "spawn.yml");
        this.spawnConfig = YamlConfiguration.loadConfiguration(spawnFile);
        loadConfig();
    }

    public Location getSpawnLocation() {
        if (spawnConfig.getString("spawn.world") == null) {
            return null;
        }

        String world = spawnConfig.getString("spawn.world");
        double x = spawnConfig.getDouble("spawn.x");
        double y = spawnConfig.getDouble("spawn.y");
        double z = spawnConfig.getDouble("spawn.z");
        float yaw = (float) spawnConfig.getDouble("spawn.yaw");
        float pitch = (float) spawnConfig.getDouble("spawn.pitch");
        return new Location(plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
    }


    public void setSpawnLocation(Location location) {
        spawnConfig.set("spawn.world", location.getWorld().getName());
        spawnConfig.set("spawn.x", location.getX());
        spawnConfig.set("spawn.y", location.getY());
        spawnConfig.set("spawn.z", location.getZ());
        spawnConfig.set("spawn.yaw", location.getYaw());
        spawnConfig.set("spawn.pitch", location.getPitch());

        saveConfig();
    }

    private void loadConfig() {
        if (!spawnFile.exists()) {
            try {
                spawnFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveConfig() {
        try {
            spawnConfig.save(spawnFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
