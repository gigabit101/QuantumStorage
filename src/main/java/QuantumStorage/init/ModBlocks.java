package QuantumStorage.init;

import QuantumStorage.block.BlockQChest;
import QuantumStorage.block.BlockQuantumDsu;
import QuantumStorage.block.BlockQuantumTank;
import QuantumStorage.items.itemblocks.ItemBlockQChest;
import QuantumStorage.items.itemblocks.ItemBlockQuantumDsu;
import QuantumStorage.items.itemblocks.ItemBlockQuantumTank;
import QuantumStorage.tile.TileQuantumDsu;
import QuantumStorage.tile.TileQuantumTank;
import QuantumStorage.tile.prefab.TileQChest;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.InvocationTargetException;

public class ModBlocks 
{
	public static Block QuantumDsu;
	
	public static Block QuantumTank;

	public static Block QChest;

	public static void init() 
	{
		//MK1
		QuantumDsu = new BlockQuantumDsu(Material.IRON);
        registerBlock(QuantumDsu, ItemBlockQuantumDsu.class, "quantumdsu");
		GameRegistry.registerTileEntity(TileQuantumDsu.class, "tilequantumdsu");

		//Tank
		QuantumTank = new BlockQuantumTank(Material.IRON);
        registerBlock(QuantumTank, ItemBlockQuantumTank.class,  "quantumtank");
		GameRegistry.registerTileEntity(TileQuantumTank.class, "tilequantumtank");

        //Chests
//        QChest = new BlockQChest();
//        registerBlock(QChest, ItemBlockQChest.class, "qchest");
//        GameRegistry.registerTileEntity(TileQChest.class, "tileqchest");
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
