package QuantumStorage.compat.waila;

import java.util.ArrayList;
import java.util.List;

import QuantumStorage.block.tile.TileQuantumDsuMk1;
import QuantumStorage.block.tile.TileQuantumDsuMk2;
import QuantumStorage.block.tile.TileQuantumDsuMk3;
import QuantumStorage.block.tile.TileQuantumDsuMk4;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WailaProviderMachines implements IWailaDataProvider 
{
	private List<String> info = new ArrayList<String>();

	@Override
	public List<String> getWailaBody(ItemStack item, List<String> tip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		TileQuantumDsuMk1 mk1 = (TileQuantumDsuMk1) accessor.getTileEntity();
		TileQuantumDsuMk2 mk2 = (TileQuantumDsuMk2) accessor.getTileEntity();
		TileQuantumDsuMk3 mk3 = (TileQuantumDsuMk3) accessor.getTileEntity();
		TileQuantumDsuMk4 mk4 = (TileQuantumDsuMk4) accessor.getTileEntity();

		mk1.addWailaInfo(info);
		mk2.addWailaInfo(info);
		mk3.addWailaInfo(info);
		mk4.addWailaInfo(info);
		tip.addAll(info);
		info.clear();

		return tip;
	}

	@Override
	public List<String> getWailaHead(ItemStack item, List<String> tip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return tip;
	}

	@Override
	public List<String> getWailaTail(ItemStack item, List<String> tip, IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return tip;
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) 
	{
		return null;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World w, int x, int y, int z) 
	{
		return tag;
	}
}
