package QuantumStorage.client;

import QuantumStorage.client.container.ContainerCrafingCard;
import QuantumStorage.client.container.ContainerPickupCard;
import QuantumStorage.client.container.ContainerQuantumBag;
import QuantumStorage.client.container.ContainerQuantumChest;
import QuantumStorage.client.container.dsu.ContainerQuantumDsuMk1;
import QuantumStorage.client.container.dsu.ContainerQuantumDsuMk2;
import QuantumStorage.client.container.dsu.ContainerQuantumDsuMk3;
import QuantumStorage.client.container.dsu.ContainerQuantumDsuMk4;
import QuantumStorage.client.container.qst.ContainerQuantumTankMk1;
import QuantumStorage.client.container.qst.ContainerQuantumTankMk2;
import QuantumStorage.client.container.qst.ContainerQuantumTankMk3;
import QuantumStorage.client.container.qst.ContainerQuantumTankMk4;
import QuantumStorage.client.gui.GuiCraftingCard;
import QuantumStorage.client.gui.GuiPickupCard;
import QuantumStorage.client.gui.GuiQuantumBag;
import QuantumStorage.client.gui.GuiQuantumChest;
import QuantumStorage.client.gui.dsu.GuiQuantumDsuMk1;
import QuantumStorage.client.gui.dsu.GuiQuantumDsuMk2;
import QuantumStorage.client.gui.dsu.GuiQuantumDsuMk3;
import QuantumStorage.client.gui.dsu.GuiQuantumDsuMk4;
import QuantumStorage.client.gui.qst.GuiQuantumTankMk1;
import QuantumStorage.client.gui.qst.GuiQuantumTankMk2;
import QuantumStorage.client.gui.qst.GuiQuantumTankMk3;
import QuantumStorage.client.gui.qst.GuiQuantumTankMk4;
import QuantumStorage.tile.TileQuantumChest;
import QuantumStorage.tile.qst.TileQuantumTankMk1;
import QuantumStorage.tile.qst.TileQuantumTankMk2;
import QuantumStorage.tile.qst.TileQuantumTankMk3;
import QuantumStorage.tile.qst.TileQuantumTankMk4;
import QuantumStorage.tile.qsu.TileQuantumDsuMk1;
import QuantumStorage.tile.qsu.TileQuantumDsuMk2;
import QuantumStorage.tile.qsu.TileQuantumDsuMk3;
import QuantumStorage.tile.qsu.TileQuantumDsuMk4;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler 
{
	public static final int dsu = 0;
	public static final int quantumbag = 1;
	public static final int crafingcard = 2;
	public static final int pickupcard = 3;
	public static final int dsuMk1 = 4;
	public static final int dsuMk2 = 5;
	public static final int dsuMk3 = 6;
	public static final int chest = 7;
	public static final int tankMk1 = 8;
	public static final int tankMk2 = 9;
	public static final int tankMk3 = 10;
	public static final int tankMk4 = 11;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if (ID == dsu) 
		{
			return new ContainerQuantumDsuMk4((TileQuantumDsuMk4) world.getTileEntity(x, y, z), player);
		}
		if (ID == dsuMk1) 
		{
			return new ContainerQuantumDsuMk1((TileQuantumDsuMk1) world.getTileEntity(x, y, z), player);
		}
		if (ID == dsuMk2) 
		{
			return new ContainerQuantumDsuMk2((TileQuantumDsuMk2) world.getTileEntity(x, y, z), player);
		}
		if (ID == dsuMk3) 
		{
			return new ContainerQuantumDsuMk3((TileQuantumDsuMk3) world.getTileEntity(x, y, z), player);
		}
		if (ID == quantumbag) 
		{
			return new ContainerQuantumBag(player);
		}
		if (ID == crafingcard) 
		{
			return new ContainerCrafingCard(player);
		}
		if (ID == pickupcard) 
		{
			return new ContainerPickupCard(player);
		}
		if (ID == chest) 
		{
			return new ContainerQuantumChest((TileQuantumChest) world.getTileEntity(x, y, z), player);
		}
		if (ID == tankMk1) 
		{
			return new ContainerQuantumTankMk1((TileQuantumTankMk1) world.getTileEntity(x, y, z), player);
		}
		if (ID == tankMk2) 
		{
			return new ContainerQuantumTankMk2((TileQuantumTankMk2) world.getTileEntity(x, y, z), player);
		}
		if (ID == tankMk3) 
		{
			return new ContainerQuantumTankMk3((TileQuantumTankMk3) world.getTileEntity(x, y, z), player);
		}
		if (ID == tankMk4) 
		{
			return new ContainerQuantumTankMk4((TileQuantumTankMk4) world.getTileEntity(x, y, z), player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if (ID == dsu) 
		{
			return new GuiQuantumDsuMk4(player, (TileQuantumDsuMk4) world.getTileEntity(x, y, z));
		}
		if (ID == dsuMk1) 
		{
			return new GuiQuantumDsuMk1(player, (TileQuantumDsuMk1) world.getTileEntity(x, y, z));
		}
		if (ID == dsuMk2) 
		{
			return new GuiQuantumDsuMk2(player, (TileQuantumDsuMk2) world.getTileEntity(x, y, z));
		}
		if (ID == dsuMk3) 
		{
			return new GuiQuantumDsuMk3(player, (TileQuantumDsuMk3) world.getTileEntity(x, y, z));
		}
		if (ID == quantumbag) 
		{
			return new GuiQuantumBag(player);
		}
		if (ID == crafingcard) 
		{
			return new GuiCraftingCard(player);
		}
		if (ID == pickupcard) 
		{
			return new GuiPickupCard(player);
		}
		if (ID == chest) 
		{
			return new GuiQuantumChest(player, (TileQuantumChest) world.getTileEntity(x, y, z));
		}
		if (ID == tankMk1) 
		{
			return new GuiQuantumTankMk1(player, (TileQuantumTankMk1) world.getTileEntity(x, y, z));
		}
		if (ID == tankMk2) 
		{
			return new GuiQuantumTankMk2(player, (TileQuantumTankMk2) world.getTileEntity(x, y, z));
		}
		if (ID == tankMk3) 
		{
			return new GuiQuantumTankMk3(player, (TileQuantumTankMk3) world.getTileEntity(x, y, z));
		}
		if (ID == tankMk4) 
		{
			return new GuiQuantumTankMk4(player, (TileQuantumTankMk4) world.getTileEntity(x, y, z));
		}
		return null;
	}
}
