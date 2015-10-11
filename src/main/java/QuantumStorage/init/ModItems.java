package QuantumStorage.init;

import QuantumStorage.items.ItemCraftingCard;
import QuantumStorage.items.ItemLinkedBag;
import QuantumStorage.items.ItemQuantumBag;
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
	
	public static void init()
	{
		quantumBag = new ItemQuantumBag();
		GameRegistry.registerItem(quantumBag, "quantumbag");
		
		quantumCraftingCard = new ItemCraftingCard();
		GameRegistry.registerItem(quantumCraftingCard, "quantumcraftingcard");
		
		linkedBag = new ItemLinkedBag();
		GameRegistry.registerItem(linkedBag, "linkedbag");
		
		addRecipes();
	}
	
	public static void addRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(linkedBag, 1, 0), 
				"EEE", 
				"ECE", 
				"EEE", 
				'E', new ItemStack(Items.leather), 
				'C', new ItemStack(Blocks.ender_chest));
	}
}
