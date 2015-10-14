package QuantumStorage;

import QuantumStorage.init.ModBlocks;
import QuantumStorage.lib.ModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabQuantumStorage extends CreativeTabs
{
	public static CreativeTabQuantumStorage instance = new CreativeTabQuantumStorage();

	public CreativeTabQuantumStorage() 
	{
		super(ModInfo.MOD_ID);
	}

	@Override
	public Item getTabIconItem() 
	{
		return Item.getItemFromBlock(ModBlocks.QuantumDsuMk4);
	}
}
