package QuantumStorage.client;

import QuantumStorage.client.container.dsu.ContainerQuantumDsuMk1;
import QuantumStorage.client.gui.dsu.GuiQuantumDsuMk1;
import QuantumStorage.tile.qsu.TileQuantumDsuMk1;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	public static final int dsu = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if (ID == dsu) 
		{
			return new ContainerQuantumDsuMk1((TileQuantumDsuMk1) world.getTileEntity(new BlockPos(x, y, z)), player);
		}
//		if (ID == dsuMk1) 
//		{
//			return new ContainerQuantumDsuMk1((TileQuantumDsuMk1) world.getTileEntity(x, y, z), player);
//		}
//		if (ID == dsuMk2) 
//		{
//			return new ContainerQuantumDsuMk2((TileQuantumDsuMk2) world.getTileEntity(x, y, z), player);
//		}
//		if (ID == dsuMk3) 
//		{
//			return new ContainerQuantumDsuMk3((TileQuantumDsuMk3) world.getTileEntity(x, y, z), player);
//		}
//		if (ID == quantumbag) 
//		{
//			return new ContainerQuantumBag(player);
//		}
//		if (ID == crafingcard) 
//		{
//			return new ContainerCrafingCard(player);
//		}
//		if (ID == pickupcard) 
//		{
//			return new ContainerPickupCard(player);
//		}
//		if (ID == chest) 
//		{
//			return new ContainerQuantumChest((TileQuantumChest) world.getTileEntity(x, y, z), player);
//		}
//		if (ID == tankMk1) 
//		{
//			return new ContainerQuantumTankMk1((TileQuantumTankMk1) world.getTileEntity(x, y, z), player);
//		}
//		if (ID == tankMk2) 
//		{
//			return new ContainerQuantumTankMk2((TileQuantumTankMk2) world.getTileEntity(x, y, z), player);
//		}
//		if (ID == tankMk3) 
//		{
//			return new ContainerQuantumTankMk3((TileQuantumTankMk3) world.getTileEntity(x, y, z), player);
//		}
//		if (ID == tankMk4) 
//		{
//			return new ContainerQuantumTankMk4((TileQuantumTankMk4) world.getTileEntity(x, y, z), player);
//		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if (ID == dsu) 
		{
			return new GuiQuantumDsuMk1(player, (TileQuantumDsuMk1) world.getTileEntity(new BlockPos(x, y, z)));
		}
//		if (ID == dsuMk1) 
//		{
//			return new GuiQuantumDsuMk1(player, (TileQuantumDsuMk1) world.getTileEntity(x, y, z));
//		}
//		if (ID == dsuMk2) 
//		{
//			return new GuiQuantumDsuMk2(player, (TileQuantumDsuMk2) world.getTileEntity(x, y, z));
//		}
//		if (ID == dsuMk3) 
//		{
//			return new GuiQuantumDsuMk3(player, (TileQuantumDsuMk3) world.getTileEntity(x, y, z));
//		}
//		if (ID == quantumbag) 
//		{
//			return new GuiQuantumBag(player);
//		}
//		if (ID == crafingcard) 
//		{
//			return new GuiCraftingCard(player);
//		}
//		if (ID == pickupcard) 
//		{
//			return new GuiPickupCard(player);
//		}
//		if (ID == chest) 
//		{
//			return new GuiQuantumChest(player, (TileQuantumChest) world.getTileEntity(x, y, z));
//		}
//		if (ID == tankMk1) 
//		{
//			return new GuiQuantumTankMk1(player, (TileQuantumTankMk1) world.getTileEntity(x, y, z));
//		}
//		if (ID == tankMk2) 
//		{
//			return new GuiQuantumTankMk2(player, (TileQuantumTankMk2) world.getTileEntity(x, y, z));
//		}
//		if (ID == tankMk3) 
//		{
//			return new GuiQuantumTankMk3(player, (TileQuantumTankMk3) world.getTileEntity(x, y, z));
//		}
//		if (ID == tankMk4) 
//		{
//			return new GuiQuantumTankMk4(player, (TileQuantumTankMk4) world.getTileEntity(x, y, z));
//		}
		return null;
	}
}
