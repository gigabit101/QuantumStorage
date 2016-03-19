package QuantumStorage.items.itemblocks;

import java.util.List;

import QuantumStorage.init.ModBlocks;
import QuantumStorage.tile.TileQuantumDsu;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ItemBlockQuantumDsu extends ItemBlock{

	public ItemBlockQuantumDsu(Block block) {
		super(block);
	}
	
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack != null && stack.hasTagCompound()) {
            if (stack.getTagCompound().getCompoundTag("tileEntity") != null)
                list.add(stack.getTagCompound().getCompoundTag("tileEntity")
                        .getInteger("storedQuantity") + " " +
                        (stack.getTagCompound().getCompoundTag("tileEntity")
                    			.getString("storedItemAsString")));
        }
    }
    
    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (!world.setBlockState(pos, ModBlocks.QuantumDsu.getDefaultState())) {
            return false;
        }
        if (world.getBlockState(pos) == ModBlocks.QuantumDsu) {
//            world.getBlockState(pos).onBlockPlacedBy(world, pos, player, stack);
//            world.getBlockState(pos).onPostBlockPlaced(world, x, y, z, metadata);
        }
        if (stack != null && stack.hasTagCompound()) {
            ((TileQuantumDsu) world.getTileEntity(pos))
                    .readFromNBTWithoutCoords(stack.getTagCompound()
                            .getCompoundTag("tileEntity"));
        }
        return true;
    }
}
