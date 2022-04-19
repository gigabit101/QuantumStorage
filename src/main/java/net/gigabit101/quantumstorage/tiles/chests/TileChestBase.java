package net.gigabit101.quantumstorage.tiles.chests;

import net.gigabit101.quantumstorage.init.ModBlocks;
import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileChestBase extends BaseContainerBlockEntity
{
    public ItemStackHandler inventory;
    int slots;

    public TileChestBase(BlockEntityType<?> tileEntityTypeIn, BlockPos blockPos, BlockState blockState, int slots)
    {
        super(tileEntityTypeIn, blockPos, blockState);
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
    public void load(CompoundTag compound)
    {
        super.load(compound);
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
            else if(getLevel() != null)
                ItemUtils.dropItem(oldInventory.getStackInSlot(slot), getLevel(), getBlockPos(), true, 10);
            else
                System.out.println("[quantumstorage] couldn't drop stack which was exceded crate limit");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag)
    {
        compoundTag.put("inv", inventory.serializeNBT());
    }

    @Override
    protected Component getDefaultName()
    {
        return new TextComponent("");
    }

    @Override
    protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_)
    {
        return null;
    }

    //TODO
    public void writeToNBTWithoutCoords(CompoundTag tagCompound)
    {
//        tagCompound = super.write(tagCompound);
//        if (inventory != null)
//        {
//            tagCompound.merge(inventory.serializeNBT());
//        }
    }

    public void readFromNBTWithoutCoords(CompoundTag compound)
    {
        inventory.deserializeNBT(compound);
    }

    public ItemStack getDropWithNBT()
    {
        CompoundTag tileEntityNBT = new CompoundTag();
        ItemStack dropStack = new ItemStack(ModBlocks.CHEST_IRON.get(), 1);
        writeToNBTWithoutCoords(tileEntityNBT);
        dropStack.getOrCreateTag().put("tileEntity", tileEntityNBT);
        return dropStack;
    }

    @Override
    public int getContainerSize()
    {
        return inventory.getSlots();
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public ItemStack getItem(int slot)
    {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount)
    {
        return inventory.extractItem(slot, amount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot)
    {
        return inventory.extractItem(slot, 64, false);
    }

    @Override
    public void setItem(int slot, ItemStack stack)
    {
        inventory.setStackInSlot(slot, stack);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    @Override
    public void clearContent()
    {
        //TODO

    }
}
