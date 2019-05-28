package QuantumStorage;

import QuantumStorage.compat.CompatHandler;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.handler.QunatumBagImpl;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.init.ModRecipes;
import QuantumStorage.multiblock.MultiBlockPackets;
import QuantumStorage.network.PacketHandler;
import QuantumStorage.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.io.File;

/**
 * Created by Gigabit101 on 27/01/2017.
 */
@Mod(name = QuantumStorage.MOD_NAME, modid = QuantumStorage.MOD_ID, version = QuantumStorage.MOD_VERSION, dependencies = QuantumStorage.MOD_DEPENDENCIES, acceptedMinecraftVersions = "[1.12,1.12.2]")
public class QuantumStorage
{
    public static final String MOD_ID = "quantumstorage";
    public static final String MOD_NAME = "QuantumStorage";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "required-after:forge@[14.21.0.2359,);required-after:reborncore";

    public static ConfigQuantumStorage config;

    @Mod.Instance
    public static QuantumStorage INSTANCE;

    @SidedProxy(clientSide = "QuantumStorage.proxy.ClientProxy", serverSide = "QuantumStorage.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event)
    {
        INSTANCE = this;

        String path = event.getSuggestedConfigurationFile().getAbsolutePath().replace(QuantumStorage.MOD_ID, "QuantumStorage");

        config = ConfigQuantumStorage.init(new File(path));

        ModBlocks.init();

        ModRecipes.init();

        if (Loader.isModLoaded("refinedstorage"))
        {
            CompatHandler.init();
        }

        proxy.registerRenders();

        PacketHandler.register();

        QunatumBagImpl.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
        
        MinecraftForge.EVENT_BUS.register(MultiBlockPackets.class);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.registerColors();
    }
}
