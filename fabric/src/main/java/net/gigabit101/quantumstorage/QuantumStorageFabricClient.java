package net.gigabit101.quantumstorage;

import net.creeperhost.polylib.registry.PolyScreens;
import net.fabricmc.api.ClientModInitializer;
import net.gigabit101.quantumstorage.client.QuantumStorageClient;

public final class QuantumStorageFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        QuantumStorageClient.init();
        PolyScreens.flush();
    }
}
