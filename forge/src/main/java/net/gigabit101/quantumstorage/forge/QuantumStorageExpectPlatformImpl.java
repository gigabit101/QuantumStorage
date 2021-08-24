package net.gigabit101.quantumstorage.forge;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class QuantumStorageExpectPlatformImpl {
    /**
     * This is our actual method to {@link net.examplemod.QuantumStorageExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
