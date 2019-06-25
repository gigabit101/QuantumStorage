package QuantumStorage;

import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
/**
 * Created by Gigabit101 on 27/01/2017.
 */
@Mod(QuantumStorage.MOD_ID)
public class QuantumStorage
{
    public static final String MOD_ID = "quantumstorage";
    public static final String MOD_NAME = "QuantumStorage";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "required-after:forge@[14.21.0.2359,);required-after:reborncore";
    
    public static ConfigQuantumStorage config;
    public static QuantumStorage INSTANCE;
    
//    @SidedProxy(clientSide = "QuantumStorage.proxy.ClientProxy", serverSide = "QuantumStorage.proxy.CommonProxy")
    public static CommonProxy proxy;

    public QuantumStorage()
    {
        INSTANCE = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preinit);

    }
    
    void preinit(FMLCommonSetupEvent event)
    {
        INSTANCE = this;

//        String path = event.getSuggestedConfigurationFile().getAbsolutePath().replace(QuantumStorage.MOD_ID, "QuantumStorage");

//        config = ConfigQuantumStorage.init(new File(path));
//
//        ModBlocks.init();
//
//        ModRecipes.init();

//        if (Loader.isModLoaded("refinedstorage"))
//        {
//            CompatHandler.init();
//        }
//
//        proxy.registerRenders();
//
//        proxy.registerKeybindings();
//
//        PacketHandler.register();
//
//        QunatumBagImpl.init();
//
//        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
//
//        MinecraftForge.EVENT_BUS.register(MultiBlockPackets.class);
    }
//
//    @Mod.EventHandler
//    public void init(FMLInitializationEvent event)
//    {
//        proxy.registerColors();
//    }
//
//    @Mod.EventHandler
//    public void serverStarted(FMLServerStartingEvent event)
//    {
//        event.registerServerCommand(new CommandBuildMultiBlock());
//    }
}
