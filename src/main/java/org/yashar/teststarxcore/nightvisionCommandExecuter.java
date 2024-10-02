package org.yashar.teststarxcore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;



public class nightvisionCommandExecuter implements CommandExecutor {
    private ConfigManager configManager;
    private final Teststarxcore plugin;

    public nightvisionCommandExecuter(Teststarxcore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // بررسی دسترسی
        if (!sender.hasPermission("teststarxcore.nightvision")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', configManager.getConfig().getString("messages.no-permission")));
            return false;
        }

        // بررسی آرگومان‌ها برای نام بازیکن
        if (args.length > 0) {
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer != null) {
                toggleNightVision(targetPlayer);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3NightVision toggled for " + targetPlayer.getName()));
                return true;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', configManager.getConfig().getString("messages.player-not-found")));
                return false;
            }
        }

        // اجرا برای خود فرستنده
        if (sender instanceof Player) {
            Player player = (Player) sender;
            toggleNightVision(player);
            return true;
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }
    }

    private void toggleNightVision(Player player) {
        int duration = plugin.getConfig().getInt("nightvision-duration");
        int amplifier = plugin.getConfig().getInt("nightvision-amplifier");

        // اگر مقدار duration برابر با -1 باشد، از مدت زمان نامحدود استفاده می‌کنیم
        int effectDuration = (duration == -1) ? Integer.MAX_VALUE : duration;

        if (isNightVision(player)) {
            // حذف افکت "Night Vision" اگر وجود دارد
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.sendTitle(ChatColor.translateAlternateColorCodes('&', configManager.getConfig().getString("messages.nightvision-disabled")), "");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', configManager.getConfig().getString("messages.nightvision-disabled")));
        } else {
            // اضافه کردن افکت "Night Vision" با مدت زمان نامحدود اگر duration برابر با -1 باشد
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, effectDuration, amplifier));
            player.sendTitle(ChatColor.translateAlternateColorCodes('&', configManager.getConfig().getString("messages.nightvision-activated")), "");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', configManager.getConfig().getString("messages.nightvision-activated")));
        }
    }

    public boolean isNightVision(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (effect.getType().equals(PotionEffectType.NIGHT_VISION)) {
                return true;
            }
        }
        return false;
    }
}