package net.gigabit101.quantumstorage.blocks;

import dev.architectury.registry.menu.MenuRegistry;
import net.gigabit101.quantumstorage.blocks.prefab.BlockWithRotation;
import net.gigabit101.quantumstorage.tiles.TileTrashCan;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockTrashCan extends BlockWithRotation
{
    public BlockTrashCan()
    {
        super(Properties.of(Material.METAL).strength(0.5F).noOcclusion());
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        if(level.isClientSide())
        {
            return InteractionResult.SUCCESS;
        }
        if(!level.isClientSide())
        {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            MenuRegistry.openExtendedMenu((ServerPlayer) player, (MenuProvider) blockEntity, packetBuffer -> packetBuffer.writeBlockPos(blockEntity.getBlockPos()));
            return InteractionResult.SUCCESS;
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new TileTrashCan(blockPos, blockState);
    }
}
