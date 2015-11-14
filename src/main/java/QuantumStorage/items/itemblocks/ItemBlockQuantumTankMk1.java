package QuantumStorage.items.itemblocks;

import java.util.List;

import QuantumStorage.init.ModBlocks;
import QuantumStorage.tile.qst.TileQuantumTankMk1;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockQuantumTankMk1 extends ItemBlock
{
	public ItemBlockQuantumTankMk1(Block block) 
	{
		super(block);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) 
	{
		if (!world.setBlock(x, y, z, ModBlocks.QuantumTankMk1, metadata, 3))
		{
			return false;
		}
		if (world.getBlock(x, y, z) == ModBlocks.QuantumTankMk1) 
		{
			world.getBlock(x, y, z).onBlockPlacedBy(world, x, y, z, player, stack);
			world.getBlock(x, y, z).onPostBlockPlaced(world, x, y, z, metadata);
		}
		if (stack != null && stack.hasTagCompound()) 
		{
			((TileQuantumTankMk1) world.getTileEntity(x, y, z)).readFromNBTWithoutCoords(stack.getTagCompound().getCompoundTag("tileEntity"));
		}
		return true;
	}
}
