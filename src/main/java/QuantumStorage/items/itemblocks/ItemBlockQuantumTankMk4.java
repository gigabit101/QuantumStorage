package QuantumStorage.items.itemblocks;

import QuantumStorage.init.ModBlocks;
import QuantumStorage.tile.qst.TileQuantumTankMk4;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockQuantumTankMk4 extends ItemBlock
{
	public ItemBlockQuantumTankMk4(Block block) 
	{
		super(block);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) 
	{
		if (!world.setBlock(x, y, z, ModBlocks.QuantumTankMk4, metadata, 3))
		{
			return false;
		}
		if (world.getBlock(x, y, z) == ModBlocks.QuantumTankMk4) 
		{
			world.getBlock(x, y, z).onBlockPlacedBy(world, x, y, z, player, stack);
			world.getBlock(x, y, z).onPostBlockPlaced(world, x, y, z, metadata);
		}
		if (stack != null && stack.hasTagCompound()) 
		{
			((TileQuantumTankMk4) world.getTileEntity(x, y, z)).readFromNBTWithoutCoords(stack.getTagCompound().getCompoundTag("tileEntity"));
		}
		return true;
	}
}
