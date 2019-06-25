package QuantumStorage;

import QuantumStorage.blocks.BlockChestDiamond;
import QuantumStorage.proxy.ClientProxy;
import QuantumStorage.proxy.CommonProxy;
import QuantumStorage.tiles.chests.TileChestDiamond;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Objects;

/**
 * Created by Gigabit101 on 27/01/2017.
 */
@Mod(QuantumStorage.MOD_ID)
public class QuantumStorage
{
    public static final String MOD_ID = "quantumstorage";
    public static final String MOD_NAME = "QuantumStorage";
    
    public static QuantumStorage INSTANCE;

//    @ObjectHolder(MOD_ID + ":" + "tilechestdiamond")
    public static TileEntityType<?> tileChestDiamond;

//    @ObjectHolder(MOD_ID + ":chestdiamond")
    public static Block blockChestDiamond ;
    
    private static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public QuantumStorage()
    {
        INSTANCE = this;
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, QuantumStorage::registerContainers);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, QuantumStorage::registerBlocks);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, QuantumStorage::registerTileEntity);
//        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, QuantumStorage::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::config);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(TileEntityType.Builder.create(TileChestDiamond::new, blockChestDiamond).build(null).setRegistryName(new ResourceLocation(QuantumStorage.MOD_ID, "tilechestdiamond")));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        Block.Properties properties = Block.Properties.create(Material.IRON, MaterialColor.GRAY);
        properties.hardnessAndResistance(3.0F);
        event.getRegistry().registerAll(new BlockChestDiamond(properties));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(new BlockItem(blockChestDiamond, new Item.Properties().group(ItemGroup.MISC)).setRegistryName(Objects.requireNonNull(blockChestDiamond.getRegistryName())));
    }

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
//        event.getRegistry().register(IForgeContainerType.create(ContainerStorageCabinet::new).setRegistryName(MOD_ID, MOD_ID));
    }

    void doClientStuff(final FMLClientSetupEvent event) {
//        proxy.registerRenders();
//        proxy.registerColors();
//        proxy.registerKeybindings();
    }

    @SubscribeEvent
    void config(ModConfig.ModConfigEvent event) {
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, StorageCabinetConfig.spec);
    }
}
