package QuantumStorage.multiblock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import reborncore.common.multiblock.IMultiblockPart;
import reborncore.common.multiblock.MultiblockControllerBase;
import reborncore.common.multiblock.rectangular.RectangularMultiblockControllerBase;
import reborncore.common.util.Inventory;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class MultiBlockStorage extends RectangularMultiblockControllerBase
{
    public Map<Integer, Inventory> invs = new TreeMap<>();
    public int pages = 0;
    
    public MultiBlockStorage(World world)
    {
        super(world);
    }
    
    @Override
    public void onAttachedPartWithMultiblockData(IMultiblockPart iMultiblockPart, NBTTagCompound nbtTagCompound)
    {
    }
    
    @Override
    protected void onBlockAdded(IMultiblockPart iMultiblockPart)
    {
    }
    
    @Override
    protected void onBlockRemoved(IMultiblockPart iMultiblockPart)
    {
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
    
    public void updateInfo()
    {
        pages = 0;
        invs.clear();
        TreeMap<Integer, TileMultiStorage> collector = new TreeMap<>();
        int append = 2745;
        
        for (IMultiblockPart part : connectedParts)
        {
            if (part.getBlockState().getValue(BlockMultiStorage.VARIANTS).equals("storage"))
            {
                pages++;
                TileMultiStorage tile = (TileMultiStorage) part;
                
                if (tile.page.isPresent())
                {
                    collector.put(tile.page.get(), tile);
                } else
                {
                    collector.put(append++, tile);
                }
            }
        }
        
        int newid = 0;
        for (TileMultiStorage tile : collector.values())
        {
            newid++;
            tile.page = Optional.of(newid);
            invs.put(newid, tile.inv);
        }
    }
    
    public Inventory getInvForPage(int page)
    {
        return invs.get(page);
    }
    
    @Override
    protected void onMachineDisassembled()
    {
    }
    
    @Override
    protected int getMinimumNumberOfBlocksForAssembledMachine()
    {
        return (9 * 3);
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
        return 3;
    }
    
    @Override
    protected int getMinimumYSize()
    {
        return 3;
    }
    
    @Override
    protected int getMinimumZSize()
    {
        return 3;
    }
    
    @Override
    protected void onAssimilate(MultiblockControllerBase multiblockControllerBase)
    {
    }
    
    @Override
    protected void onAssimilated(MultiblockControllerBase multiblockControllerBase)
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
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
    }
    
    @Override
    public void formatDescriptionPacket(NBTTagCompound nbtTagCompound)
    {
        writeToNBT(nbtTagCompound);
    }
    
    @Override
    public void decodeDescriptionPacket(NBTTagCompound nbtTagCompound)
    {
        readFromNBT(nbtTagCompound);
    }
}
