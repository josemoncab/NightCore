package dev.josemc.nightcore.test;

import dev.josemc.nightcore.NightCore;
import dev.josemc.nightcore.files.NYML;
import dev.josemc.nightcore.test.files.ExampleHolder;

public class TestManager {
    private final NightCore plugin = NightCore.get();
    private final String dataFolder = plugin.getDataFolder().getPath();

    public TestManager() {
        this.testFiles();
    }

    private void testFiles() {
        /* TEST 1: Crear el archivo con parametros por defecto
        *   Crear archivo en la raiz de la carpeta del plugin
        * */
        NYML test1 = NYML.loadFrom(ExampleHolder.class);
    }
}