package org.yashar.teststarxcore;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
public class repaircommand implements CommandExecutor {


    private final Teststarxcore plugin;

    public repaircommand(Teststarxcore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(formatMessage("messages.not-a-player"));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(formatMessage("messages.no-argument"));
            return true;
        }

        if (args[0].equalsIgnoreCase("hand")) {
            repairHand(player);
            player.sendMessage(formatMessage("messages.repaired-hand")
                    .replace("{item}", getItemName(player.getInventory().getItemInMainHand())));
        } else if (args[0].equalsIgnoreCase("all")) {
            repairAll(player);
            player.sendMessage(formatMessage("messages.repaired-all"));
        } else {
            player.sendMessage(formatMessage("messages.invalid-argument"));
        }

        return true;
    }

    private void repairHand(Player player) {
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand != null && itemInHand.getType() != Material.AIR) {
            itemInHand.setDurability((short) 0);
        }
    }

    private void repairAll(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                item.setDurability((short) 0);
            }
        }
    }

    private String getItemName(ItemStack item) {
        if (item != null && item.getType() != Material.AIR) {
            return item.getType().toString().replace("_", " ").toLowerCase();
        }
        return "unknown item";
    }

    private String formatMessage(String path) {
        String message = plugin.getConfig().getString(path);
        if (message != null) {
            return ChatColor.translateAlternateColorCodes('&', message);
        }
        return "Message not found.";
    }
}