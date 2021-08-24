package net.gigabit101.quantumstorage.fabric;

import net.fabricmc.api.ModInitializer;
import net.gigabit101.quantumstorage.QuantumStorage;

public class QuantumStorageModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        QuantumStorage.init();
    }
}
