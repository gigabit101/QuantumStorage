package QuantumStorage.init;

import QuantumStorage.compat.CompatHandler;
import QuantumStorage.upgrades.ItemUpgrade;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ModelHandler
{
    @SubscribeEvent
    public static void init(ModelRegistryEvent event)
    {
        //Blocks
        registerItemModel(ModBlocks.DSU, 0);
        registerItemModel(ModBlocks.TANK, 0);
        registerItemModel(ModBlocks.BARREL, 0);
        registerItemModel(ModBlocks.CRATER, 0);
        registerItemModel(ModBlocks.CHEST_IRON, 0);
        registerItemModel(ModBlocks.CHEST_GOLD, 0);
        registerItemModel(ModBlocks.CHEST_DIAMOND, 0);
        registerItemModel(ModBlocks.QUANTUM_CRAFTER, 0);
        registerItemModel(ModBlocks.CRATE, 0);
        registerItemModel(ModBlocks.TRASH_CAN, 0);
        registerItemModel(ModBlocks.TRASH_CAN_FLUID, 0);
        registerItemModel(ModBlocks.SAFE, 0);
        registerItemModel(ModItems.BATTERY, 0);
        registerItemModel(ModItems.BATTERY, 1);

        //Items
        int i;
        for (i = 0; i < ItemUpgrade.types.length; ++i)
        {
            String[] name = ItemUpgrade.types.clone();
            registerItemModel(ModItems.UPGRADE, i, name[i]);
        }

        registerItemModel(ModItems.CRATE, 0);

        if (Loader.isModLoaded("refinedstorage"))
        {
            registerItemModel(CompatHandler.DISK, 0);
            registerItemModel(CompatHandler.DISK_FLUID, 0);
        }
    }

    static void registerItemModel(Item i, int meta)
    {
        ResourceLocation loc = i.getRegistryName();
        ModelLoader.setCustomModelResourceLocation(i, meta, new ModelResourceLocation(loc, "inventory"));
    }

    static void registerItemModel(Block b, int meta)
    {
        registerItemModel(Item.getItemFromBlock(b), meta);
    }

    static void registerItemModel(Item i, int meta, String variant)
    {
        ResourceLocation loc = i.getRegistryName();
        ModelLoader.setCustomModelResourceLocation(i, meta, new ModelResourceLocation(loc, "type=" + variant));
    }
}
