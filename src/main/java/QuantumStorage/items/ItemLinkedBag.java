package QuantumStorage.items;

import java.util.List;

import org.lwjgl.input.Keyboard;

import QuantumStorage.QuantumStorage;
import QuantumStorage.block.BlockQuantumDsuMk1;
import QuantumStorage.block.BlockQuantumDsuMk2;
import QuantumStorage.block.BlockQuantumDsuMk3;
import QuantumStorage.block.BlockQuantumDsuMk4;
import QuantumStorage.block.tile.TileQuantumDsuMk1;
import QuantumStorage.block.tile.TileQuantumDsuMk2;
import QuantumStorage.block.tile.TileQuantumDsuMk3;
import QuantumStorage.block.tile.TileQuantumDsuMk4;
import QuantumStorage.client.GuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

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
			if(block instanceof BlockQuantumDsuMk1 || block instanceof BlockQuantumDsuMk2 || block instanceof BlockQuantumDsuMk3 || block instanceof BlockQuantumDsuMk4)
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
	
			if(!world.isRemote && !player.isSneaking() && stack.stackTagCompound != null)
			{
				if(world.getTileEntity(cordX, cordY, cordZ) instanceof TileQuantumDsuMk1)
					player.openGui(QuantumStorage.INSTANCE, GuiHandler.dsuMk1, world, cordX, cordY, cordZ);
				if(world.getTileEntity(cordX, cordY, cordZ) instanceof TileQuantumDsuMk2)
					player.openGui(QuantumStorage.INSTANCE, GuiHandler.dsuMk2, world, cordX, cordY, cordZ);
				if(world.getTileEntity(cordX, cordY, cordZ) instanceof TileQuantumDsuMk3)
					player.openGui(QuantumStorage.INSTANCE, GuiHandler.dsuMk3, world, cordX, cordY, cordZ);
				if(world.getTileEntity(cordX, cordY, cordZ) instanceof TileQuantumDsuMk4)
					player.openGui(QuantumStorage.INSTANCE, GuiHandler.dsu, world, cordX, cordY, cordZ);
			}
				else if(world.getTileEntity(cordX, cordY, cordZ) == null)
					player.addChatComponentMessage(new ChatComponentText("QSU is missing or not loaded"));
			return stack;
		}
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) 
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
		{
			if(stack != null && stack.stackTagCompound != null)
			{
				int cordX = stack.stackTagCompound.getInteger("xcord");
				int cordY = stack.stackTagCompound.getInteger("ycord");
				int cordZ = stack.stackTagCompound.getInteger("zcord");
				list.add("Linked to QSU @ X=" + cordX + " Y=" + cordY + " Z=" + cordZ);
			}
			if(stack.stackTagCompound == null)
			{
				list.add("Shift Click on a QSU to link to its inventory");
			}
		}
		else list.add(StatCollector.translateToLocal("quantumstorage.holdshiftmessage"));
	}
}
