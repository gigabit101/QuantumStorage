package net.gigabit101.quantumstorage;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.platform.Platform;

import java.nio.file.Path;

@Deprecated
public class QuantumStorageExpectPlatform {
    /**
     * We can use {@link Platform#getConfigFolder()} but this is just an example of {@link ExpectPlatform}.
     * <p>
     * This must be a public static method. The platform-implemented solution must be placed under a
     * platform sub-package, with its class suffixed with {@code Impl}.
     * <p>
     * Example:
     * Expect: net.examplemod.ExampleExpectPlatform#getConfigDirectory()
     * Actual Fabric: net.examplemod.fabric.ExampleExpectPlatformImpl#getConfigDirectory()
     * Actual Forge: net.examplemod.forge.ExampleExpectPlatformImpl#getConfigDirectory()
     */
    @ExpectPlatform
    public static Path getConfigDirectory() {
        // Just throw an error, the content should get replaced at runtime.
        throw new AssertionError();
    }
}
