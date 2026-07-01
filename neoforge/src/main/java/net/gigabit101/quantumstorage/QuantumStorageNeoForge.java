package net.gigabit101.quantumstorage;

import net.creeperhost.polylib.neoforge.registry.NeoPolyRegistry;
import net.creeperhost.polylib.neoforge.registry.NeoPolyScreens;
import net.gigabit101.quantumstorage.client.QuantumStorageClient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public final class QuantumStorageNeoForge {
    public QuantumStorageNeoForge(ModContainer container, IEventBus bus) {
        QuantumStorageCommon.init();
        NeoPolyRegistry.registerToBus(bus, Constants.MOD_ID);

        if (FMLLoader.getCurrent().getDist().isClient()) {
            QuantumStorageClient.init();
            NeoPolyScreens.registerToBus(bus);
            bus.addListener(QuantumStorageNeoForgeClient::registerItemDecorations);
        }
    }
}
