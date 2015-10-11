package QuantumStorage.init;

import QuantumStorage.block.BlockQuantumDsu;
import QuantumStorage.block.tile.TileQuantumDsu;
import QuantumStorage.items.ItemQuantumStorage;
import QuantumStorage.items.itemblocks.ItemBlockQuantumDsu;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModBlocks {
	public static Block QuantumDsu;

	public static void init() 
	{
		QuantumDsu = new BlockQuantumDsu(Material.iron);
		GameRegistry.registerBlock(QuantumDsu, ItemBlockQuantumDsu.class, "quantumdsu");
		GameRegistry.registerTileEntity(TileQuantumDsu.class, "tilequantumdsu");
		addRecipes();
	}

	public static void addRecipes() 
	{
		GameRegistry.addRecipe(new ItemStack(QuantumDsu, 1, 0), "EEE", "ECE", "EEE", 'E',
				new ItemStack(Items.ender_pearl), 'C', new ItemStack(Blocks.ender_chest));
	}
}
