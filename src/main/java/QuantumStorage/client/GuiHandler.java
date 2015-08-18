package QuantumStorage.client;

import QuantumStorage.block.tile.TileQuantumDsu;
import QuantumStorage.client.container.ContainerQuantumDsu;
import QuantumStorage.client.gui.GuiQuantumDsu;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
	public static final int dsu = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == dsu) {
			return new ContainerQuantumDsu((TileQuantumDsu) world.getTileEntity(x, y, z), player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == dsu) {
			return new GuiQuantumDsu(player, (TileQuantumDsu) world.getTileEntity(x, y, z));
		}
		return null;
	}

}
