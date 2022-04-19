package net.gigabit101.quantumstorage.blocks;

import net.gigabit101.quantumstorage.tiles.TileQsu;
import net.gigabit101.quantumstorage.tiles.chests.TileChestBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BlockChestBase extends BaseEntityBlock
{
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public BlockChestBase()
    {
        super(Properties.of(Material.METAL).strength(2.0F));
        this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_)
    {
        return RenderShape.MODEL;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new TileQsu(blockPos, blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
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

//    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
//    {
//        builder.add(FACING);
//    }

    //TODO
//    @Override
//    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tile, ItemStack stack)
//    {
//        TileChestBase tileEntity = (TileChestBase) tile;
//
//        float xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
//        float yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
//        float zOffset = world.rand.nextFloat() * 0.8F + 0.1F;
//
//        ItemStack stacknbt = (tileEntity).getDropWithNBT();
//
//        // wtf is this? dropping a random amount of items?
//        int amountToDrop = Math.min(world.rand.nextInt(21) + 10, stacknbt.getCount());
//
//        ItemEntity entityitem = new ItemEntity(world, pos.getX() + xOffset, pos.getY() + yOffset, pos.getZ() + zOffset, stacknbt.split(amountToDrop));
//        world.addEntity(entityitem);
//    }
//
//    @Override
//    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack)
//    {
//        super.onBlockPlacedBy(world, pos, state, entity, stack);
//        TileChestBase qsu = (TileChestBase) world.getTileEntity(pos);
//
//        if (stack.hasTag())
//        {
//            qsu.readFromNBTWithoutCoords(stack.getTag().getCompound("tileEntity"));
//        }
//        world.notifyBlockUpdate(pos, state, state, 3);
//    }
}
