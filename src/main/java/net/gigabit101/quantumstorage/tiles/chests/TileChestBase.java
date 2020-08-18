package net.gigabit101.quantumstorage.tiles.chests;

import net.gigabit101.quantumstorage.init.QSBlocks;
import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TileChestBase extends TileEntity implements INamedContainerProvider {
    public ItemStackHandler inventory;
    int slots;

    public TileChestBase(TileEntityType<?> tileEntityTypeIn, int slots)
    {
        super(tileEntityTypeIn);
        this.slots = slots;
        inventory = new ItemStackHandler(slots);
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

    @Override
    public void read(BlockState state, CompoundNBT compound)
    {
        super.read(state, compound);
        if (compound.contains("inv"))
        {
            inventory.deserializeNBT(compound.getCompound("inv"));
            if(inventory.getSlots() != slots)
                fixInventory();
        }
    }

    // data fixer as the diamond crate had 109 slots but only displayed 108 slots
    private void fixInventory(){
        ItemStackHandler oldInventory = inventory;

        inventory = new ItemStackHandler(slots);

        for(int slot = 0; slot < oldInventory.getSlots(); slot++){
            if(slot < inventory.getSlots())
                inventory.setStackInSlot(slot, oldInventory.getStackInSlot(slot));
            else if(getWorld() != null)
                ItemUtils.dropItem(oldInventory.getStackInSlot(slot), getWorld(), getPos(), true, 10);
            else
                System.out.println("[quantumstorage] couldn't drop stack which was exceded crate limit");
        }
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT compound)
    {
        compound.put("inv", inventory.serializeNBT());
        return super.write(compound);
    }

    public void writeToNBTWithoutCoords(CompoundNBT tagCompound)
    {
        tagCompound = super.write(tagCompound);
        if (inventory != null)
        {
            tagCompound.merge(inventory.serializeNBT());
        }
    }

    public void readFromNBTWithoutCoords(CompoundNBT compound)
    {
        inventory.deserializeNBT(compound);
    }

    public ItemStack getDropWithNBT()
    {
        CompoundNBT tileEntityNBT = new CompoundNBT();
        ItemStack dropStack = new ItemStack(QSBlocks.CHEST_IRON.get(), 1);
        writeToNBTWithoutCoords(tileEntityNBT);
        dropStack.getOrCreateTag().put("tileEntity", tileEntityNBT);
        return dropStack;
    }
}
