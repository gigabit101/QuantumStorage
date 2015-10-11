package QuantumStorage.items;

import java.util.List;

import QuantumStorage.QuantumStorage;
import QuantumStorage.block.BlockQuantumDsu;
import QuantumStorage.block.tile.TileQuantumDsu;
import QuantumStorage.client.GuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemLinkedBag extends ItemQuantumStorage
{	
	public ItemLinkedBag()
	{
		setUnlocalizedName("linkedbag");
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) 
	{
		Block block = world.getBlock(x, y, z);
		if(player.isSneaking())
		{
			if(block instanceof BlockQuantumDsu)
			{
				stack.stackTagCompound = new NBTTagCompound();
				stack.stackTagCompound.setInteger("xcord", x);
				stack.stackTagCompound.setInteger("ycord", y);
				stack.stackTagCompound.setInteger("zcord", z);
			}
		}
		return false;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) 
	{
		if(stack.stackTagCompound != null)
		{
			int cordX = stack.stackTagCompound.getInteger("xcord");
			int cordY = stack.stackTagCompound.getInteger("ycord");
			int cordZ = stack.stackTagCompound.getInteger("zcord");
	
			if(!player.isSneaking() && stack.stackTagCompound != null)
				if(world.getTileEntity(cordX, cordY, cordZ) instanceof TileQuantumDsu)
					player.openGui(QuantumStorage.INSTANCE, GuiHandler.dsu, world, cordX, cordY, cordZ);
				else
					player.addChatComponentMessage(new ChatComponentText("QSU is missing or not loaded"));
			return stack;
		}
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) 
	{
		if(stack != null && stack.stackTagCompound != null)
		{
			int cordX = stack.stackTagCompound.getInteger("xcord");
			int cordY = stack.stackTagCompound.getInteger("ycord");
			int cordZ = stack.stackTagCompound.getInteger("zcord");
			list.add("Linked to QSU @ X-" + cordX + " Y-" + cordY + " Z-" + cordZ);
		}
		if(stack.stackTagCompound == null)
		{
			list.add("Shift Click on a QSU to link to its inventory");
		}
	}
}
