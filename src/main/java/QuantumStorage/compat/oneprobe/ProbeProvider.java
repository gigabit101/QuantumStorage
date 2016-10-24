package QuantumStorage.compat.oneprobe;

import QuantumStorage.lib.ModInfo;
import QuantumStorage.tile.TileQuantumDsu;
import QuantumStorage.tile.TileQuantumStorage;
import mcjty.theoneprobe.api.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import reborncore.api.IListInfoProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 10/10/2016.
 */
public class ProbeProvider implements IProbeInfoProvider
{
    @Override
    public String getID()
    {
        return ModInfo.MOD_ID.toLowerCase();
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data)
    {
        TileEntity tile = world.getTileEntity(data.getPos());
        if (tile instanceof TileQuantumStorage)
        {
            List<String> strs = new ArrayList<>();
            ((IListInfoProvider) tile).addInfo(strs, true);
            for (String string : strs)
            {
                probeInfo.text(string);
            }
        }
        if (tile instanceof TileQuantumDsu)
        {
            TileQuantumDsu dsu = (TileQuantumDsu) world.getTileEntity(data.getPos());
            if(dsu.getStackInSlot(0) != null)
                probeInfo.text("" + dsu.getStackInSlot(0).getItem().toString());
//                probeInfo.item(dsu.getStackInSlot(0));
        }
    }
}
