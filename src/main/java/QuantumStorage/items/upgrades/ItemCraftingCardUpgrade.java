package QuantumStorage.items.upgrades;

import QuantumStorage.QuantumStorage;
import QuantumStorage.client.GuiHandler;
import QuantumStorage.util.InventoryHelper;
import QuantumStorage.util.ItemNBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemCraftingCardUpgrade extends ItemUpgrade
{
	private static final String TAG_ITEMS = "InvItems";
	private static final String TAG_SLOT = "Slot";
	
	public ItemCraftingCardUpgrade()
	{
		setUnlocalizedName("quantumCraftingCard");
	}
	
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) 
    {
        player.openGui(QuantumStorage.INSTANCE, GuiHandler.crafingcard, world, 0, 0, 0);
        return itemStack;
    }
    
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int s, float xs, float ys, float zs) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof IInventory) {
			if(!world.isRemote) {
				ForgeDirection side = ForgeDirection.getOrientation(s);
				IInventory inv = (IInventory) tile;
				ItemStack[] stacks = loadStacks(stack);
				ItemStack[] newStacks = new ItemStack[stacks.length];
				boolean putAny = false;

				int i = 0;
				for(ItemStack petal : stacks) {
					if(petal != null) {
						int count = InventoryHelper.testInventoryInsertion(inv, petal, side);
						InventoryHelper.insertItemIntoInventory(inv, petal, side, -1);

						ItemStack newPetal = petal.copy();
						if(newPetal.stackSize == 0)
							newPetal = null;

						newStacks[i] = newPetal;
						putAny |= count > 0;
					}

					i++;
				}

				setStacks(stack, newStacks);
				if(putAny && inv instanceof TileEntityChest) {
					inv = InventoryHelper.getInventory(inv);
					player.displayGUIChest(inv);
				}
			}

			return true;
		}
		return false;
	}
    
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
}
