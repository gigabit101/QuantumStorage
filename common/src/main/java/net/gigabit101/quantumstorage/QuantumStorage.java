package net.gigabit101.quantumstorage;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.utils.Env;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.gigabit101.quantumstorage.init.ModBlocks;
import net.gigabit101.quantumstorage.init.ModMenus;
import net.gigabit101.quantumstorage.init.ModItems;
import net.gigabit101.quantumstorage.init.ModScreens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class QuantumStorage
{
    public static final String MOD_ID = "quantumstorage";
    public static final CreativeModeTab CREATIVE_TAB = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "creative_tab"), () -> new ItemStack(ModBlocks.QUANTUM_STORAGE_UNIT.get()));

    @Environment(EnvType.CLIENT)
    private static void onClientSetup(Minecraft minecraft)
    {
        ModScreens.init();
    }

    public static void init()
    {
        ModBlocks.BLOCKS.register();
        ModBlocks.TILES.register();
        ModItems.ITEMS.register();
        ModMenus.MENUS.register();

        if(Platform.getEnvironment() == Env.CLIENT)
        {
            ClientLifecycleEvent.CLIENT_SETUP.register(QuantumStorage::onClientSetup);
//            RenderTypeRegistry.register(RenderType.cutoutMipped(), ModBlocks.QUANTUM_STORAGE_UNIT.get());
        }
    }
}
