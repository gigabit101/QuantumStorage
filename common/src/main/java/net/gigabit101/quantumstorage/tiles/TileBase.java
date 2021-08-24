package net.gigabit101.quantumstorage.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileBase extends BlockEntity
{
    public TileBase(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState)
    {
        super(blockEntityType, blockPos, blockState);
    }
}
