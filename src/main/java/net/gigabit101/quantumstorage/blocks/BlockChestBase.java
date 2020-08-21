package net.gigabit101.quantumstorage.blocks;

import net.gigabit101.quantumstorage.tiles.chests.TileChestBase;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BlockChestBase extends ContainerBlock {
    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public BlockChestBase()
    {
        super(Properties.create(Material.IRON).notSolid().hardnessAndResistance(2.0F));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Nonnull
    public BlockState rotate(BlockState state, Rotation rot)
    {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Nonnull
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult traceResult)
    {
        if (!world.isRemote)
        {
            NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) world.getTileEntity(pos), pos);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.SUCCESS;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

    @Override
    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tile, ItemStack stack)
    {
        TileChestBase tileEntity = (TileChestBase) tile;

        float xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
        float yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
        float zOffset = world.rand.nextFloat() * 0.8F + 0.1F;

        ItemStack stacknbt = (tileEntity).getDropWithNBT();

        // wtf is this? dropping a random amount of items?
        int amountToDrop = Math.min(world.rand.nextInt(21) + 10, stacknbt.getCount());

        ItemEntity entityitem = new ItemEntity(world, pos.getX() + xOffset, pos.getY() + yOffset, pos.getZ() + zOffset, stacknbt.split(amountToDrop));
        world.addEntity(entityitem);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack)
    {
        super.onBlockPlacedBy(world, pos, state, entity, stack);
        TileChestBase qsu = (TileChestBase) world.getTileEntity(pos);

        if (stack.hasTag())
        {
            qsu.readFromNBTWithoutCoords(stack.getTag().getCompound("tileEntity"));
        }
        world.notifyBlockUpdate(pos, state, state, 3);
    }
}
