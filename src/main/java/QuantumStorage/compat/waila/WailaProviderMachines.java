package QuantumStorage.compat.waila;

import java.util.ArrayList;
import java.util.List;

import QuantumStorage.tile.TileQuantumStorage;
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
		TileQuantumStorage tile = (TileQuantumStorage) accessor.getTileEntity();


		tile.addWailaInfo(info);

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
