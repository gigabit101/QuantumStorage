package net.gigabit101.quantumstorage.tiles.chests;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerChestIron;
import net.gigabit101.quantumstorage.init.QSBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
public class TileChestIron extends TileEntity implements INamedContainerProvider
{
    public ItemStackHandler inventory = new ItemStackHandler(36);

    public TileChestIron()
    {
        super(QSBlocks.CHEST_IRON_TILE.get());
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
        }
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT compound)
    {
        compound.put("inv", inventory.serializeNBT());
        return super.write(compound);
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent("tile.chestiron.name");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity)
    {
        return new ContainerChestIron(id, playerEntity.inventory, this);
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
        CompoundNBT tileEntity = new CompoundNBT();
        ItemStack dropStack = new ItemStack(QSBlocks.CHEST_IRON.get(), 1);
        writeToNBTWithoutCoords(tileEntity);
        dropStack.setTag(new CompoundNBT());
        dropStack.getTag().put("tileEntity", tileEntity);
        return dropStack;
    }
}
