package net.gigabit101.quantumstorage.tiles;

import net.gigabit101.quantumstorage.containers.ContainerTank;
import net.gigabit101.quantumstorage.init.ModBlocks;
import net.gigabit101.quantumstorage.network.VanillaPacketDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 03/04/2017.
 */
public class TileTank extends BaseContainerBlockEntity
{
    public FluidTank tank = new FluidTank(Integer.MAX_VALUE);
    public ItemStackHandler inventory = new ItemStackHandler(2);
    
    public TileTank(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.TANK_TILE.get(), blockPos, blockState);
    }

    //TODO
    public void tick()
    {
//        if(!inventory.getStackInSlot(0).isEmpty())
//        {
//            ItemStack inputStack = inventory.getStackInSlot(0);
//            ItemStack outputStack = inventory.getStackInSlot(1);
//            Item item = inputStack.getItem();
//            if (item instanceof BucketItem && item != Items.BUCKET)
//            {
//                if (outputStack.isEmpty() || ((outputStack.getItem() == Items.BUCKET) && outputStack.getCount() <outputStack.getMaxStackSize()))
//                {
//                    FluidActionResult fillResult = FluidUtil.tryEmptyContainer(inputStack, tank, Integer.MAX_VALUE, null, true);
//                    if (fillResult.isSuccess())
//                    {
//                        inventory.extractItem(0, 1, false);
//                        inventory.insertItem(1, fillResult.getResult(), false);
//                    }
//                }
//            }
//            inputStack = inventory.getStackInSlot(0);
//            outputStack = inventory.getStackInSlot(1);
//            item = inputStack.getItem();
//            if (item == Items.BUCKET && outputStack.isEmpty())
//            {
//                FluidActionResult fillResult = FluidUtil.tryFillContainer(inputStack, tank, Integer.MAX_VALUE, null, true);
//                if (fillResult.isSuccess())
//                {
//                    inventory.extractItem(0, 1, false);
//                    inventory.insertItem(1, fillResult.getResult(), false);
//                }
//            }
//        }
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

    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        tank.readFromNBT(compound);
        inventory.deserializeNBT(compound);
    }

    @Override
    @Nonnull
    protected void saveAdditional(CompoundTag compound)
    {
        tank.writeToNBT(compound);
        compound.merge(inventory.serializeNBT());
    }

    @Override
    protected Component getDefaultName()
    {
        return null;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory)
    {
        return new ContainerTank(id, inventory, this);
    }
    
    public void writeToNBTWithoutCoords(CompoundTag tagCompound)
    {
        if (tank != null) tagCompound.merge(tank.writeToNBT(tagCompound));
        if (inventory != null) tagCompound.merge(inventory.serializeNBT());
    }
    
    public void readFromNBTWithoutCoords(CompoundTag compound)
    {
        tank.readFromNBT(compound);
        inventory.deserializeNBT(compound);
    }
    
    public ItemStack getDropWithNBT()
    {
        CompoundTag tileEntity = new CompoundTag();
        ItemStack dropStack = new ItemStack(ModBlocks.TANK.get(), 1);
        writeToNBTWithoutCoords(tileEntity);
        dropStack.setTag(new CompoundTag());
        dropStack.getTag().put("tileEntity", tileEntity);
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
