package QuantumStorage.items.upgrades;

import java.util.List;

import org.lwjgl.input.Keyboard;

import QuantumStorage.util.ItemNBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

public class ItemPickupUpgrade extends ItemUpgrade
{
	private static final String TAG_ITEMS = "InvItems";
	private static final String TAG_SLOT = "Slot";
	
	public ItemPickupUpgrade()
	{
		setUnlocalizedName("pickupupgrade");
	}
//	
//    @Override
//    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) 
//    {
//        player.openGui(QuantumStorage.INSTANCE, GuiHandler.pickupcard, world, 0, 0, 0);
//        return itemStack;
//    }
    
	public static ItemStack[] loadStacks(ItemStack stack) 
	{
		NBTTagList var2 = ItemNBTHelper.getList(stack, TAG_ITEMS, 10, false);
		ItemStack[] inventorySlots = new ItemStack[64];
		for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = var2.getCompoundTagAt(var3);
			byte var5 = var4.getByte(TAG_SLOT);
			if(var5 >= 0 && var5 < inventorySlots.length)
				inventorySlots[var5] = ItemStack.loadItemStackFromNBT(var4);
		}

		return inventorySlots;
	}

	public static void setStacks(ItemStack stack, ItemStack[] inventorySlots)
	{
		NBTTagList var2 = new NBTTagList();
		for(int var3 = 0; var3 < inventorySlots.length; ++var3)
			if(inventorySlots[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte(TAG_SLOT, (byte)var3);
				inventorySlots[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}

		ItemNBTHelper.setList(stack, TAG_ITEMS, var2);
	}
    
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) 
	{
		ItemStack[] baginv = loadStacks(stack);
		ItemStack stackAt = baginv[0];
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			if(stackAt != null)
				list.add("Stored Item: " + stackAt.getDisplayName());
		}
		else
		{
			list.add(StatCollector.translateToLocal("quantumstorage.holdshiftmessage"));
		}
	}
}
