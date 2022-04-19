package net.gigabit101.quantumstorage.blocks;

import net.gigabit101.quantumstorage.tiles.chests.TileChestGold;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BlockChestGold extends BlockChestBase
{
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new TileChestGold(blockPos, blockState);
    }
    

}
