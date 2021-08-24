package net.gigabit101.quantumstorage.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class QuantumStorageExpectPlatformImpl {
    /**
     * This is our actual method to {@link net.examplemod.QuantumStorageExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
