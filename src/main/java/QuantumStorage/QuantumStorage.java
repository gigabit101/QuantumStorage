package QuantumStorage;

import QuantumStorage.blocks.*;
import QuantumStorage.client.CreativeTabQuantumStorage;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.containers.*;
import QuantumStorage.items.ItemQuantumBag;
import QuantumStorage.items.ItemQuantumBattery;
import QuantumStorage.proxy.ClientProxy;
import QuantumStorage.proxy.CommonProxy;
import QuantumStorage.tiles.TileQsu;
import QuantumStorage.tiles.TileTank;
import QuantumStorage.tiles.TileTrashcan;
import QuantumStorage.tiles.chests.TileChestDiamond;
import QuantumStorage.tiles.chests.TileChestGold;
import QuantumStorage.tiles.chests.TileChestIron;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;

import java.util.EnumMap;
import java.util.Objects;

/**
 * Created by Gigabit101 on 27/01/2017.
 */
@Mod(QuantumStorage.MOD_ID)
public class QuantumStorage
{
    public static final String MOD_ID = "quantumstorage";
    
    public static QuantumStorage INSTANCE;
    ConfigQuantumStorage config = ConfigQuantumStorage.INSTANCE;

    //TILES
    @ObjectHolder(MOD_ID + ":" + "tilechestdiamond")
    public static TileEntityType<?> tileChestDiamond;
    @ObjectHolder(MOD_ID + ":" + "tilechestgold")
    public static TileEntityType<?> tileChestGold;
    @ObjectHolder(MOD_ID + ":" + "tilechestiron")
    public static TileEntityType<?> tileChestIron;
    @ObjectHolder(MOD_ID + ":" + "tiletrashcan")
    public static TileEntityType<?> tileTrashcan;
    @ObjectHolder(MOD_ID + ":" + "tileqsu")
    public static TileEntityType<?> tileQsu;
    @ObjectHolder(MOD_ID + ":" + "tiletank")
    public static TileEntityType<?> tileTank;

    //BLOCKS
    @ObjectHolder(MOD_ID + ":" + "chestdiamond")
    public static Block blockChestDiamond;
    @ObjectHolder(MOD_ID + ":" + "chestgold")
    public static Block blockChestGold;
    @ObjectHolder(MOD_ID + ":" + "chestiron")
    public static Block blockChestIron;
    @ObjectHolder(MOD_ID + ":" + "trashcan")
    public static Block blockTashcan;
    @ObjectHolder(MOD_ID + ":" + "qsu")
    public static Block blockQsu;
    @ObjectHolder(MOD_ID + ":" + "tank")
    public static Block blockTank;


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
    
    //ITEMS
    @ObjectHolder(MOD_ID + ":" + "qsu")
    public static Item battery;

    public static final EnumMap<DyeColor, ItemQuantumBag> BAGS = new EnumMap<>(DyeColor.class);


    private static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public QuantumStorage()
    {
        INSTANCE = this;
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, QuantumStorage::registerContainers);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, QuantumStorage::registerBlocks);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, QuantumStorage::registerTileEntity);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, QuantumStorage::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::config);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event)
    {
        event.getRegistry().register(TileEntityType.Builder.create(TileChestDiamond::new, blockChestDiamond).build(null).setRegistryName(new ResourceLocation(QuantumStorage.MOD_ID, "tilechestdiamond")));
        event.getRegistry().register(TileEntityType.Builder.create(TileChestGold::new, blockChestGold).build(null).setRegistryName(new ResourceLocation(QuantumStorage.MOD_ID, "tilechestgold")));
        event.getRegistry().register(TileEntityType.Builder.create(TileChestIron::new, blockChestIron).build(null).setRegistryName(new ResourceLocation(QuantumStorage.MOD_ID, "tilechestiron")));

        event.getRegistry().register(TileEntityType.Builder.create(TileTrashcan::new, blockTashcan).build(null).setRegistryName(new ResourceLocation(QuantumStorage.MOD_ID, "tiletrashcan")));
        event.getRegistry().register(TileEntityType.Builder.create(TileQsu::new, blockQsu).build(null).setRegistryName(new ResourceLocation(QuantumStorage.MOD_ID, "tileqsu")));
        event.getRegistry().register(TileEntityType.Builder.create(TileTank::new, blockTank).build(null).setRegistryName(new ResourceLocation(QuantumStorage.MOD_ID, "tiletank")));
    
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        Block.Properties properties = Block.Properties.create(Material.IRON, MaterialColor.GRAY);
        properties.hardnessAndResistance(3.0F);

        event.getRegistry().register(new BlockChestDiamond(properties));
        event.getRegistry().register(new BlockChestGold(properties));
        event.getRegistry().register(new BlockChestIron(properties));

        event.getRegistry().register(new BlockTrashcan(properties));
        event.getRegistry().register(new BlockQSU(properties));
        event.getRegistry().register(new BlockTank(properties));
    
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new BlockItem(blockChestDiamond, new Item.Properties().group(CreativeTabQuantumStorage.INSTANCE)).setRegistryName(Objects.requireNonNull(blockChestDiamond.getRegistryName())));
        event.getRegistry().register(new BlockItem(blockChestGold, new Item.Properties().group(CreativeTabQuantumStorage.INSTANCE)).setRegistryName(Objects.requireNonNull(blockChestGold.getRegistryName())));
        event.getRegistry().register(new BlockItem(blockChestIron, new Item.Properties().group(CreativeTabQuantumStorage.INSTANCE)).setRegistryName(Objects.requireNonNull(blockChestIron.getRegistryName())));

        event.getRegistry().register(new BlockItem(blockTashcan, new Item.Properties().group(CreativeTabQuantumStorage.INSTANCE)).setRegistryName(Objects.requireNonNull(blockTashcan.getRegistryName())));
        event.getRegistry().register(new BlockItem(blockQsu, new Item.Properties().group(CreativeTabQuantumStorage.INSTANCE)).setRegistryName(Objects.requireNonNull(blockQsu.getRegistryName())));
        event.getRegistry().register(new BlockItem(blockTank, new Item.Properties().group(CreativeTabQuantumStorage.INSTANCE)).setRegistryName(Objects.requireNonNull(blockTank.getRegistryName())));
        
        event.getRegistry().register(new ItemQuantumBattery());

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
    
    }

    void doClientStuff(final FMLClientSetupEvent event)
    {
        proxy.registerGuis();
        proxy.registerRenders();
        proxy.registerColors();
        proxy.registerKeybindings();
    }

    @SubscribeEvent
    void config(ModConfig.ModConfigEvent event)
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigQuantumStorage.spec);
    }
}
