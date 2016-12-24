package QuantumStorage.client;

import QuantumStorage.client.container.ContainerMultiTank;
import QuantumStorage.client.container.ContainerQChest;
import QuantumStorage.client.container.ContainerQuantumDsu;
import QuantumStorage.client.container.ContainerQuantumTank;
import QuantumStorage.client.gui.GuiMultiTank;
import QuantumStorage.client.gui.GuiQChest;
import QuantumStorage.client.gui.GuiQuantumDsu;
import QuantumStorage.client.gui.GuiQuantumTank;
import QuantumStorage.tile.TileMultiTank;
import QuantumStorage.tile.TileQuantumDsu;
import QuantumStorage.tile.TileQuantumTank;
import QuantumStorage.tile.prefab.TileQChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	public static final int dsu = 0;
	public static final int tank = 1;
	public static final int chest = 2;
	public static final int multitank = 3;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if (ID == dsu) 
		{
			return new ContainerQuantumDsu((TileQuantumDsu) world.getTileEntity(new BlockPos(x, y, z)), player);
		}
		if (ID == tank) 
		{
			return new ContainerQuantumTank((TileQuantumTank) world.getTileEntity(new BlockPos (x, y, z)), player);
		}
        if (ID == chest)
        {
            return new ContainerQChest((TileQChest) world.getTileEntity(new BlockPos (x, y, z)), player);
        }
        if(ID == multitank)
        {
            return new ContainerMultiTank((TileMultiTank) world.getTileEntity(new BlockPos(x, y, z)), player);
        }
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if (ID == dsu) 
		{
			return new GuiQuantumDsu(player, (TileQuantumDsu) world.getTileEntity(new BlockPos(x, y, z)));
		}
		if (ID == tank) 
		{
			return new GuiQuantumTank(player, (TileQuantumTank) world.getTileEntity(new BlockPos(x, y, z)));
		}
        if (ID == chest)
        {
            return new GuiQChest(player, (TileQChest) world.getTileEntity(new BlockPos(x, y, z)));
        }
        if (ID == multitank)
        {
            return new GuiMultiTank((TileMultiTank) world.getTileEntity(new BlockPos(x, y, z)), player);
        }
		return null;
	}
}
