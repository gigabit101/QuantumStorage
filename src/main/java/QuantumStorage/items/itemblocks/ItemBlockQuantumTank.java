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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

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
		if (stack != null && stack.hasTagCompound()) 
		{
			((TileQuantumTank) world.getTileEntity(pos)).readFromNBTWithoutCoords(stack.getTagCompound().getCompoundTag("tileEntity"));
		}
		return true;	
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		if (stack != null && stack.hasTagCompound())
		{
			if(stack.getTagCompound().getCompoundTag("tileEntity").getCompoundTag("TileQuantumTank") != null)
			{
				list.add("" + TextFormatting.DARK_AQUA  + stack.getTagCompound().getCompoundTag("tileEntity").getCompoundTag("TileQuantumTank").getInteger("Amount")+" MB of "
                        + stack.getTagCompound().getCompoundTag("tileEntity").getCompoundTag("TileQuantumTank").getString("FluidName").toUpperCase());
			}
		}
	}
}
