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
	}
}
