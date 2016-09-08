package QuantumStorage.init;

import QuantumStorage.config.ConfigQuantumStorage;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes 
{
	public static void init()
	{
		addBlockRecipes();
	}
	
	public static void addBlockRecipes()
	{
		if(!ConfigQuantumStorage.disableDsu)
			GameRegistry.addRecipe(new ItemStack(ModBlocks.QuantumDsu), 
					"OOO",
					"ICI",
					"III",
					'I', new ItemStack(Items.IRON_INGOT),
					'O', new ItemStack(Blocks.OBSIDIAN),
					'C', new ItemStack(Blocks.CHEST));
		
		
		if(!ConfigQuantumStorage.disableTank)
			GameRegistry.addRecipe(new ItemStack(ModBlocks.QuantumTank),
					"OOO",
					"IBI",
					"III",
					'I', new ItemStack(Items.IRON_INGOT),
                    'O', new ItemStack(Blocks.OBSIDIAN),
					'B', new ItemStack(Items.BUCKET));
	}
}
