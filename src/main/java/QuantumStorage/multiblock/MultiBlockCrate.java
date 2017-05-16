package QuantumStorage.multiblock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.Sys;
import reborncore.common.multiblock.IMultiblockPart;
import reborncore.common.multiblock.MultiblockControllerBase;
import reborncore.common.multiblock.rectangular.RectangularMultiblockControllerBase;
import reborncore.common.network.VanillaPacketDispatcher;

/**
 * Created by Gigabit101 on 12/05/2017.
 */
public class MultiBlockCrate extends RectangularMultiblockControllerBase
{
    ItemStackHandler inv = new ItemStackHandler(0);

    public int SLOT_SIZE = 0;

    public MultiBlockCrate(World world)
    {
        super(world);
    }

    @Override
    public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data)
    {
        readFromNBT(data);
    }

    @Override
    protected void onBlockAdded(IMultiblockPart newPart)
    {
        updateInfo();
    }

    @Override
    protected void onBlockRemoved(IMultiblockPart oldPart)
    {
//        updateInfo();
    }

    @Override
    protected void onMachineAssembled()
    {
//        updateInfo();
    }

    @Override
    protected void onMachineRestored()
    {
//        updateInfo();
    }

    @Override
    protected void onMachinePaused() {}

    @Override
    protected void onMachineDisassembled() {}

    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine()
    {
        return (1);
    }

    @Override
    protected int getMaximumXSize() {
        return 16;
    }

    @Override
    protected int getMaximumZSize() {
        return 16;
    }

    @Override
    protected int getMaximumYSize() {
        return 16;
    }

    @Override
    protected int getMinimumXSize() {
        return 1;
    }

    @Override
    protected int getMinimumYSize() {
        return 1;
    }

    @Override
    protected int getMinimumZSize() {
        return 1;
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
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(worldObj.getTileEntity(this.getReferenceCoord().toBlockPos()));
    }

    @Override
    public void writeToNBT(NBTTagCompound data)
    {

    }

    @Override
    public void readFromNBT(NBTTagCompound data)
    {

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
        SLOT_SIZE = 0;
        for (IMultiblockPart part : connectedParts)
        {
            if(part.isConnected() && !part.isVisited())
            {
                SLOT_SIZE = +5;
                part.setVisited();
                TileCrate crate = (TileCrate)part;
                ItemStackHandler crateInv = crate.invTile;
            }

//            inv.deserializeNBT();
            inv.setSize(SLOT_SIZE);
        }

    }

    public ItemStackHandler getInv()
    {
        return this.inv;
    }
}
