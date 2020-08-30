package net.gigabit101.quantumstorage;

import net.gigabit101.quantumstorage.config.ConfigQuantumStorage;
import net.gigabit101.quantumstorage.containers.*;
import net.gigabit101.quantumstorage.init.QSBlocks;
import net.gigabit101.quantumstorage.init.QSItems;
import net.gigabit101.quantumstorage.init.recipe.RecipeBagColour;
import net.gigabit101.quantumstorage.items.backpack.ItemQuantumBag;
import net.gigabit101.quantumstorage.proxy.ClientProxy;
import net.gigabit101.quantumstorage.proxy.CommonProxy;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ObjectHolder;

import java.util.EnumMap;

/**
 * Created by Gigabit101 on 27/01/2017.
 */
@Mod(QuantumStorage.MOD_ID)
public class QuantumStorage
{
    public static final String MOD_ID = "quantumstorage";
    
    public static QuantumStorage INSTANCE;
    
    //CONTAINERS
    @ObjectHolder(MOD_ID + ":" + "chestdiamond")
    public static ContainerType<ContainerChestDiamond> containerChestDiamondContainerType = null;
    @ObjectHolder(MOD_ID + ":" + "chestgold")
    public static ContainerType<ContainerChestGold> containerChestGoldContainerType = null;
    @ObjectHolder(MOD_ID + ":" + "chestiron")
    public static ContainerType<ContainerChestIron> containerChestIronContainerType = null;
    @ObjectHolder(MOD_ID + ":" + "trashcan")
    public static ContainerType<ContainerTrashcan> containerTrashcanContainerType = null;
    @ObjectHolder(MOD_ID + ":" + "qsu")
    public static ContainerType<ContainerQSU> containerQsuContainerType = null;
    @ObjectHolder(MOD_ID + ":" + "tank")
    public static ContainerType<ContainerTank> containerTankContainerType = null;
    @ObjectHolder(MOD_ID + ":" + "bag")
    public static final ContainerType<ContainerBag> quantumBag = null;

    public static final EnumMap<DyeColor, ItemQuantumBag> BAGS = new EnumMap<>(DyeColor.class);

    private static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public QuantumStorage()
    {
        INSTANCE = this;
    
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        QSItems.ITEMS.register(eventBus);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, QuantumStorage::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, QuantumStorage::serializers);

        QSBlocks.BLOCKS.register(eventBus);
        QSBlocks.TILES_ENTITIES.register(eventBus);
    
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigQuantumStorage.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigQuantumStorage.COMMON_CONFIG);
        
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, QuantumStorage::registerContainers);

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

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event)
    {
        event.getRegistry().register(IForgeContainerType.create(ContainerChestDiamond::new).setRegistryName(MOD_ID, "chestdiamond"));
        event.getRegistry().register(IForgeContainerType.create(ContainerChestGold::new).setRegistryName(MOD_ID, "chestgold"));
        event.getRegistry().register(IForgeContainerType.create(ContainerChestIron::new).setRegistryName(MOD_ID, "chestiron"));

        event.getRegistry().register(IForgeContainerType.create(ContainerTrashcan::new).setRegistryName(MOD_ID, "trashcan"));
        event.getRegistry().register(IForgeContainerType.create(ContainerQSU::new).setRegistryName(MOD_ID, "qsu"));
        event.getRegistry().register(IForgeContainerType.create(ContainerTank::new).setRegistryName(MOD_ID, "tank"));
        event.getRegistry().register(new ContainerType<>(ContainerBag::new).setRegistryName("bag"));
    }

    @SubscribeEvent
    public static void serializers(RegistryEvent.Register<IRecipeSerializer<?>> e)
    {
        e.getRegistry().register(RecipeBagColour.SERIALIZER.setRegistryName(new ResourceLocation(QuantumStorage.MOD_ID, "bag_colour")));
    }

    void doClientStuff(final FMLClientSetupEvent event)
    {
        proxy.registerGuis();
        proxy.registerRenders();
        proxy.registerColors();
        proxy.registerKeybindings();
    }
}
