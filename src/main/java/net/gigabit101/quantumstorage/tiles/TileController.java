package net.gigabit101.quantumstorage.tiles;

import net.gigabit101.quantumstorage.init.ModBlocks;
import net.gigabit101.quantumstorage.inventory.ControllerItemHandler;
import net.gigabit101.quantumstorage.network.VanillaPacketDispatcher;
import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileController extends BlockEntity
{
    List<BlockPos> connectedTiles = new ArrayList<>();
    public ControllerItemHandler inventory = new ControllerItemHandler(this);

    public int maxConnections = 180;

    public TileController(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.CONTROLLER_TILE.get(), blockPos, blockState);
    }

    @Override
    public void onLoad()
    {
        inventory.setSize(connectedTiles.size());
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }

    public boolean connectTileToController(BlockPos connectPos)
    {
        if(level.getBlockEntity(connectPos) != null && level.getBlockEntity(connectPos) instanceof TileQsu && connectedTiles.size() < maxConnections)
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
    public void load(CompoundTag compound)
    {
        super.load(compound);
        deserializeNBT(compound);
        inventory.deserializeNBT(compound);
    }

    @Override
    @Nonnull
    protected void saveAdditional(CompoundTag compound)
    {
        compound.merge(serializeNBT());
        compound.merge(inventory.serializeNBT());
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
                    TileQsu tileQsu = (TileQsu) level.getBlockEntity(pos1);
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
            TileQsu tileQsu = (TileQsu) level.getBlockEntity(connectedTiles.get(slotID));
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
            TileQsu tileQsu = (TileQsu) level.getBlockEntity(connectedTiles.get(slotID));
            int amount = tileQsu.inventory.getStackInSlot(0).getCount() + tileQsu.inventory.getStackInSlot(1).getCount() + tileQsu.inventory.getStackInSlot(2).getCount();
            ItemStack stackOut = tileQsu.inventory.getStackInSlot(2).copy();
            stackOut.setCount(amount);
            return stackOut;
        }
        return ItemStack.EMPTY;
    }

    public int getSlotLimit(int slot)
    {
        if(connectedTiles.isEmpty()) return 0;
        if(connectedTiles.get(slot) == null) return 0;

        if(validateConnection(connectedTiles.get(slot)))
        {
            TileQsu tileQsu = (TileQsu) level.getBlockEntity(connectedTiles.get(slot));
            return tileQsu.inventory.getSlotLimit(2);
        }
        return 0;
    }

    public boolean validateConnection(BlockPos blockPos)
    {
        if(blockPos == null) return false;
        if(!level.isLoaded(blockPos)) return false;
        if(level.getBlockEntity(blockPos) == null) return false;

        return level.getBlockEntity(blockPos) instanceof TileQsu;
    }



    public CompoundTag serializeNBT()
    {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < connectedTiles.size(); i++)
        {
            if (connectedTiles.get(i) != null)
            {
                CompoundTag posTag = new CompoundTag();
                posTag.putInt("X", connectedTiles.get(i).getX());
                posTag.putInt("Y", connectedTiles.get(i).getY());
                posTag.putInt("Z", connectedTiles.get(i).getZ());

                nbtTagList.add(posTag);
            }
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put("connections", nbtTagList);
        nbt.putInt("Size", connectedTiles.size());
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt)
    {
        ListTag tagList = nbt.getList("connections", Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++)
        {
            CompoundTag itemTags = tagList.getCompound(i);
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
