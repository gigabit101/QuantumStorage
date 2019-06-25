//package QuantumStorage.init;
//
//import QuantumStorage.upgrades.ItemUpgrade;
//import net.minecraft.block.Block;
//import net.minecraft.item.DyeColor;
//import net.minecraft.item.Item;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.client.event.ModelRegistryEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
///**
// * Created by Gigabit101 on 07/03/2017.
// */
//@Mod.EventBusSubscriber()
//public class ModelHandler
//{
//    @SubscribeEvent
//    public static void init(ModelRegistryEvent event)
//    {
//        //Blocks
//        registerItemModel(ModBlocks.DSU, 0);
//        registerItemModel(ModBlocks.TANK, 0);
//        registerItemModel(ModBlocks.CRATER, 0);
//        registerItemModel(ModBlocks.CHEST_IRON, 0);
//        registerItemModel(ModBlocks.CHEST_GOLD, 0);
//        registerItemModel(ModBlocks.CHEST_DIAMOND, 0);
//        registerItemModel(ModBlocks.CHEST_QUANIUM, 0);
//        registerItemModel(ModBlocks.QUANTUM_CRAFTER, 0);
//        registerItemModel(ModBlocks.CRATE, 0);
//        registerItemModel(ModBlocks.TRASH_CAN, 0);
//        registerItemModel(ModBlocks.TRASH_CAN_FLUID, 0);
//        registerItemModel(ModBlocks.SAFE, 0);
//        registerItemModel(ModItems.BATTERY, 0);
//        registerItemModel(ModItems.BATTERY, 1);
//        registerItemModel(ModItems.REMOTE, 0);
//
//        int i;
//        for (i = 0; i < DyeColor.values().length; ++i)
//        {
//            registerItemModel(ModItems.BAG, i);
//        }
//
//        //Items
//        for (i = 0; i < ItemUpgrade.types.length; ++i)
//        {
//            String[] name = ItemUpgrade.types.clone();
//            registerItemModel(ModItems.UPGRADE, i, name[i]);
//        }
//
//        registerItemModel(ModItems.CRATE, 0);
//
////        if (Loader.isModLoaded("refinedstorage"))
////        {
////            registerItemModel(CompatHandler.DISK, 0);
////            registerItemModel(CompatHandler.DISK_FLUID, 0);
////        }
////        for (i = 0; i < BlockMultiStorage.types.length; ++i)
////        {
////            String[] name = BlockMultiStorage.types.clone();
////            registerItemModel(ModBlocks.MULTIBLOCK_STORAGE, i, name[i]);
////        }
//    }
//
//
//    static void registerItemModel(Item i, int meta)
//    {
//        ResourceLocation loc = i.getRegistryName();
////        ModelLoader.setCustomModelResourceLocation(i, meta, new ModelResourceLocation(loc, "inventory"));
//    }
//
//    static void registerItemModel(Block b, int meta)
//    {
//        registerItemModel(Item.getItemFromBlock(b), meta);
//    }
//
//    static void registerItemModel(Item i, int meta, String variant)
//    {
//        ResourceLocation loc = i.getRegistryName();
////        ModelLoader.setCustomModelResourceLocation(i, meta, new ModelResourceLocation(loc, "type=" + variant));
//    }
//
//    static void registerItemModel(Block b, int meta, String variant)
//    {
//        registerItemModel(Item.getItemFromBlock(b), meta, variant);
//    }
//}
