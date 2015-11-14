package QuantumStorage.init;

import QuantumStorage.config.ConfigQuantumStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import reborncore.common.util.CraftingHelper;

public class ModRecipes 
{
	public static void init()
	{
		addBlockRecipes();
		addItemRecipes();
		addUpgradeRecipes();
		if(!ConfigQuantumStorage.disableQuantumBag)
			addBagRecipes();
	}
	
	public static void addBlockRecipes()
	{
		if(!ConfigQuantumStorage.disableqsumk1)
			GameRegistry.addRecipe(new ItemStack(ModBlocks.QuantumDsuMk1), 
					"EEE", 
					"ECE", 
					"EEE", 
					'E', new ItemStack(Blocks.chest), 
					'C', new ItemStack(ModItems.misc));	
		
		if(!ConfigQuantumStorage.disableqsumk2)
			GameRegistry.addRecipe(new ItemStack(ModBlocks.QuantumDsuMk2), 
					"EEE", 
					"ECE", 
					"EEE", 
					'E', new ItemStack(Items.gold_ingot), 
					'C', new ItemStack(ModBlocks.QuantumDsuMk1));	
		
		if(!ConfigQuantumStorage.disableqsumk3)
			GameRegistry.addRecipe(new ItemStack(ModBlocks.QuantumDsuMk3), 
					"EEE", 
					"ECE", 
					"EEE", 
					'E', new ItemStack(Items.diamond), 
					'C', new ItemStack(ModBlocks.QuantumDsuMk2));	
		if(!ConfigQuantumStorage.disableqsumk4)
			GameRegistry.addRecipe(new ItemStack(ModBlocks.QuantumDsuMk4), 
					"EEE", 
					"ECE", 
					"EEE", 
					'E', new ItemStack(Items.emerald), 
					'C', new ItemStack(ModBlocks.QuantumDsuMk3));	
	}
	
	public static void addBagRecipes()
	{		
		GameRegistry.addRecipe(new ItemStack(ModItems.quantumBag, 1, 0), 
				"EEE", 
				"ECE", 
				"LLL", 
				'L', new ItemStack(Items.leather), 
				'E', new ItemStack(Blocks.wool),
				'C', new ItemStack(ModItems.misc ,1 ,0));
		
		//Im Lazy
		int i;
		for(i = 0; i < 15; ++i)
		{
			OreDictionary.registerOre("quantumbag", new ItemStack(ModItems.quantumBag, 1, i));
		}
		
		//Colour Bags
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 0), 
				"quantumbag",
				"dyeWhite");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 1), 
				"quantumbag",
				"dyeOrange");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 2), 
				"quantumbag",
				"dyeMagenta");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 3), 
				"quantumbag",
				"dyeLightBlue");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 4), 
				"quantumbag",
				"dyeYellow");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 5), 
				"quantumbag",
				"dyeLime");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 6), 
				"quantumbag",
				"dyePink");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 7), 
				"quantumbag",
				"dyeGray");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 8), 
				"quantumbag",
				"dyeLightGray");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 9), 
				"quantumbag",
				"dyeCyan");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 10), 
				"quantumbag",
				"dyePurple");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 11), 
				"quantumbag",
				"dyeBlue");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 12), 
				"quantumbag",
				"dyeBrown");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 13), 
				"quantumbag",
				"dyeGreen");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 14), 
				"quantumbag",
				"dyeRed");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(ModItems.quantumBag ,1, 15), 
				"quantumbag",
				"dyeBlack");
	}
	
	public static void addItemRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(ModItems.misc, 1, 1), 
				"EEE", 
				"ECE", 
				"EEE", 
				'E', new ItemStack(Items.paper), 
				'C', new ItemStack(Blocks.redstone_torch));
		
		GameRegistry.addRecipe(new ItemStack(ModItems.misc, 1, 0), 
				" E ", 
				"ECE", 
				" E ", 
				'E', new ItemStack(Items.ender_pearl), 
				'C', new ItemStack(Items.diamond));
		if(!ConfigQuantumStorage.disableLinkedBag)
			GameRegistry.addRecipe(new ItemStack(ModItems.linkedBag, 1, 0), 
					"EEE", 
					"ECE", 
					"EEE", 
					'E', new ItemStack(Items.leather), 
					'C', new ItemStack(ModItems.misc ,1 ,0));
	}
	
	public static void addUpgradeRecipes()
	{
		//Upgrades
		if(!ConfigQuantumStorage.disableMk2Upgrade)
			GameRegistry.addRecipe(new ItemStack(ModItems.qsuUpgrades, 1, 0), 
					"EEE", 
					"ECE", 
					"EEE", 
					'E', new ItemStack(Items.gold_ingot), 
					'C', new ItemStack(ModItems.misc, 1, 1));	
		if(!ConfigQuantumStorage.disableMk3Upgrade)
			GameRegistry.addRecipe(new ItemStack(ModItems.qsuUpgrades, 1, 1), 
					"EEE", 
					"ECE", 
					"EEE", 
					'E', new ItemStack(Items.diamond), 
					'C', new ItemStack(ModItems.misc ,1 ,1));	
		if(!ConfigQuantumStorage.disableMk3Upgrade)
			GameRegistry.addRecipe(new ItemStack(ModItems.qsuUpgrades, 1, 2), 
					"EEE", 
					"ECE", 
					"EEE", 
					'E', new ItemStack(Items.emerald), 
					'C', new ItemStack(ModItems.misc,1 ,1));
		
		if(!ConfigQuantumStorage.disableAutoPickupUpgrade)
			GameRegistry.addShapelessRecipe(new ItemStack(ModItems.pickupCard),
					new ItemStack(ModItems.misc, 1, 1), 
					new ItemStack(Items.ender_pearl));
	}
}
