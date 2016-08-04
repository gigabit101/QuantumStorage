package QuantumStorage.items.itemblocks;

import QuantumStorage.init.ModBlocks;
import QuantumStorage.tile.TileQuantumTank;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBlockQuantumTank extends ItemBlock
{
	public ItemBlockQuantumTank(Block block) 
	{
		super(block);
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) 
	{
		if (!world.setBlockState(pos, ModBlocks.QuantumTank.getDefaultState()))
		{
			return false;
		}
		if (world.getBlockState(pos) == ModBlocks.QuantumTank) 
		{
//			world.getBlockState(pos).onBlockPlacedBy(world, pos, player, stack);
//			world.getBlockState(pos).onPostBlockPlaced(world, pos, metadata);
		}
		if (stack != null && stack.hasTagCompound()) 
		{
			((TileQuantumTank) world.getTileEntity(pos)).readFromNBTWithoutCoords(stack.getTagCompound().getCompoundTag("tileEntity"));
		}
		return true;	
	}
}
