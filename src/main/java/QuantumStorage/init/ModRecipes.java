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
					"EEE", 
					"ECE", 
					"EEE", 
					'E', new ItemStack(Blocks.chest), 
					'C', new ItemStack(Items.emerald));	
		
		
		if(!ConfigQuantumStorage.disableTank)
			GameRegistry.addRecipe(new ItemStack(ModBlocks.QuantumTank), 
					"EEE", 
					"ECE", 
					"EEE", 
					'E', new ItemStack(Items.bucket), 
					'C', new ItemStack(Items.emerald));	
	}
}
