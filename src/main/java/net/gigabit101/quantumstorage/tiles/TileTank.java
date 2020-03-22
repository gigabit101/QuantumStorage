package net.gigabit101.quantumstorage.tiles;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerTank;
import net.gigabit101.quantumstorage.init.QSBlocks;
import net.gigabit101.quantumstorage.network.VanillaPacketDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 03/04/2017.
 */
public class TileTank extends TileEntity implements INamedContainerProvider
{
    public TileTank()
    {
        super(QSBlocks.TANK_TILE.get());
    }
    
    public FluidTank tank = new FluidTank(Integer.MAX_VALUE);
    
    @Override
    public void onLoad()
    {
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, final @Nullable Direction side)
    {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return LazyOptional.of(() -> tank).cast();
        }
        return super.getCapability(cap, side);
    }
    
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
    }
    
    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
    {
        super.onDataPacket(net, packet);
        this.read(packet.getNbtCompound());
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        tank.readFromNBT(compound);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT compound)
    {
        compound = super.write(compound);
        compound.merge(tank.writeToNBT(compound));
        return compound;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent("tile.tank.name");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity)
    {
        return new ContainerTank(id, playerEntity.inventory, this);
    }
    
    public void writeToNBTWithoutCoords(CompoundNBT tagCompound)
    {
        tagCompound = super.write(tagCompound);
        if (tank != null)
        {
            tagCompound.merge(tank.writeToNBT(tagCompound));
        }
    }
    
    public void readFromNBTWithoutCoords(CompoundNBT compound)
    {
        tank.readFromNBT(compound);
    }
    
    public ItemStack getDropWithNBT()
    {
        CompoundNBT tileEntity = new CompoundNBT();
        ItemStack dropStack = new ItemStack(QSBlocks.TANK.get(), 1);
        writeToNBTWithoutCoords(tileEntity);
        dropStack.setTag(new CompoundNBT());
        dropStack.getTag().put("tileEntity", tileEntity);
        return dropStack;
    }
}
