package net.gigabit101.quantumstorage.tiles;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerTank;
import net.gigabit101.quantumstorage.init.QSBlocks;
import net.gigabit101.quantumstorage.network.VanillaPacketDispatcher;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 03/04/2017.
 */
public class TileTank extends TileEntity implements INamedContainerProvider, ITickableTileEntity
{
    public FluidTank tank = new FluidTank(Integer.MAX_VALUE);
    public ItemStackHandler inventory = new ItemStackHandler(2);
    
    public TileTank()
    {
        super(QSBlocks.TANK_TILE.get());
    }

    @Override
    public void tick()
    {
        if(!inventory.getStackInSlot(0).isEmpty())
        {
            ItemStack inputStack = inventory.getStackInSlot(0);
            ItemStack outputStack = inventory.getStackInSlot(1);
            Item item = inputStack.getItem();
            if (item instanceof BucketItem && item != Items.BUCKET)
            {
                if (outputStack.isEmpty() || ((outputStack.getItem() == Items.BUCKET) && outputStack.getCount() <outputStack.getMaxStackSize()))
                {
                    FluidActionResult fillResult = FluidUtil.tryEmptyContainer(inputStack, tank, Integer.MAX_VALUE, null, true);
                    if (fillResult.isSuccess())
                    {
                        inventory.extractItem(0, 1, false);
                        inventory.insertItem(1, fillResult.getResult(), false);
                    }
                }
            }
            inputStack = inventory.getStackInSlot(0);
            outputStack = inventory.getStackInSlot(1);
            item = inputStack.getItem();
            if (item == Items.BUCKET && outputStack.isEmpty())
            {
                FluidActionResult fillResult = FluidUtil.tryFillContainer(inputStack, tank, Integer.MAX_VALUE, null, true);
                if (fillResult.isSuccess())
                {
                    inventory.extractItem(0, 1, false);
                    inventory.insertItem(1, fillResult.getResult(), false);
                }
            }
        }
    }
    
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
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return LazyOptional.of(() -> inventory).cast();
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
        this.deserializeNBT(packet.getNbtCompound());
    }

    @Override
    public void read(BlockState state, CompoundNBT compound)
    {
        super.read(state, compound);
        tank.readFromNBT(compound);
        inventory.deserializeNBT(compound);
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT compound)
    {
        compound = super.write(compound);
        tank.writeToNBT(compound);
        compound.merge(inventory.serializeNBT());
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

        if (tank != null) tagCompound.merge(tank.writeToNBT(tagCompound));
        if (inventory != null) tagCompound.merge(inventory.serializeNBT());
    }
    
    public void readFromNBTWithoutCoords(CompoundNBT compound)
    {
        tank.readFromNBT(compound);
        inventory.deserializeNBT(compound);
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
