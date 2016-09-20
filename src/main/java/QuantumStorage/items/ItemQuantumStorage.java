package QuantumStorage.items;

import QuantumStorage.CreativeTabQuantumStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.input.Keyboard;

import java.util.List;

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
			list.add(I18n.translateToLocal(stack.getUnlocalizedName() + ".tooltip"));
		else
			list.add(I18n.translateToLocal("quantumstorage.holdshiftmessage"));
	}
}
