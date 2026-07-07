package net.gigabit101.quantumstorage.block;

import net.creeperhost.polylib.blocks.InteractableBlock;
import net.gigabit101.quantumstorage.block.entity.MultiblockStorageBlockEntity;
import net.gigabit101.quantumstorage.registry.QuantumStorageContent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MultiblockStorageBlock extends QuantumStorageBlock implements EntityBlock {
    public MultiblockStorageBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return QuantumStorageContent.MULTIBLOCK_STORAGE_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.getBlockEntity(pos) instanceof InteractableBlock interactable) {
            return interactable.onBlockUse(state, level, pos, player, hit);
        }
        return super.useWithoutItem(state, level, pos, player, hit);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (blockEntity instanceof MultiblockStorageBlockEntity storageBlockEntity && storageBlockEntity.isStorageBlock()) {
            storageBlockEntity.dropContents();
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }
}
