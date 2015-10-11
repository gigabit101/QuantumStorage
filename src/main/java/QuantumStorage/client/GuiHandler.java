package QuantumStorage.client;

import QuantumStorage.block.tile.TileQuantumDsu;
import QuantumStorage.client.container.ContainerCrafingCard;
import QuantumStorage.client.container.ContainerQuantumBag;
import QuantumStorage.client.container.ContainerQuantumDsu;
import QuantumStorage.client.gui.GuiCraftingCard;
import QuantumStorage.client.gui.GuiQuantumBag;
import QuantumStorage.client.gui.GuiQuantumDsu;
import QuantumStorage.init.ModItems;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler 
{
	public static final int dsu = 0;
	public static final int quantumbag = 1;
	public static final int crafingcard = 2;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if (ID == dsu) 
		{
			return new ContainerQuantumDsu((TileQuantumDsu) world.getTileEntity(x, y, z), player);
		}
		if (ID == quantumbag) 
		{
			return new ContainerQuantumBag(player.inventory, world);
		}
		if (ID == crafingcard) 
		{
			return new ContainerCrafingCard(player.inventory, world);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if (ID == dsu) 
		{
			return new GuiQuantumDsu(player, (TileQuantumDsu) world.getTileEntity(x, y, z));
		}
		if (ID == quantumbag) 
		{
			return new GuiQuantumBag(player.inventory, world);
		}
		if (ID == crafingcard) 
		{
			return new GuiCraftingCard(player.inventory, world);
		}
		return null;
	}
}
