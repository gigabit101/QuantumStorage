package net.gigabit101.quantumstorage.block;

import net.creeperhost.polylib.blocks.PolyEntityBlock;
import net.gigabit101.quantumstorage.block.entity.StorageCrateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StorageCrateBlock extends PolyEntityBlock {
    public StorageCrateBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide() && !player.isCreative() && player.hasCorrectToolForDrops(state)
                && level.getBlockEntity(pos) instanceof StorageCrateBlockEntity blockEntity) {
            blockEntity.prepareForHarvest(level.registryAccess());
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (blockEntity instanceof StorageCrateBlockEntity crateBlockEntity) {
            ItemStack retainedStack = crateBlockEntity.consumeRetainedHarvestStack();
            if (!retainedStack.isEmpty()) {
                player.awardStat(Stats.BLOCK_MINED.get(this));
                player.causeFoodExhaustion(0.005F);
                popResource(level, pos, retainedStack);
                level.removeBlockEntity(pos);
                return;
            }
        }
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
    }
}
