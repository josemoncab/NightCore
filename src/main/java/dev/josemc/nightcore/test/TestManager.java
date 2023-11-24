package dev.josemc.nightcore.test;

import dev.josemc.nightcore.NightCore;
import dev.josemc.nightcore.files.NYML;
import dev.josemc.nightcore.test.files.ExampleHolder;

public class TestManager {
    private final NightCore plugin = NightCore.get();
    private final String dataFolder = plugin.getDataFolder().getPath();

    public TestManager() {
        testFiles();
    }

    private void testFiles() {
        NYML test1 = NYML.loadFrom(ExampleHolder.class);
    }
}