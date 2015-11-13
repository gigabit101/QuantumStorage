package QuantumStorage.init;

import QuantumStorage.block.BlockQuantumChest;
import QuantumStorage.block.BlockQuantumDsuMk1;
import QuantumStorage.block.BlockQuantumDsuMk2;
import QuantumStorage.block.BlockQuantumDsuMk3;
import QuantumStorage.block.BlockQuantumDsuMk4;
import QuantumStorage.block.tile.TileQuantumChest;
import QuantumStorage.block.tile.TileQuantumDsuMk1;
import QuantumStorage.block.tile.TileQuantumDsuMk2;
import QuantumStorage.block.tile.TileQuantumDsuMk3;
import QuantumStorage.block.tile.TileQuantumDsuMk4;
import QuantumStorage.items.itemblocks.ItemBlockQuantumChest;
import QuantumStorage.items.itemblocks.ItemBlockQuantumDsuMk1;
import QuantumStorage.items.itemblocks.ItemBlockQuantumDsuMk2;
import QuantumStorage.items.itemblocks.ItemBlockQuantumDsuMk3;
import QuantumStorage.items.itemblocks.ItemBlockQuantumDsuMk4;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModBlocks 
{
	public static Block QuantumDsuMk1;
	public static Block QuantumDsuMk2;
	public static Block QuantumDsuMk3;
	public static Block QuantumDsuMk4;
	public static Block QuantumChest;

	public static void init() 
	{
		//MK1
		QuantumDsuMk1 = new BlockQuantumDsuMk1(Material.iron);
		GameRegistry.registerBlock(QuantumDsuMk1, ItemBlockQuantumDsuMk1.class, "quantumdsumk1");
		GameRegistry.registerTileEntity(TileQuantumDsuMk1.class, "tilequantumdsumk1");
		//MK2
		QuantumDsuMk2 = new BlockQuantumDsuMk2(Material.iron);
		GameRegistry.registerBlock(QuantumDsuMk2, ItemBlockQuantumDsuMk2.class, "quantumdsumk2");
		GameRegistry.registerTileEntity(TileQuantumDsuMk2.class, "tilequantumdsumk2");
		//MK3
		QuantumDsuMk3 = new BlockQuantumDsuMk3(Material.iron);
		GameRegistry.registerBlock(QuantumDsuMk3, ItemBlockQuantumDsuMk3.class, "quantumdsumk3");
		GameRegistry.registerTileEntity(TileQuantumDsuMk3.class, "tilequantumdsumk3");
		//Mk4
		QuantumDsuMk4 = new BlockQuantumDsuMk4(Material.iron);
		GameRegistry.registerBlock(QuantumDsuMk4, ItemBlockQuantumDsuMk4.class, "quantumdsu");
		GameRegistry.registerTileEntity(TileQuantumDsuMk4.class, "tilequantumdsu");
		//Chest
//		QuantumChest = new BlockQuantumChest(Material.iron);
//		GameRegistry.registerBlock(QuantumChest, ItemBlockQuantumChest.class ,"quantumchest");
//		GameRegistry.registerTileEntity(TileQuantumChest.class, "tilequantumchest");
		addRecipes();
	}

	public static void addRecipes() 
	{	
		GameRegistry.addRecipe(new ItemStack(QuantumDsuMk1), 
				"EEE", 
				"ECE", 
				"EEE", 
				'E', new ItemStack(Blocks.chest), 
				'C', new ItemStack(ModItems.misc));	
		
		GameRegistry.addRecipe(new ItemStack(QuantumDsuMk2), 
				"EEE", 
				"ECE", 
				"EEE", 
				'E', new ItemStack(Items.gold_ingot), 
				'C', new ItemStack(QuantumDsuMk1));	
		
		GameRegistry.addRecipe(new ItemStack(QuantumDsuMk3), 
				"EEE", 
				"ECE", 
				"EEE", 
				'E', new ItemStack(Items.diamond), 
				'C', new ItemStack(QuantumDsuMk2));	
		
		GameRegistry.addRecipe(new ItemStack(QuantumDsuMk4), 
				"EEE", 
				"ECE", 
				"EEE", 
				'E', new ItemStack(Items.emerald), 
				'C', new ItemStack(QuantumDsuMk3));	
	}
}
