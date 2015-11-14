package QuantumStorage.items.itemblocks;

import QuantumStorage.init.ModBlocks;
import QuantumStorage.tile.TileQuantumChest;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class ItemBlockQuantumChest extends ItemBlock
{
	public ItemBlockQuantumChest(Block block) 
	{
		super(block);
	}
	
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    @Override
//    @SideOnly(Side.CLIENT)
//    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) 
//    {
//        if (stack != null && stack.hasTagCompound()) {
//            if (stack.getTagCompound().getCompoundTag("tileEntity") != null)
//                list.add(stack.getTagCompound().getCompoundTag("tileEntity")
//                        .getInteger("storedQuantity") + " " +
//                        (stack.getTagCompound().getCompoundTag("tileEntity")
//                    			.getString("storedItemAsString")));
//        }
//    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player,  World world, int x, int y, int z, int side, float hitX, float hitY,  float hitZ, int metadata) 
    {
        if (!world.setBlock(x, y, z, ModBlocks.QuantumChest, metadata, 3)) 
        {
            return false;
        }
        if (world.getBlock(x, y, z) == ModBlocks.QuantumChest) 
        {
            world.getBlock(x, y, z).onBlockPlacedBy(world, x, y, z, player, stack);
            world.getBlock(x, y, z).onPostBlockPlaced(world, x, y, z, metadata);
        }
        if (stack != null && stack.hasTagCompound()) 
        {
            ((TileQuantumChest) world.getTileEntity(x, y, z)).readFromNBTWithoutCoords(stack.getTagCompound().getCompoundTag("tileEntity"));
        }
        return true;
    }
}
