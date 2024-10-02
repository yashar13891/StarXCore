package org.yashar.teststarxcore;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class RepairTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("repair.use")) { // optional: check for permission
                suggestions.add("hand");
                suggestions.add("all");
            }
        }
        return suggestions;
    }
}