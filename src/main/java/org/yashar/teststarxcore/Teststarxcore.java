package org.yashar.teststarxcore;

import org.bukkit.plugin.java.JavaPlugin;

public final class Teststarxcore extends JavaPlugin {
    private static Teststarxcore Instance;
    public static Teststarxcore plugin;
    public static Teststarxcore getPlugin() {
        return plugin;
    }
    private SpawnDataManager spawnDataManager;
    private ConfigManager configManager;
    @Override
    public void onEnable() {
        spawnDataManager = new SpawnDataManager(this);
        this.configManager = new ConfigManager(this);
        Instance = this;
        saveDefaultConfig();
        getCommand("gmc").setExecutor(new gmccommand());
        getCommand("gma").setExecutor(new gmacommand());
        getCommand("gms").setExecutor(new gmscommand());
        getCommand("gmsp").setExecutor(new gmspcommand());
        getCommand("repair").setExecutor(new repaircommand(this));
        getCommand("nightvision").setExecutor(new nightvisionCommandExecuter(this));
        getCommand("setspawn").setExecutor(new SetSpawnCommand(spawnDataManager,this));
        getCommand("spawn").setExecutor(new SpawnCommand(spawnDataManager, this));
        this.getCommand("repair").setTabCompleter(new RepairTabCompleter());
        this.getCommand("fly").setExecutor(new FlyCommandExecutor());
        this.getCommand("fly").setTabCompleter(new FlyTabCompleter());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static Teststarxcore getInstance() {
        return Instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
