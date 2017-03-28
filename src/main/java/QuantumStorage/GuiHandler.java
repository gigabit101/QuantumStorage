package QuantumStorage;

import QuantumStorage.client.AdvancedGui;
import QuantumStorage.inventory.AdvancedContainer;
import QuantumStorage.tiles.AdvancedTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by Gigabit101 on 27/01/2017.
 */
public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(world.getTileEntity(new BlockPos(x, y, z)) != null && world.getTileEntity(new BlockPos(x, y, z)) instanceof AdvancedTileEntity)
        {
            AdvancedTileEntity machine = (AdvancedTileEntity) world.getTileEntity(new BlockPos(x, y, z));
            return new AdvancedContainer(player, machine);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(world.getTileEntity(new BlockPos(x, y, z)) != null && world.getTileEntity(new BlockPos(x, y, z)) instanceof AdvancedTileEntity)
        {
            AdvancedTileEntity machine = (AdvancedTileEntity) world.getTileEntity(new BlockPos(x, y, z));
            return new AdvancedGui(player, machine);
        }
        return null;
    }
}
