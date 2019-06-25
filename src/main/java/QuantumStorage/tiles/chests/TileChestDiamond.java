package QuantumStorage.tiles.chests;

import QuantumStorage.QuantumStorage;
import QuantumStorage.containers.ContainerChestDiamond;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
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
public class TileChestDiamond extends TileEntity implements INamedContainerProvider
{
    public ItemStackHandler inventory = new ItemStackHandler(91);

    public TileChestDiamond()
    {
        super(QuantumStorage.tileChestDiamond);
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
    public void read(CompoundNBT compound)
    {
        super.read(compound);
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
        return new TranslationTextComponent("tile.chestdiamond.name");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity)
    {
        return new ContainerChestDiamond(id, playerEntity.inventory, this);
    }
}
