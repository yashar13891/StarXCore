package org.yashar.teststarxcore;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlyTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> playerNames = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    playerNames.add(player.getName());
                }
            }
            return playerNames;
        } else if (args.length == 2) {
            List<String> options = Arrays.asList("enable", "disable");
            List<String> filteredOptions = new ArrayList<>();
            for (String option : options) {
                if (option.toLowerCase().startsWith(args[1].toLowerCase())) {
                    filteredOptions.add(option);
                }
            }
            return filteredOptions;
        }
        return null;
    }
}

