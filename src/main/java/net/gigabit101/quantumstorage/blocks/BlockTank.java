package net.gigabit101.quantumstorage.blocks;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.tiles.TileTank;
import net.minecraft.block.*;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockTank extends ContainerBlock
{
    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public BlockTank(Properties properties)
    {
        super(properties);
        setRegistryName(new ResourceLocation(QuantumStorage.MOD_ID, "tank"));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isNormalCube(BlockState p_220081_1_, IBlockReader p_220081_2_, BlockPos p_220081_3_)
    {
        return false;
    }

    @Override
    public boolean isVariableOpacity()
    {
        return false;
    }

    @Override
    public boolean isSolid(BlockState p_200124_1_)
    {
        return false;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Nonnull
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
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

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn)
    {
        return QuantumStorage.tileTank.create();
    }

    public boolean onBlockActivated(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult traceResult)
    {
         if(FluidUtil.interactWithFluidHandler(player, hand, world, pos, traceResult.getFace()))
         {
             return true;
         }
        if (!world.isRemote)
        {
            NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) world.getTileEntity(pos), pos);
        }
        return true;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }
    
    @Override
    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity tile, ItemStack stack)
    {
        TileTank tileEntity = (TileTank) tile;

        float xOffset = world.rand.nextFloat() * 0.8F + 0.1F;
        float yOffset = world.rand.nextFloat() * 0.8F + 0.1F;
        float zOffset = world.rand.nextFloat() * 0.8F + 0.1F;

        ItemStack stacknbt = (tileEntity).getDropWithNBT();
        int amountToDrop = Math.min(world.rand.nextInt(21) + 10, stacknbt.getCount());

        ItemEntity entityitem = new ItemEntity(world, pos.getX() + xOffset, pos.getY() + yOffset, pos.getZ() + zOffset, stacknbt.split(amountToDrop));
        world.addEntity(entityitem);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack)
    {
        super.onBlockPlacedBy(world, pos, state, entity, stack);
        TileTank qsu = (TileTank) world.getTileEntity(pos);

        if (stack.hasTag())
        {
            qsu.readFromNBTWithoutCoords(stack.getTag().getCompound("tileEntity"));
        }
        world.notifyBlockUpdate(pos, state, state, 3);
    }
    
    @Override
    public void addInformation(ItemStack p_190948_1_, @Nullable IBlockReader p_190948_2_, List<ITextComponent> tooltip, ITooltipFlag p_190948_4_)
    {
        tooltip.add(new TranslationTextComponent(TextFormatting.RED + "WIP"));
    }
}
