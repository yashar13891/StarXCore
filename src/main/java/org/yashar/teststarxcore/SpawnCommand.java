package org.yashar.teststarxcore;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnCommand implements CommandExecutor {
    private final SpawnDataManager spawnDataManager;
    private final Teststarxcore plugin;
    private final FileConfiguration config;

    public SpawnCommand(SpawnDataManager spawnDataManager, Teststarxcore plugin) {
        this.spawnDataManager = spawnDataManager;
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("teststarxcore.spawn")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.no-permission")));
                return false;
            }

            Location spawnLocation = spawnDataManager.getSpawnLocation();

            if (spawnLocation == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.spawn-not-set")));
                return true;
            }

            int delay = config.getInt("teleport-delay", 5);

            new BukkitRunnable() {
                private int timeLeft = delay;
                private boolean moved = false;
                private boolean messageSent = false;
                private final Location initialLocation = player.getLocation();

                @Override
                public void run() {
                    if (moved) {
                        if (!messageSent) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.teleport-cancelled")));
                            messageSent = true;
                        }
                        cancel();
                        return;
                    }

                    if (timeLeft > 0) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(config.getString("messages.teleporting"), timeLeft)));
                        timeLeft--;
                    } else {
                        Location currentLocation = player.getLocation();
                        if (currentLocation.getWorld().equals(initialLocation.getWorld()) &&
                                currentLocation.distance(initialLocation) > 0.5) {
                            moved = true;
                            if (!messageSent) {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.teleport-cancelled")));
                                messageSent = true;
                            }
                        } else {
                            player.teleport(spawnLocation);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.teleported")));
                            cancel();
                        }
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L);

            return true;
        }
        return false;
    }
}
