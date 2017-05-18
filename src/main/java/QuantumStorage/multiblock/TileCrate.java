package QuantumStorage.multiblock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import reborncore.common.multiblock.MultiblockControllerBase;
import reborncore.common.multiblock.MultiblockTileEntityBase;

import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 12/05/2017.
 */
public class TileCrate extends MultiblockTileEntityBase
{
    @Override
    public void onMachineActivated()
    {
    }

    @Override
    public void onMachineDeactivated()
    {
    }

    @Override
    public MultiblockControllerBase createNewMultiblock()
    {
        return new MultiBlockCrate(getWorld());
    }

    @Override
    public Class<? extends MultiblockControllerBase> getMultiblockControllerType()
    {
        return MultiBlockCrate.class;
    }

    public MultiBlockCrate getMultiBlock()
    {
        return (MultiBlockCrate) getMultiblockController();
    }

    @Override
    public void update()
    {
    }

    boolean isCrate()
    {
        return getWorld().getBlockState(getPos()).getBlock() instanceof BlockCrate;
    }

    ItemStackHandler invTile = new ItemStackHandler(5);

    public TileCrate()
    {
    }

    @Override
    public void readFromNBT(NBTTagCompound data)
    {
        super.readFromNBT(data);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data)
    {
        return super.writeToNBT(data);
    }

    @Override
    public void onMachineAssembled(MultiblockControllerBase multiblockControllerBase)
    {

    }

    @Override
    public void onMachineBroken()
    {

    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && getMultiBlock() != null)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && getMultiBlock() != null)
        {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getMultiBlock().getInv());
        }
        return super.getCapability(capability, facing);
    }
}
