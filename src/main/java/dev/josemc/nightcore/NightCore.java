package dev.josemc.nightcore;

import org.bukkit.plugin.java.JavaPlugin;

public class NightCore extends JavaPlugin {

    private static NightCore instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    public static NightCore get() {
        return instance;
    }
}
