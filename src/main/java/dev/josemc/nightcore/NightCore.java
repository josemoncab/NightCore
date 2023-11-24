package dev.josemc.nightcore;

import dev.josemc.nightcore.test.TestManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NightCore extends JavaPlugin {

    private static NightCore instance;

    @Override
    public void onEnable() {
        instance = this;

        new TestManager();
    }

    public static NightCore get() {
        return instance;
    }
}
