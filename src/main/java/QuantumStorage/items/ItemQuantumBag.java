package QuantumStorage.items;

import java.awt.Color;
import java.util.List;

import org.lwjgl.Sys;

import QuantumStorage.QuantumStorage;
import QuantumStorage.client.GuiHandler;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.items.upgrades.ItemPickupUpgrade;
import QuantumStorage.util.InventoryHelper;
import QuantumStorage.util.ItemNBTHelper;
import QuantumStorage.util.ItemUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import scala.reflect.internal.Trees.Return;

public class ItemQuantumBag extends ItemQuantumStorage
{
	public static final String[]COLORS = new String[] {"white", "orange", "magenta", "lightBlue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};

	private static final int[] colorCache = new int[COLORS.length];
	private static final String TAG_ITEMS = "InvItems";
	private static final String TAG_SLOT = "Slot";
	
	public ItemQuantumBag()
	{
		setUnlocalizedName("quantumBag");
        setHasSubtypes(true);
        MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onPickupItem(EntityItemPickupEvent event) 
	{		
		ItemStack stack = event.item.getEntityItem();
		if(stack.stackSize > 0)
		{
			for(int i = 0; i < event.entityPlayer.inventory.getSizeInventory(); i++) 
			{
				if(i == event.entityPlayer.inventory.currentItem)
					continue; // prevent item deletion

				ItemStack invStack = event.entityPlayer.inventory.getStackInSlot(i);
				ItemStack target = getTarget(invStack);
				if(target != null && ItemUtils.isItemEqual(stack, target, true, true))
					continue;

				if(invStack != null && invStack.getItem() == this) 
				{
					for(i = 0; i < 54; ++i)
					{
						ItemStack[] bagInv = loadStacks(invStack);
						ItemStack stackAt = bagInv[i];

						boolean didChange = false;
						if(stackAt == null) 
						{
							bagInv[i] = stack.copy();
							stack.stackSize = 0;
							didChange = true;
							if(didChange)
								setStacks(invStack, bagInv);
							
							break;							
						}
						else if (ItemUtils.isItemEqual(target, stackAt, true, true))
						{
							int stackAtSize = stackAt.stackSize;
							int stackSize = stack.stackSize;
							int spare = 64 - stackAtSize;
							int pass = Math.min(spare, stackSize);
							if(pass > 0) 
							{
								stackAt.stackSize += pass;
								stack.stackSize -= pass;
								didChange = true;
							}
							if(didChange)
								setStacks(invStack, bagInv);
						}
						if(didChange)
							setStacks(invStack, bagInv);
					} 

				}
				if(stack.stackSize == 0)
					return;
			}
		}
	}
	
	public ItemStack getTarget(ItemStack bagStack)
	{
		int i;
		for(i = 54; i < 59; ++i)
		{
			loadStacks(bagStack);
			ItemStack[] bagInv = loadStacks(bagStack);
			ItemStack stackUpgrade = bagInv[i];
			if(stackUpgrade != null)
			{
				ItemStack[] upgradeInv = loadStacks(stackUpgrade);
				ItemStack target = upgradeInv[0];
				if(target != null)
				return target;
				break;
			}
		}
		return null;
	}
	
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) 
    {
        player.openGui(QuantumStorage.INSTANCE, GuiHandler.quantumbag, world, 0, 0, 0);
        return itemStack;
    }
    

    @Override
    public int getColorFromItemStack(ItemStack itemStack, int pass) {
        int cachedColor = colorCache[itemStack.getItemDamage()];
        if (cachedColor == 0) {
            float[] colorArray = EntitySheep.fleeceColorTable[itemStack.getItemDamage()];
            Color color = new Color(colorArray[0], colorArray[1], colorArray[2]).brighter();
            colorCache[itemStack.getItemDamage()] = color.getRGB();
        }
        return cachedColor;
    }

    public String[] getNames() 
    {
        return COLORS;
    }

    public String getIconPrefix() 
    {
        return null;
    }

    public String getOverrideIcon() 
    {
        return "quantumbag";
    }
    
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int meta = 0; meta < COLORS.length; ++meta) 
        {
            list.add(new ItemStack(item, 1, meta));
        }
    }
    
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int s, float xs, float ys, float zs) 
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof IInventory) 
		{
			if(!world.isRemote) {
				ForgeDirection side = ForgeDirection.getOrientation(s);
				IInventory inv = (IInventory) tile;
				ItemStack[] stacks = loadStacks(stack);
				ItemStack[] newStacks = new ItemStack[stacks.length];
				boolean putAny = false;

				int i = 0;
				for(ItemStack petal : stacks) 
				{
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
				if(putAny && inv instanceof TileEntityChest) 
				{
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
