package org.yashar.teststarxcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import static org.yashar.teststarxcore.Teststarxcore.plugin;

public class FlyCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                if (!player.hasPermission("teststarxcore.fly")) {
                    player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                    return true;
                }
                boolean allowFlight = player.getAllowFlight();
                if (allowFlight) {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aTurned off flight!"));
                } else {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aTurned on flight!"));
                }
                return true;
            }

            if (args.length > 0) {
                if (!player.hasPermission("teststarxcore.fly.others")) {
                    player.sendMessage(ChatColor.RED + "You don't have permission to use this command on others.");
                    return true;
                }
                String playerName = args[0];
                Player target = Bukkit.getServer().getPlayerExact(playerName);
                if (target == null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis player is not online."));
                    return true;
                }

                if (args.length == 1) {
                    boolean allowFlight = target.getAllowFlight();
                    if (allowFlight) {
                        target.setAllowFlight(false);
                        target.setFlying(false);
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aTurned off flight!"));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aFlight off for " + target.getName()));
                    } else {
                        target.setAllowFlight(true);
                        target.setFlying(true);
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aTurned on flight!"));
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aFlight on for " + target.getName()));
                    }
                }

                if (args.length > 1) {
                    String action = args[1].toLowerCase();
                    if (action.equals("enable")) {
                        target.setAllowFlight(true);
                        target.setFlying(true);
                        player.sendMessage(ChatColor.GREEN + "Flight on for " + target.getName());
                        target.sendMessage(ChatColor.GREEN + "Turned on flight!");
                    } else if (action.equals("disable")) {
                        target.setAllowFlight(false);
                        target.setFlying(false);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aFlight off for " + target.getName()));
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aTurned off flight!"));
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cInvalid arguments! Use 'enable' or 'disable'."));
                    }
                }
            }
        }
        return true;
    }
}
