package net.gigabit101.quantumstorage;

import net.gigabit101.quantumstorage.client.CrateItemOverlayRenderer;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;

public final class QuantumStorageNeoForgeClient {
    private QuantumStorageNeoForgeClient() {
    }

    public static void registerItemDecorations(RegisterItemDecorationsEvent event) {
        event.register(QuantumStorageContent.CRATE_ITEM.get(), (graphics, font, stack, x, y) -> {
            CrateItemOverlayRenderer.render(graphics, font, stack, x, y);
            return false;
        });
    }
}
