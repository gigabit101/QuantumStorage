package QuantumStorage.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

/**
 * Created by Gigabit101 on 07/09/2016.
 */
public class ModelHelper
{
    public static void init()
    {
        registerItemModel(ModBlocks.QuantumDsu, 0);
        registerItemModel(ModBlocks.QuantumTank, 0);
        registerItemModel(ModItems.renderUpgrade, 0);
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
}
