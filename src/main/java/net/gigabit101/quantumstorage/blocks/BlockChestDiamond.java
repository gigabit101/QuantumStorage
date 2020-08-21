package net.gigabit101.quantumstorage.blocks;

import net.gigabit101.quantumstorage.tiles.chests.TileChestDiamond;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockChestDiamond extends BlockChestBase
{

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn)
    {
        return new TileChestDiamond();
    }

}
