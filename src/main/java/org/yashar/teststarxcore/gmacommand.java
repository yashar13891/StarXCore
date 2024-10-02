package org.yashar.teststarxcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class gmacommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("teststarxcore.gma")) {
            FileConfiguration config = Teststarxcore.getInstance().getConfig();
            String noPermissionMessage = config.getString("messages.no-permission", "&x&E&3&3&4&1&4ℹ &cʏᴏᴜ ᴅᴏ ɴᴏᴛ ʜᴀᴠᴇ ᴘᴇʀᴍɪꜱꜱɪᴏɴ ᴛᴏ ᴜꜱᴇ ᴛʜɪꜱ ᴄᴏᴍᴍᴀɴᴅ.");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermissionMessage));
            return true;
        }

        FileConfiguration config = Teststarxcore.getInstance().getConfig();
        String message = config.getString("messages.adventure", "&x&E&3&3&4&1&4ℹ &eʏᴏᴜʀ ɢᴀᴍᴇ ᴍᴏᴅᴇ ʜᴀꜱ ʙᴇᴇɴ ᴄʜᴀɴɢᴇᴅ ᴛᴏ &cᴀᴅᴠᴇɴᴛᴜʀᴇ.");
        String targetMessage = config.getString("messages.target", "&aYou changed &b%player%'s &agame mode to &bAdventure.");

        if (args.length == 0) {
            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        target.setGameMode(GameMode.ADVENTURE);
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', targetMessage.replace("%player%", target.getName())));
        return true;
    }
}
