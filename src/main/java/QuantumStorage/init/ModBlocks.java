package QuantumStorage.init;

import QuantumStorage.block.BlockQuantumStorage;
import QuantumStorage.block.tile.TileQuantumStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModBlocks {
	public static Block simpleDsu;

	public static void init() {
		simpleDsu = new BlockQuantumStorage(Material.iron);
		GameRegistry.registerBlock(simpleDsu, "simpleDsu");
		GameRegistry.registerTileEntity(TileQuantumStorage.class, "tilesimpledsu");
		addRecipes();
	}

	public static void addRecipes() {
		GameRegistry.addRecipe(new ItemStack(simpleDsu, 1, 0), "EEE", "ECE", "EEE", 'E',
				new ItemStack(Items.ender_pearl), 'C', new ItemStack(Blocks.chest));
	}
}
