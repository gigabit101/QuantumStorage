package QuantumStorage.blocks;

import QuantumStorage.QuantumStorage;
import QuantumStorage.client.CreativeTabQuantumStorage;
import QuantumStorage.init.ModBlocks;
import QuantumStorage.tiles.TileController;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Gigabit101 on 29/03/2017.
 */
public class BlockCable extends Block
{
    public BlockCable()
    {
        super(Material.IRON);
        setUnlocalizedName(QuantumStorage.MOD_ID + ".cable");
        setCreativeTab(CreativeTabQuantumStorage.INSTANCE);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(worldIn, pos, state);
        updateInventories(worldIn, pos);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
    {
        super.onNeighborChange(world, pos, neighbor);
        updateInventories(world, pos);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        updateInventories(worldIn, pos);
    }

    public void updateInventories(IBlockAccess world, BlockPos pos)
    {
//        List<BlockPos> visited = new ArrayList<BlockPos>();
//
//        Queue<BlockPos> queue = new PriorityQueue<BlockPos>();
//        BlockPos start = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
//        queue.add(start);
//        visited.add(start);
//
//        while (!queue.isEmpty())
//        {
//            BlockPos element = queue.poll();
//
//            for (int x = -1; x <= 1; x++)
//            {
//                for (int y = -1; y <= 1; y++)
//                {
//                    for (int z = -1; z <= 1; z++)
//                    {
//                        if (Math.abs(x) + Math.abs(y) + Math.abs(z) == 1)
//                        {
//                            BlockPos target = new BlockPos(element.getX() + x, element.getY() + y, element.getZ() + z);
//
//                            if (!visited.contains(target))
//                            {
//                                visited.add(target);
//                                IBlockState block = world.getBlockState(new BlockPos(x, y, z));
//                                int meta = block.getBlock().getMetaFromState(block);
//                                if (block.getBlock() == ModBlocks.CONTROLLER)
//                                {
//                                    TileEntity tileEntity = world.getTileEntity(target);
//                                    if (tileEntity != null && tileEntity instanceof TileController)
//                                    {
//                                        ((TileController) tileEntity).updateInventories();
//                                    }
//                                }
//                                else if (isCable(block.getBlock(), meta))
//                                {
//                                    queue.add(target);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    public boolean isCable(Block block, int meta)
    {
        return block == ModBlocks.CABLE;
    }
}
