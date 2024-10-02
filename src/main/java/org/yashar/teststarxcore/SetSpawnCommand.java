package org.yashar.teststarxcore;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    private final SpawnDataManager spawnDataManager;
    private final FileConfiguration config;

    public SetSpawnCommand(SpawnDataManager spawnDataManager, Teststarxcore plugin) {
        this.spawnDataManager = spawnDataManager;
        this.config = plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Check for permission
            if (!player.hasPermission("teststarxcore.setspawn")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.no-permission")));
                return false;
            }

            Location location = player.getLocation();
            spawnDataManager.setSpawnLocation(location);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.spawn-set")));
            return true;
        }
        return false;
    }
}
