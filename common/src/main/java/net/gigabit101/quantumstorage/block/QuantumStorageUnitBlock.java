package net.gigabit101.quantumstorage.block;

import net.creeperhost.polylib.blocks.PolyEntityBlock;
import net.gigabit101.quantumstorage.block.entity.QuantumStorageUnitBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;

public class QuantumStorageUnitBlock extends PolyEntityBlock {
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public QuantumStorageUnitBlock(BlockBehaviour.Properties properties) {
        super(properties
                .mapColor(MapColor.METAL)
                .requiresCorrectToolForDrops()
                .strength(2.0F, 100.0F)
                .sound(SoundType.METAL)
                .noOcclusion());
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (level.getBlockEntity(pos) instanceof QuantumStorageUnitBlockEntity blockEntity) {
            blockEntity.prepareForHarvest();
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(FACING);
    }
}
