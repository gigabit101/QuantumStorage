package QuantumStorage.init;

import QuantumStorage.items.ItemLinkedBag;
import QuantumStorage.items.ItemMisc;
import QuantumStorage.items.ItemQuantumBag;
import QuantumStorage.items.upgrades.ItemCraftingCardUpgrade;
import QuantumStorage.items.upgrades.ItemPickupUpgrade;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModItems 
{
	public static Item quantumBag;
	public static Item quantumCraftingCard;
	public static Item linkedBag;
	public static Item pickupCard;
	public static Item misc;
	
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
		
		addRecipes();
	}
	
	public static void addRecipes()
	{
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
	}
}
