package QuantumStorage.init;

import QuantumStorage.items.ItemUpgrade;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class ModelHandler
{
    public static void init()
    {
        registerItemModel(ModBlocks.DSU, 0);
        registerItemModel(ModBlocks.TANK, 0);
        registerItemModel(ModBlocks.BARREL, 0);
        int i;
        for (i = 0; i < ItemUpgrade.types.length; ++i) {
            String[] name = ItemUpgrade.types.clone();
            registerItemModel(ModItems.UPGRADE, i, name[i]);
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
