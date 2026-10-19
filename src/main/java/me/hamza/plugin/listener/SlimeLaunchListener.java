package me.hamza.plugin.listener;

import me.hamza.plugin.SlimeLauncher;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class SlimeLaunchListener implements Listener {
    private final SlimeLauncher plugin;

    public SlimeLaunchListener(SlimeLauncher plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();

        if (to == null || (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ())) {
            return;
        }

        Player player = event.getPlayer();
        Location playerLoc = player.getLocation();

        boolean onSlime = playerLoc.getBlock().getType() == Material.SLIME_BLOCK
                || playerLoc.getBlock().getRelative(BlockFace.DOWN).getType() == Material.SLIME_BLOCK;

        if (!onSlime) {
            return;
        }

        double horizontalMultiplier = this.plugin.getConfig().getDouble("slime-launcher.horizontal-multiplier", 2.0D);
        double verticalBoost = this.plugin.getConfig().getDouble("slime-launcher.vertical-boost", 1.2D);

        Vector direction = playerLoc.getDirection().setY(0).normalize().multiply(horizontalMultiplier);
        direction.setY(verticalBoost);

        player.setVelocity(direction);

        if (this.plugin.getConfig().getBoolean("slime-launcher.sound.enabled", true)) {
            String soundName = this.plugin.getConfig().getString("slime-launcher.sound.name", "FIREWORK_LAUNCH");
            try {
                Sound sound = Sound.valueOf(soundName.toUpperCase());
                float volume = (float) this.plugin.getConfig().getDouble("slime-launcher.sound.volume", 1.0D);
                float pitch = (float) this.plugin.getConfig().getDouble("slime-launcher.sound.pitch", 1.0D);

                player.playSound(playerLoc, sound, volume, pitch);
            } catch (IllegalArgumentException ignored) {
            }
        }
    }
}