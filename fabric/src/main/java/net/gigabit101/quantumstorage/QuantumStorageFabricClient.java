package net.gigabit101.quantumstorage;

import net.creeperhost.polylib.registry.PolyScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ExtractItemDecorationsCallback;
import net.gigabit101.quantumstorage.client.CrateItemOverlayRenderer;
import net.gigabit101.quantumstorage.client.QuantumStorageClient;

public final class QuantumStorageFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        QuantumStorageClient.init();
        ExtractItemDecorationsCallback.EVENT.register(CrateItemOverlayRenderer::render);
        PolyScreens.flush();
    }
}
