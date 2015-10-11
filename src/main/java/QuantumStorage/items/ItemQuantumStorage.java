package QuantumStorage.items;

import QuantumStorage.CreativeTabQuantumStorage;
import QuantumStorage.lib.ModInfo;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemQuantumStorage extends Item
{
	public ItemQuantumStorage()
	{
		setMaxStackSize(1);
		setCreativeTab(CreativeTabQuantumStorage.instance);
	}
	
	@Override
	public void registerIcons(IIconRegister iconRegister) 
	{
		itemIcon = iconRegister.registerIcon(ModInfo.MOD_ID.toLowerCase() + ":" + getUnlocalizedName().toLowerCase().substring(5));
	}
}
