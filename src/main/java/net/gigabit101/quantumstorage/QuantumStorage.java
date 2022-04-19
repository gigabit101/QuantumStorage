package net.gigabit101.quantumstorage;

import net.gigabit101.quantumstorage.config.ConfigQuantumStorage;
import net.gigabit101.quantumstorage.init.ModBlocks;
import net.gigabit101.quantumstorage.init.ModContainers;
import net.gigabit101.quantumstorage.init.ModItems;
import net.gigabit101.quantumstorage.init.ModScreens;
import net.gigabit101.quantumstorage.items.backpack.ItemQuantumBag;
import net.gigabit101.quantumstorage.proxy.ClientProxy;
import net.gigabit101.quantumstorage.proxy.CommonProxy;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;

/**
 * Created by Gigabit101 on 27/01/2017.
 */
@Mod(QuantumStorage.MOD_ID)
public class QuantumStorage
{
    public static final String MOD_ID = "quantumstorage";
    public static Logger LOGGER = LogManager.getLogger();
    
    public static QuantumStorage INSTANCE;

    public static final EnumMap<DyeColor, ItemQuantumBag> BAGS = new EnumMap<>(DyeColor.class);

    private static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public QuantumStorage()
    {
        INSTANCE = this;
    
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        ModItems.ITEMS.register(eventBus);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, QuantumStorage::registerItems);

        ModBlocks.BLOCKS.register(eventBus);
        ModBlocks.TILES_ENTITIES.register(eventBus);
        ModContainers.CONTAINERS.register(eventBus);
    
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigQuantumStorage.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigQuantumStorage.COMMON_CONFIG);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    
        ConfigQuantumStorage.loadConfig(ConfigQuantumStorage.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-client.toml"));
        ConfigQuantumStorage.loadConfig(ConfigQuantumStorage.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MOD_ID + "-common.toml"));
    }

    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        for (DyeColor color : DyeColor.values())
        {
            ItemQuantumBag s = new ItemQuantumBag(color);
            BAGS.put(color, s);
            event.getRegistry().register(s);
        }
    }

    void doClientStuff(final FMLClientSetupEvent event)
    {
        ModScreens.init();
        proxy.registerRenders();
        proxy.registerColors();
        proxy.registerKeybindings();
    }
}
