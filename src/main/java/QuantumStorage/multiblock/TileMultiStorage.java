package QuantumStorage.multiblock;

import QuantumStorage.inventory.CachingItemHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import reborncore.common.multiblock.MultiblockControllerBase;
import reborncore.common.multiblock.MultiblockValidationException;
import reborncore.common.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import reborncore.common.util.Inventory;

import javax.annotation.Nullable;
import java.util.Optional;

public class TileMultiStorage extends RectangularMultiblockTileEntityBase
{
    @Override
    public void isGoodForFrame() throws MultiblockValidationException
    {
        if (!getVarient().equals("frame"))
        {
            throw new MultiblockValidationException(getVarient() + " is not valid for the frame of the block");
        }
    }
    
    @Override
    public void isGoodForSides() throws MultiblockValidationException
    {
        if (!getVarient().equals("heat") && !getVarient().equals("io") && !getVarient().equals("interface"))
        {
            throw new MultiblockValidationException(getVarient() + " is not valid for the sides of the block");
        }
    }
    
    @Override
    public void isGoodForTop() throws MultiblockValidationException
    {
        if (!getVarient().equals("heat") && !getVarient().equals("io") && !getVarient().equals("interface"))
        {
            throw new MultiblockValidationException(getVarient() + " is not valid for the sides of the block");
        }
    }
    
    @Override
    public void isGoodForBottom() throws MultiblockValidationException
    {
        if (!getVarient().equals("heat"))
        {
            throw new MultiblockValidationException(getVarient() + " is not valid for the sides of the block");
        }
    }
    
    @Override
    public void isGoodForInterior() throws MultiblockValidationException
    {
        if (!getVarient().equals("storage"))
        {
            throw new MultiblockValidationException(getVarient() + " is not valid for the inside of the block");
        }
    }
    
    @Override
    public void onMachineActivated()
    {
        if (getMultiBlock() != null)
        {
            update();
        }
    }
    
    @Override
    public void onMachineDeactivated()
    {
    }
    
    String getVarient()
    {
        return world.getBlockState(pos).getValue(BlockMultiStorage.VARIANTS);
    }
    
    @Override
    public MultiblockControllerBase createNewMultiblock()
    {
        return new MultiBlockStorage(getWorld());
    }
    
    @Override
    public Class<? extends MultiblockControllerBase> getMultiblockControllerType()
    {
        return MultiBlockStorage.class;
    }
    
    public MultiBlockStorage getMultiBlock()
    {
        return (MultiBlockStorage) getMultiblockController();
    }
    
    
    public CachingItemHandler inv;
    public Optional<Integer> page = Optional.empty();
    
    public TileMultiStorage(String varient)
    {
        if (varient.equals("storage"))
        {
            inv = new CachingItemHandler(78);
        }
    }
    
    @Override
    public void onLoad()
    {
    }
    
    @Override
    public void readFromNBT(NBTTagCompound data)
    {
        super.readFromNBT(data);
        if (inv == null && data.hasKey("hasInv"))
        {
            inv = new CachingItemHandler(78);
        }
        if (inv != null)
        {
            inv.deserializeNBT(data);
        }
        if (data.hasKey("page"))
        {
            page = Optional.of(data.getInteger("page"));
        }
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data)
    {
        if (inv != null)
        {
            data = super.writeToNBT(data);
            data.merge(inv.serializeNBT());
            data.setBoolean("hasInv", true);
        }
        if (page.isPresent())
        {
            data.setInteger("page", page.get());
        }
        return super.writeToNBT(data);
    }
    
    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(getPos(), 0, writeToNBT(new NBTTagCompound()));
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }
    
    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }
    
    public TileMultiStorage()
    {
    }
    
    @Override
    public void update()
    {
    }
}
