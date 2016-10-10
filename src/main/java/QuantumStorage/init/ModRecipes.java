package QuantumStorage.init;

import QuantumStorage.config.ConfigQuantumStorage;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import reborncore.RebornRegistry;
import reborncore.common.util.CraftingHelper;

public class ModRecipes 
{
	public static void init()
	{
		addBlockRecipes();
	}
	
	public static void addBlockRecipes()
	{
		if(!ConfigQuantumStorage.disableDsu)
			CraftingHelper.addShapedOreRecipe(new ItemStack(ModBlocks.QuantumDsu),
					"OOO",
					"ICI",
					"III",
					'I', new ItemStack(Items.IRON_INGOT),
					'O', new ItemStack(Blocks.OBSIDIAN),
					'C', "chest");
		
		
		if(!ConfigQuantumStorage.disableTank)
			CraftingHelper.addShapedOreRecipe(new ItemStack(ModBlocks.QuantumTank),
					"OOO",
					"IBI",
					"III",
					'I', new ItemStack(Items.IRON_INGOT),
                    'O', new ItemStack(Blocks.OBSIDIAN),
					'B', new ItemStack(Items.BUCKET));
	}
}
