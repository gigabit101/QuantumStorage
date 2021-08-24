package net.gigabit101.quantumstorage.blocks;

import dev.architectury.registry.menu.MenuRegistry;
import net.gigabit101.quantumstorage.blocks.prefab.BlockWithRotation;
import net.gigabit101.quantumstorage.tiles.TileQuantumStorageUnit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockQuantumStorageUnit extends BlockWithRotation
{
    public BlockQuantumStorageUnit()
    {
        super(Properties.of(Material.METAL));
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        if(!level.isClientSide())
        {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            MenuRegistry.openExtendedMenu((ServerPlayer) player, (MenuProvider) blockEntity, packetBuffer -> packetBuffer.writeBlockPos(blockEntity.getBlockPos()));
            return InteractionResult.SUCCESS;
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new TileQuantumStorageUnit(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType)
    {
        return TileQuantumStorageUnit::tick;
    }
}
