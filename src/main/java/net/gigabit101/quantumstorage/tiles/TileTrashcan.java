package net.gigabit101.quantumstorage.tiles;

import net.gigabit101.quantumstorage.containers.ContainerTrashcan;
import net.gigabit101.quantumstorage.init.ModBlocks;
import net.gigabit101.quantumstorage.inventory.InventoryTrashcan;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 03/04/2017.
 */
public class TileTrashcan extends BaseContainerBlockEntity
{
    public InventoryTrashcan inventory = new InventoryTrashcan();

    public TileTrashcan(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.TRASH_CAN_TILE.get(), blockPos, blockState);
    }

    public void tick()
    {
        if(!inventory.getStackInSlot(0).isEmpty())
        {
            inventory.setStackInSlot(0, ItemStack.EMPTY);
        }
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
    protected Component getDefaultName()
    {
        return new TextComponent("trashcan");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory)
    {
        return new ContainerTrashcan(id, inventory, this);
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
