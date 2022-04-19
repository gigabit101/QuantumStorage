package net.gigabit101.quantumstorage.blocks;

import net.gigabit101.quantumstorage.tiles.TileQsu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockQSU extends BaseEntityBlock
{
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    
    public BlockQSU()
    {
        super(Properties.of(Material.METAL).strength(2.0F));
        this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.MODEL;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new TileQsu(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult traceResult)
    {
        if (!world.isClientSide)
        {
            NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) world.getBlockEntity(pos), pos);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType)
    {
        return (level1, blockPos, blockState1, tile) ->
        {
            if(tile instanceof TileQsu tileQsu)
            {
                tileQsu.tick();
            }
        };
    }

    //TODO
//    @Override
//    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tile, ItemStack stack)
//    {
//        world.removeTileEntity(pos);
//        TileQsu tileEntity = (TileQsu) tile;
//
//        float xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
//        float yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
//        float zOffset = world.rand.nextFloat() * 0.8F + 0.1F;
//
//        ItemStack stacknbt = (tileEntity).getDropWithNBT();
//
//        ItemEntity entityitem = new ItemEntity(world, pos.getX() + xOffset, pos.getY() + yOffset, pos.getZ() + zOffset, stacknbt);
//        world.addEntity(entityitem);
//    }
//
//    @Override
//    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack)
//    {
//        super.onBlockPlacedBy(world, pos, state, entity, stack);
//        TileQsu qsu = (TileQsu) world.getTileEntity(pos);
//
//        if (stack.hasTag())
//        {
//            qsu.readFromNBTWithoutCoords(stack.getTag().getCompound("tileEntity"));
//        }
//        world.notifyBlockUpdate(pos, state, state, 3);
//    }


    @Override
    public void appendHoverText(ItemStack stack, BlockGetter p_190948_2_, List<Component> tooltip, TooltipFlag p_190948_4_)
    {
        if (!stack.isEmpty() && stack.hasTag())
        {
            if (stack.getTag().getCompound("tileEntity") != null)
            {
//                CompoundList tagList = stack.getTag().getCompound("tileEntity").getList("Items", Constants.NBT.TAG_COMPOUND);
//                ItemStack stack1;
            
//                CompoundNBT itemTags = tagList.getCompound(0);
//                CompoundNBT itemTags2 = tagList.getCompound(2);
//
//                int count = itemTags.getInt("SizeSpecial") + itemTags2.getInt("SizeSpecial");
//
//                stack1 = ItemStack.read(itemTags);
//                stack1.setCount(count);
//
//                if (!stack1.isEmpty())
//                {
//                    tooltip.add(new TranslationTextComponent(TextFormatting.GOLD + "Stored Item Type: " + stack1.getCount() + " " + stack1.getDisplayName()));
//                }
            }
        }
    }
}
