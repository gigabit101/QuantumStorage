package QuantumStorage.multiblock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.Sys;
import reborncore.common.multiblock.MultiblockControllerBase;
import reborncore.common.multiblock.MultiblockValidationException;
import reborncore.common.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import reborncore.common.util.Inventory;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Created by Gigabit101 on 12/05/2017.
 */
public class TileCrate extends RectangularMultiblockTileEntityBase
{
    @Override
    public void isGoodForFrame() throws MultiblockValidationException
    {
        if (!isCrate()) {
            throw new MultiblockValidationException("is not valid for the frame of the block");
        }
    }

    @Override
    public void isGoodForSides() throws MultiblockValidationException
    {
        if (!isCrate()) {
            throw new MultiblockValidationException("is not valid for the sides of the block");
        }
    }

    @Override
    public void isGoodForTop() throws MultiblockValidationException
    {
        if (!isCrate()) {
            throw new MultiblockValidationException(" is not valid for the sides of the block");
        }
    }

    @Override
    public void isGoodForBottom() throws MultiblockValidationException
    {
        if (!isCrate()) {
            throw new MultiblockValidationException(" is not valid for the sides of the block");
        }
    }

    @Override
    public void isGoodForInterior() throws MultiblockValidationException
    {
        if (!isCrate()) {
            throw new MultiblockValidationException(" is not valid for the inside of the block");
        }
    }

    @Override
    public void onMachineActivated() {}

    @Override
    public void onMachineDeactivated() {}

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
    public void update() {}

//    @Nullable
//    @Override
//    public SPacketUpdateTileEntity getUpdatePacket()
//    {
//        return new SPacketUpdateTileEntity(getPos(), 0, writeToNBT(new NBTTagCompound()));
//    }
//
//    @Override
//    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
//    {
//        super.onDataPacket(net, pkt);
//        readFromNBT(pkt.getNbtCompound());
//    }
//
//    @Override
//    public NBTTagCompound getUpdateTag() {
//        return writeToNBT(new NBTTagCompound());
//    }

    boolean isCrate()
    {
        return getWorld().getBlockState(getPos()).getBlock() instanceof BlockCrate;
    }

    ItemStackHandler invTile = new ItemStackHandler(5);

    public TileCrate() {}

    @Override
    public void readFromNBT(NBTTagCompound data)
    {
        super.readFromNBT(data);
        invTile.deserializeNBT(data);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data)
    {
        data = super.writeToNBT(data);
        data.merge(invTile.serializeNBT());
        return data;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && getMultiBlock() != null)
        {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && getMultiBlock() != null)
        {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getMultiBlock().getInv());
        }
        return super.getCapability(capability, facing);
    }
}
