package net.gigabit101.quantumstorage.blocks;

import net.gigabit101.quantumstorage.tiles.TileController;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;

public class BlockController extends BaseEntityBlock
{
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public BlockController()
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new TileController(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState)
    {
        return RenderShape.MODEL;
    }


    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        TileController controllerTile = (TileController) worldIn.getBlockEntity(pos);
        ItemStack stackIn = player.getItemInHand(handIn);

        if(!(player instanceof FakePlayer) && !worldIn.isClientSide)
        {
            if(stackIn.isEmpty() && player.isCrouching())
            {
                if(!controllerTile.getConnectedTiles().isEmpty())
                {
//                    controllerTile.getConnectedTiles().forEach(tilePos -> player.sendStatusMessage(new StringTextComponent(tilePos.toString()), false));
                    controllerTile.inventory.setSize(controllerTile.getConnectedTiles().size());
                    player.sendMessage(new TextComponent("Amount of connected tiles : " + controllerTile.getConnectedTiles().size()), Util.NIL_UUID);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }
}
