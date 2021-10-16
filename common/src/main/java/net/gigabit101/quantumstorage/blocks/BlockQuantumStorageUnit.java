package net.gigabit101.quantumstorage.blocks;

import dev.architectury.registry.menu.MenuRegistry;
import net.gigabit101.quantumstorage.blocks.prefab.BlockWithRotation;
import net.gigabit101.quantumstorage.init.ModBlocks;
import net.gigabit101.quantumstorage.init.ModItems;
import net.gigabit101.quantumstorage.items.ItemMemoryCard;
import net.gigabit101.quantumstorage.tiles.TileQuantumStorageUnit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BlockQuantumStorageUnit extends BaseEntityBlock
{
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty HAS_MEMORY_CARD = BooleanProperty.create("has_memorycard");

    public BlockQuantumStorageUnit()
    {
        super(Properties.of(Material.METAL).noOcclusion().strength(0.5F));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HAS_MEMORY_CARD, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext)
    {
        return (BlockState)this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite()).setValue(HAS_MEMORY_CARD, false);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> arg)
    {
        arg.add(FACING, HAS_MEMORY_CARD);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        if(level.isClientSide())
        {
            return InteractionResult.SUCCESS;
        }
        TileQuantumStorageUnit tileQuantumStorageUnit = (TileQuantumStorageUnit) level.getBlockEntity(blockPos);
        if (player.getItemInHand(interactionHand).getItem() instanceof ItemMemoryCard && !blockState.getValue(HAS_MEMORY_CARD))
        {
            BlockState blockState1 = blockState.setValue(HAS_MEMORY_CARD, true);
            level.setBlock(blockPos, blockState1, 3);
            ItemStack memoryCard = player.getItemInHand(interactionHand);
            if (memoryCard.hasTag()) tileQuantumStorageUnit.loadFromTag(memoryCard.getTagElement("BlockEntityTag"));
            player.getItemInHand(interactionHand).shrink(1);
            return InteractionResult.SUCCESS;
        }
        else if (player.isCrouching() && player.getItemInHand(interactionHand).isEmpty() && blockState.getValue(HAS_MEMORY_CARD))
        {
            BlockState blockState1 = blockState.setValue(HAS_MEMORY_CARD, false);
            level.setBlock(blockPos, blockState1, 3);
            ItemStack memoryCard = createMemoryCard(tileQuantumStorageUnit);
            player.addItem(memoryCard);
            tileQuantumStorageUnit.getInventory().clearContent();
            return InteractionResult.SUCCESS;
        }
        if(!level.isClientSide() && !player.isCrouching())
        {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            MenuRegistry.openExtendedMenu((ServerPlayer) player, (MenuProvider) blockEntity, packetBuffer -> packetBuffer.writeBlockPos(blockEntity.getBlockPos()));
            return InteractionResult.SUCCESS;
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    public ItemStack createMemoryCard(TileQuantumStorageUnit tileQuantumStorageUnit)
    {
        ItemStack memoryCard = new ItemStack(ModItems.MEMORY_CARD.get());
        CompoundTag compoundTag = tileQuantumStorageUnit.saveToTag(new CompoundTag());
        if (!compoundTag.isEmpty()) memoryCard.addTagElement("BlockEntityTag", compoundTag);

        return memoryCard;
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.MODEL;
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player)
    {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof TileQuantumStorageUnit)
        {
            TileQuantumStorageUnit tileQuantumStorageUnit = (TileQuantumStorageUnit) blockEntity;

            if (!level.isClientSide && !player.isCreative())
            {
                ItemStack itemStack = createMemoryCard(tileQuantumStorageUnit);
                ItemEntity itemEntity = new ItemEntity(level, (double)blockPos.getX() + 0.5D, (double)blockPos.getY() + 0.5D, (double)blockPos.getZ() + 0.5D, itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            } else
            {
                super.playerWillDestroy(level, blockPos, blockState, player);;
            }
        }
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
