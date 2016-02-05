package QuantumStorage.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import QuantumStorage.CreativeTabQuantumStorage;
import QuantumStorage.lib.ModInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemQuantumStorage extends Item
{
	public ItemQuantumStorage()
	{
		setMaxStackSize(1);
		setCreativeTab(CreativeTabQuantumStorage.instance);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) 
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			list.add(StatCollector.translateToLocal(stack.getUnlocalizedName() + ".tooltip"));
		else
			list.add(StatCollector.translateToLocal("quantumstorage.holdshiftmessage"));
	}
}
