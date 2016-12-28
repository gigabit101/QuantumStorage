package QuantumStorage.beta;

import QuantumStorage.beta.chest.*;
import QuantumStorage.items.itemblocks.ItemBlockQChest;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Gigabit101 on 28/12/2016.
 */
public class BetaInit
{
    public static Block QChest;

    public static void init()
    {
        //Chests
        QChest = new BlockQChest();
        registerBlock(QChest, ItemBlockQChest.class, "qchest");
		GameRegistry.registerTileEntity(TileIronChest.class, "ironchest");
		GameRegistry.registerTileEntity(TileGoldChest.class, "goldchest");
		GameRegistry.registerTileEntity(TileDiamondChest.class, "diamondchest");
    }

    public static void registerBlock(Block block, String name)
    {
        block.setRegistryName(name);
        GameRegistry.register(block);
        GameRegistry.register(new ItemBlock(block), block.getRegistryName());
    }

    public static void registerBlock(Block block, Class<? extends ItemBlock> itemclass, String name)
    {
        block.setRegistryName(name);
        GameRegistry.register(block);
        try
        {
            ItemBlock itemBlock = itemclass.getConstructor(Block.class).newInstance(block);
            itemBlock.setRegistryName(name);
            GameRegistry.register(itemBlock);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
}
