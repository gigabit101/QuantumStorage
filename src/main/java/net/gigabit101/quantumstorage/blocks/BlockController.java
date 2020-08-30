package net.gigabit101.quantumstorage.blocks;

import net.gigabit101.quantumstorage.tiles.TileController;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;

public class BlockController extends ContainerBlock
{
    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public BlockController()
    {
        super(Properties.create(Material.IRON).notSolid().hardnessAndResistance(2.0F));
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn)
    {
        return new TileController();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        TileController controllerTile = (TileController) worldIn.getTileEntity(pos);
        ItemStack stackIn = player.getHeldItem(handIn);

        if(!(player instanceof FakePlayer) && !worldIn.isRemote())
        {
            if(stackIn.isEmpty() && player.isSneaking())
            {
                if(!controllerTile.getConnectedTiles().isEmpty())
                {
//                    controllerTile.getConnectedTiles().forEach(tilePos -> player.sendStatusMessage(new StringTextComponent(tilePos.toString()), false));
                    controllerTile.inventory.setSize(controllerTile.getConnectedTiles().size());
                    player.sendStatusMessage(new StringTextComponent("Amount of connected tiles : " + controllerTile.getConnectedTiles().size()), true);
                }
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
