package QuantumStorage.items;

import QuantumStorage.rewrite.AdvancedTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Gigabit101 on 17/03/2017.
 */
public class ItemBlockQStorage extends ItemBlock
{
    Block block;

    public ItemBlockQStorage(Block block)
    {
        super(block);
        this.block = block;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        if(stack != null && stack.hasTagCompound())
        {
            //TODO
//            tooltip.add(TextFormatting.RED + "COMING SOON!!!");
//            tooltip.add(stack.getTagCompound().toString());
        }
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if (!world.setBlockState(pos, block.getDefaultState()))
        {
            return false;
        }

        if (stack != null && stack.hasTagCompound())
        {
            ((AdvancedTileEntity) world.getTileEntity(pos)).readFromNBTWithoutCoords(stack.getTagCompound().getCompoundTag("tileEntity"));
        }
        return true;
    }
}
