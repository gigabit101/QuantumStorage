package net.gigabit101.quantumstorage;

import net.fabricmc.api.ModInitializer;

public final class QuantumStorageFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        QuantumStorageCommon.init();
    }
}
