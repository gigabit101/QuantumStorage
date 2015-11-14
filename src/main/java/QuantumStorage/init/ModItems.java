package QuantumStorage.init;

import QuantumStorage.items.ItemLinkedBag;
import QuantumStorage.items.ItemMisc;
import QuantumStorage.items.ItemQsuUpgrade;
import QuantumStorage.items.ItemQuantumBag;
import QuantumStorage.items.upgrades.ItemCraftingCardUpgrade;
import QuantumStorage.items.upgrades.ItemPickupUpgrade;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import reborncore.common.util.CraftingHelper;

public class ModItems 
{
	public static Item quantumBag;
	public static Item quantumCraftingCard;
	public static Item linkedBag;
	public static Item pickupCard;
	public static Item misc;
	public static Item qsuUpgrades;
	
	public static void init()
	{
		quantumBag = new ItemQuantumBag();
		GameRegistry.registerItem(quantumBag, "quantumbag");
		
		quantumCraftingCard = new ItemCraftingCardUpgrade();
		GameRegistry.registerItem(quantumCraftingCard, "quantumcraftingcard");
		
		linkedBag = new ItemLinkedBag();
		GameRegistry.registerItem(linkedBag, "linkedbag");
		
		pickupCard = new ItemPickupUpgrade();
		GameRegistry.registerItem(pickupCard, "pickupcard");
		
		misc = new ItemMisc();
		GameRegistry.registerItem(misc, "misc");
		
		qsuUpgrades = new ItemQsuUpgrade();
		GameRegistry.registerItem(qsuUpgrades, "qsuupgrade");
		
		addRecipes();
	}
	
	public static void addRecipes()
	{
		//Im Lazy
		int i;
		for(i = 0; i < 15; ++i)
		{
			OreDictionary.registerOre("quantumbag", new ItemStack(quantumBag, 1, i));
		}
		
		GameRegistry.addRecipe(new ItemStack(misc, 1, 1), 
				"EEE", 
				"ECE", 
				"EEE", 
				'E', new ItemStack(Items.paper), 
				'C', new ItemStack(Blocks.redstone_torch));
		
		GameRegistry.addRecipe(new ItemStack(misc, 1, 0), 
				" E ", 
				"ECE", 
				" E ", 
				'E', new ItemStack(Items.ender_pearl), 
				'C', new ItemStack(Items.diamond));
		
		GameRegistry.addShapelessRecipe(new ItemStack(pickupCard),
				new ItemStack(misc, 1, 1), 
				new ItemStack(Items.ender_pearl));
				
		GameRegistry.addRecipe(new ItemStack(linkedBag, 1, 0), 
				"EEE", 
				"ECE", 
				"EEE", 
				'E', new ItemStack(Items.leather), 
				'C', new ItemStack(misc ,1 ,0));
		
		GameRegistry.addRecipe(new ItemStack(quantumBag, 1, 0), 
				"EEE", 
				"ECE", 
				"LLL", 
				'L', new ItemStack(Items.leather), 
				'E', new ItemStack(Blocks.wool),
				'C', new ItemStack(misc ,1 ,0));
		//Upgrades		
		GameRegistry.addRecipe(new ItemStack(qsuUpgrades, 1, 0), 
				"EEE", 
				"ECE", 
				"EEE", 
				'E', new ItemStack(Items.gold_ingot), 
				'C', new ItemStack(misc, 1, 1));	
		
		GameRegistry.addRecipe(new ItemStack(qsuUpgrades, 1, 1), 
				"EEE", 
				"ECE", 
				"EEE", 
				'E', new ItemStack(Items.diamond), 
				'C', new ItemStack(misc ,1 ,1));	
		
		GameRegistry.addRecipe(new ItemStack(qsuUpgrades, 1, 2), 
				"EEE", 
				"ECE", 
				"EEE", 
				'E', new ItemStack(Items.emerald), 
				'C', new ItemStack(misc,1 ,1));	
		
		//Colour Bags
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 0), 
				"quantumbag",
				"dyeWhite");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 1), 
				"quantumbag",
				"dyeOrange");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 2), 
				"quantumbag",
				"dyeMagenta");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 3), 
				"quantumbag",
				"dyeLightBlue");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 4), 
				"quantumbag",
				"dyeYellow");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 5), 
				"quantumbag",
				"dyeLime");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 6), 
				"quantumbag",
				"dyePink");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 7), 
				"quantumbag",
				"dyeGray");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 8), 
				"quantumbag",
				"dyeLightGray");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 9), 
				"quantumbag",
				"dyeCyan");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 10), 
				"quantumbag",
				"dyePurple");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 11), 
				"quantumbag",
				"dyeBlue");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 12), 
				"quantumbag",
				"dyeBrown");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 13), 
				"quantumbag",
				"dyeGreen");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 14), 
				"quantumbag",
				"dyeRed");
		
		CraftingHelper.addShapelessOreRecipe(new ItemStack(quantumBag ,1, 15), 
				"quantumbag",
				"dyeBlack");
	}
}
