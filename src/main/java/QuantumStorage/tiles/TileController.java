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
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 29/03/2017.
 */
public class TileController extends AdvancedTileEntity implements ITickable
{
    public TileController()
    {
        this.inv = new ItemStackHandler(1);
    }
    
    @Override
    public List<Slot> getSlots()
    {
        List<Slot> slots = new ArrayList<>();
        slots.add(new SlotItemHandler(inv, 0, 80, 50));
        return slots;
    }
    
    @Override
    public void update()
    {
        if (!world.isRemote)
        {
//            TileController controller = (TileController) world.getTileEntity(pos);
//            if (controller != null)
//            {
//                for (final IItemHandler invs : getConnectedCapabilities(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, world, pos))
//                {
//                    addInv(invs);
//                }
//            }
        }
    }
    
    public static List<TileQuantumStorageUnit> getAttached(World world, BlockPos pos)
    {
        List<TileQuantumStorageUnit> units = new ArrayList<>();
    
        for (EnumFacing side : EnumFacing.values())
        {
            if(!world.isRemote && world.getTileEntity(pos.offset(side)) != null && world.getTileEntity(pos.offset(side)) instanceof TileQuantumStorageUnit)
            {
                TileQuantumStorageUnit i = (TileQuantumStorageUnit) world.getTileEntity(pos.offset(side));
                units.add(i);
            }
        }
        return units;
    }
    
    @Override
    public String getName()
    {
        return "controller";
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileController();
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!playerIn.isSneaking())
        {
            openGui(playerIn, (AdvancedTileEntity) worldIn.getTileEntity(pos));
            return true;
        }
        else
        {
            for (EnumFacing s : EnumFacing.values())
            {
                
            }
            playerIn.sendMessage(new TextComponentString(getAttached(world, pos).toString()));
            return true;
        }
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
    
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }
    
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inv);
        }
        return super.getCapability(capability, facing);
    }
}
