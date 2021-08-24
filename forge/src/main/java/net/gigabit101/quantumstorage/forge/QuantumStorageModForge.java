package net.gigabit101.quantumstorage.forge;

import dev.architectury.platform.forge.EventBuses;
import net.gigabit101.quantumstorage.QuantumStorage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(QuantumStorage.MOD_ID)
public class QuantumStorageModForge
{
    public QuantumStorageModForge()
    {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(QuantumStorage.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        QuantumStorage.init();
    }
}
