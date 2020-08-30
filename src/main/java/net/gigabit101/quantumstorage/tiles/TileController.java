package net.gigabit101.quantumstorage.tiles;

import net.gigabit101.quantumstorage.init.QSBlocks;
import net.gigabit101.quantumstorage.inventory.ControllerItemHandler;
import net.gigabit101.quantumstorage.network.VanillaPacketDispatcher;
import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileController extends TileEntity
{
    List<BlockPos> connectedTiles = new ArrayList<>();
    public ControllerItemHandler inventory = new ControllerItemHandler(this);

    public int maxConnections = 180;

    public TileController()
    {
        super(QSBlocks.CONTROLLER_TILE.get());
    }

    @Override
    public void onLoad()
    {
        inventory.setSize(connectedTiles.size());
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }

    public boolean connectTileToController(BlockPos connectPos)
    {
        if(world.getTileEntity(connectPos) != null && world.getTileEntity(connectPos) instanceof TileQsu && connectedTiles.size() < maxConnections)
        {
            for(BlockPos blockPos : connectedTiles)
            {
                if(blockPosMatches(connectPos, blockPos))
                {
                    return false;
                }
            }
            connectedTiles.add(connectPos);
            inventory.setSize(connectedTiles.size());
            return true;
        }
        return false;
    }

    public boolean removeConnection(BlockPos connectPos)
    {
        if(!connectedTiles.isEmpty())
        {
            for (int i = 0; i < connectedTiles.size(); i++)
            {
                BlockPos posi = connectedTiles.get(i);
                if(blockPosMatches(connectPos, posi))
                {
                    connectedTiles.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean blockPosMatches(BlockPos pos1, BlockPos pos2)
    {
        return (pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY() && pos1.getZ() == pos2.getZ());
    }

    @Override
    public void read(BlockState state, CompoundNBT compound)
    {
        super.read(state, compound);
        deserializeNBT(compound);
        inventory.deserializeNBT(compound);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT compound)
    {
        compound = super.write(compound);
        compound.merge(serializeNBT());
        compound.merge(inventory.serializeNBT());
        return compound;
    }

    public ItemStack insertItem(ItemStack stack, boolean simulate)
    {
        if(stack.isEmpty()) return stack;
        if(connectedTiles.isEmpty()) return stack;

        for(BlockPos pos1 : getConnectedTiles())
        {
            if(pos1 != null)
            {
                if(validateConnection(pos1))
                {
                    TileQsu tileQsu = (TileQsu) world.getTileEntity(pos1);
                    ItemStack insert = ItemHandlerHelper.insertItem(tileQsu.inventory, stack, simulate);
                    if(!ItemUtils.isItemEqual(insert, stack, false))
                    {
                        return insert;
                    }
                }
            }
        }
        return stack;
    }

    public ItemStack extractItem(int slotID, int amount, boolean simulate)
    {
        if (amount == 0) return ItemStack.EMPTY;
        if(connectedTiles == null) return ItemStack.EMPTY;
        if(connectedTiles.get(slotID) == null) return ItemStack.EMPTY;

        if(validateConnection(connectedTiles.get(slotID)))
        {
            TileQsu tileQsu = (TileQsu) world.getTileEntity(connectedTiles.get(slotID));
            return tileQsu.inventory.extractItem(2, amount, simulate);
        }
        return ItemStack.EMPTY;
    }

    public ItemStack getStackInSlot(int slotID)
    {
        if(connectedTiles == null) return ItemStack.EMPTY;
        if(connectedTiles.get(slotID) == null) return ItemStack.EMPTY;

        if(validateConnection(connectedTiles.get(slotID)))
        {
            TileQsu tileQsu = (TileQsu) world.getTileEntity(connectedTiles.get(slotID));
            int amount = tileQsu.inventory.getStackInSlot(0).getCount() + tileQsu.inventory.getStackInSlot(1).getCount() + tileQsu.inventory.getStackInSlot(2).getCount();
            ItemStack stackOut = tileQsu.inventory.getStackInSlot(2).copy();
            stackOut.setCount(amount);
            return stackOut;
        }
        return ItemStack.EMPTY;
    }

    public boolean validateConnection(BlockPos blockPos)
    {
        if(blockPos == null) return false;
        if(!world.isBlockLoaded(blockPos)) return false;
        if(world.getTileEntity(blockPos) == null) return false;

        return world.getTileEntity(blockPos) instanceof TileQsu;
    }

    public CompoundNBT serializeNBT()
    {
        ListNBT nbtTagList = new ListNBT();
        for (int i = 0; i < connectedTiles.size(); i++)
        {
            if (connectedTiles.get(i) != null)
            {
                CompoundNBT posTag = new CompoundNBT();
                posTag.putInt("X", connectedTiles.get(i).getX());
                posTag.putInt("Y", connectedTiles.get(i).getY());
                posTag.putInt("Z", connectedTiles.get(i).getZ());

                nbtTagList.add(posTag);
            }
        }
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("connections", nbtTagList);
        nbt.putInt("Size", connectedTiles.size());
        return nbt;
    }

    public void deserializeNBT(CompoundNBT nbt)
    {
        ListNBT tagList = nbt.getList("connections", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++)
        {
            CompoundNBT itemTags = tagList.getCompound(i);
            BlockPos pos = new BlockPos(itemTags.getInt("X"), itemTags.getInt("Y"), itemTags.getInt("Z"));
            connectedTiles.add(pos);
        }
        onLoad();
    }

    public List<BlockPos> getConnectedTiles()
    {
        return connectedTiles;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, final @Nullable Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return LazyOptional.of(() -> inventory).cast();
        }
        return super.getCapability(cap, side);
    }
}
