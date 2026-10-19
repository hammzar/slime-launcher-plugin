package me.hamza.plugin;

import me.hamza.plugin.listener.SlimeLaunchListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SlimeLauncher extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new SlimeLaunchListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
