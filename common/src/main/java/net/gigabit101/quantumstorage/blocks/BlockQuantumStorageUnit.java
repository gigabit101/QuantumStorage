package net.gigabit101.quantumstorage.blocks;

import dev.architectury.registry.menu.MenuRegistry;
import net.gigabit101.quantumstorage.blocks.prefab.BlockWithRotation;
import net.gigabit101.quantumstorage.init.ModBlocks;
import net.gigabit101.quantumstorage.tiles.TileQuantumStorageUnit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BlockQuantumStorageUnit extends BlockWithRotation
{
    public BlockQuantumStorageUnit()
    {
        super(Properties.of(Material.METAL).noOcclusion());
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
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player)
    {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof TileQuantumStorageUnit)
        {
            TileQuantumStorageUnit tileQuantumStorageUnit = (TileQuantumStorageUnit) blockEntity;

            if (!level.isClientSide && !player.isCreative() && !tileQuantumStorageUnit.isEmpty())
            {
                ItemStack itemStack = new ItemStack(ModBlocks.QUANTUM_STORAGE_UNIT.get());
                CompoundTag compoundTag = tileQuantumStorageUnit.saveToTag(new CompoundTag());
                if (!compoundTag.isEmpty())
                {
                    itemStack.addTagElement("BlockEntityTag", compoundTag);
                }

                ItemEntity itemEntity = new ItemEntity(level, (double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            } else
            {
                super.playerWillDestroy(level, blockPos, blockState, player);;
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder)
    {
        return new ArrayList<>();
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

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag)
    {
        if(!itemStack.isEmpty() && itemStack.hasTag())
        {
            if(itemStack.getTag().getCompound("BlockEntityTag") != null)
            {
                ListTag listTag = itemStack.getTag().getCompound("BlockEntityTag").getList("Items", 10);
                list.add(new TranslatableComponent(ChatFormatting.GREEN + "Slot 0: " + ChatFormatting.WHITE + ItemStack.of(listTag.getCompound(0)).toString()));
                ItemStack storageStack = ItemStack.of(listTag.getCompound(2));
                int count = listTag.getCompound(1).getInt("SizeSpecial");
                storageStack.setCount(count);
                list.add(new TranslatableComponent(ChatFormatting.GREEN + "Slot 1: " + ChatFormatting.WHITE + storageStack.toString()));
                list.add(new TranslatableComponent(ChatFormatting.GREEN + "Slot 2: " + ChatFormatting.WHITE + ItemStack.of(listTag.getCompound(2)).toString()));
            }
        }
        super.appendHoverText(itemStack, blockGetter, list, tooltipFlag);
    }
}
