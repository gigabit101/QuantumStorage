package QuantumStorage.tiles;

import QuantumStorage.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 29/03/2017.
 */
public class TileController extends AdvancedTileEntity implements ITickable
{
    List<IItemHandler> l = new ArrayList<>();
    
    public void addInv(IItemHandler handler)
    {
        if (!l.contains(handler))
            l.add(handler);
    }
    
    public List<IItemHandler> getItemHandlers()
    {
        return l;
    }
    
    @Override
    public void update()
    {
        if (!world.isRemote)
        {
            TileController controller = (TileController) world.getTileEntity(pos);
            if (controller != null)
            {
                for (final IItemHandler invs : getConnectedCapabilities(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, world, pos))
                {
                    addInv(invs);
                }
            }

//            if(getInv().getStackInSlot(0) != ItemStack.EMPTY)
//            {
//                for(final IItemHandler i : getItemHandlers())
//                {
//                    if(i.getStackInSlot(1) == ItemStack.EMPTY)
//                    {
//                        i.insertItem(1, getInv().getStackInSlot(0), false);
//                        getInv().setStackInSlot(0, ItemStack.EMPTY);
//                    }
//                    else if(ItemUtils.isItemEqual(getInv().getStackInSlot(0), i.getStackInSlot(1), true, true, true))
//                    {
//                        i.insertItem(1, getInv().getStackInSlot(0), false);
//                        getInv().setStackInSlot(0, ItemStack.EMPTY);
//                    }
//                }
//            }
        }
    }
    
    public static <T> List<T> getConnectedCapabilities(Capability<T> capability, World world, BlockPos pos)
    {
        final List<T> capabilities = new ArrayList<T>();
        
        for (final EnumFacing side : EnumFacing.values())
        {
            final TileEntity tile = world.getTileEntity(pos.offset(side));
            
            if (tile != null && !tile.isInvalid() && tile.hasCapability(capability, side.getOpposite()))
                capabilities.add(tile.getCapability(capability, side.getOpposite()));
        }
        return capabilities;
    }
    
    @Override
    public String getName()
    {
        return "controller";
    }
    
    @Override
    public List<Slot> getSlots()
    {
        return null;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileController();
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (getItemHandlers() != null)
        {
            TileController te = (TileController) worldIn.getTileEntity(pos);
            
            playerIn.sendMessage(new TextComponentString(te.getItemHandlers().toString()));
        }
        return true;
    }
    
    @Override
    public Block getBlock()
    {
        return ModBlocks.CONTROLLER;
    }
    
    @Override
    public void addRecipe()
    {
    
    }
}
