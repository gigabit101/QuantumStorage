package QuantumStorage.multiblock;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import reborncore.common.multiblock.IMultiblockPart;
import reborncore.common.multiblock.MultiblockControllerBase;
import reborncore.common.multiblock.MultiblockValidationException;

/**
 * Created by Gigabit101 on 12/05/2017.
 */
public class MultiBlockCrate extends MultiblockControllerBase
{
    ItemStackHandler inv;// = new ItemStackHandler(5);

    public int size = 5;

    public MultiBlockCrate(World world)
    {
        super(world);
        this.inv = new ItemStackHandler(size);
    }

    @Override
    public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data)
    {
        readFromNBT(data);
    }

    @Override
    protected void onBlockAdded(IMultiblockPart newPart)
    {
    }

    @Override
    protected void onBlockRemoved(IMultiblockPart oldPart)
    {
        updateInfo();
    }

    @Override
    protected void onMachineAssembled()
    {
        updateInfo();
    }

    @Override
    protected void onMachineRestored()
    {
    }

    @Override
    protected void onMachinePaused()
    {
    }

    @Override
    protected void onMachineDisassembled()
    {
    }

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine()
    {
        return (1);
    }

    @Override
    protected int getMaximumXSize()
    {
        return 16;
    }

    @Override
    protected int getMaximumZSize()
    {
        return 16;
    }

    @Override
    protected int getMaximumYSize()
    {
        return 16;
    }

    @Override
    protected int getMinimumXSize()
    {
        return 1;
    }

    @Override
    protected int getMinimumYSize()
    {
        return 1;
    }

    @Override
    protected int getMinimumZSize()
    {
        return 1;
    }

    @Override
    protected void isMachineWhole() throws MultiblockValidationException
    {
    }

    @Override
    protected void onAssimilate(MultiblockControllerBase assimilated)
    {

    }

    @Override
    protected void onAssimilated(MultiblockControllerBase assimilator)
    {

    }

    @Override
    protected boolean updateServer()
    {
        return true;
    }

    @Override
    protected void updateClient()
    {
    }

    @Override
    public void writeToNBT(NBTTagCompound data)
    {
        data.setInteger("invSize", inv.getSlots());
        data.merge(inv.serializeNBT());
    }

    @Override
    public void readFromNBT(NBTTagCompound data)
    {
        if (data.hasKey("invSize"))
        {
            inv = new ItemStackHandler(data.getInteger("invSize"));
            size = data.getInteger("invSize");
        } else
        {
            inv = new ItemStackHandler(5);
        }
        inv.deserializeNBT(data);
    }

    @Override
    public void formatDescriptionPacket(NBTTagCompound data)
    {
        writeToNBT(data);
    }

    @Override
    public void decodeDescriptionPacket(NBTTagCompound data)
    {
        readFromNBT(data);
    }

    public void updateInfo()
    {
        size = 0;
        for (IMultiblockPart part : connectedParts)
        {
            if (part.isConnected() && !part.isVisited())
            {
                size += 5;
                part.setVisited();
            }
            part.setUnvisited();
            setSize(size);
        }
        if (connectedParts.size() == 1)
        {
            setSize(5);
        }
    }

    public void setSize(int size)
    {
        this.size = size;
        if (isAssembled())
        {
            ItemStackHandler copy = new ItemStackHandler(size);
            int slot = 0;
            while (slot < Math.min(inv.getSlots(), copy.getSlots()))
            {
                ItemStack stack = this.inv.getStackInSlot(slot);
                if (!stack.isEmpty())
                {
                    copy.setStackInSlot(slot, stack);
                    inv.setStackInSlot(slot, ItemStack.EMPTY);
                }
                slot++;
            }
            this.inv = copy;
        }
    }

    public ItemStackHandler getInv()
    {
        return this.inv;
    }

    protected void addSlotlessItemHandler()
    {
        IItemHandler iItemHandler = getInv();
    }
}
