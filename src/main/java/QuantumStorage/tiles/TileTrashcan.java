package QuantumStorage.tiles;

import QuantumStorage.QuantumStorage;
import QuantumStorage.containers.ContainerChestIron;
import QuantumStorage.containers.ContainerTrashcan;
import QuantumStorage.inventory.InventoryTrashcan;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 03/04/2017.
 */
public class TileTrashcan extends TileEntity implements INamedContainerProvider, ITickableTileEntity
{
    public InventoryTrashcan inventory = new InventoryTrashcan();

    public TileTrashcan()
    {
        super(QuantumStorage.tileTrashcan);
    }

    @Override
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
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent("tile.trashcan.name");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity)
    {
        return new ContainerTrashcan(id, playerEntity.inventory, this);
    }
}
