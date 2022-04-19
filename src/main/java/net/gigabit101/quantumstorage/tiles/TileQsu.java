package net.gigabit101.quantumstorage.tiles;

import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.gigabit101.quantumstorage.containers.ContainerQSU;
import net.gigabit101.quantumstorage.init.ModBlocks;
import net.gigabit101.quantumstorage.inventory.DsuInventoryHandler;
import net.gigabit101.quantumstorage.network.VanillaPacketDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 03/04/2017.
 */
public class TileQsu extends BaseContainerBlockEntity
{
    int INPUT = 0;
    int STORAGE = 1;
    int OUTPUT = 2;

    public ItemStack lockedStack = ItemStack.EMPTY;
    public DsuInventoryHandler inventory = new DsuInventoryHandler(lockedStack);
    public boolean isLocked = false;

    public TileQsu(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.QSU_TILE.get(), blockPos, blockState);
    }
    
    @Override
    public void onLoad()
    {
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }
    
    public void tick()
    {
        try
        {
            if (!inventory.getStackInSlot(INPUT).isEmpty())
            {
                if (inventory.getStackInSlot(STORAGE).isEmpty() && (inventory.getStackInSlot(OUTPUT).isEmpty() || ItemUtils.isItemEqual(inventory.getStackInSlot(INPUT), inventory.getStackInSlot(OUTPUT), true)))
                {
                    inventory.setStackInSlot(STORAGE, inventory.getStackInSlot(INPUT).copy());
                    inventory.setStackInSlot(INPUT, ItemStack.EMPTY);
                } else if (!inventory.getStackInSlot(STORAGE).isEmpty() && ItemUtils.isItemEqual(inventory.getStackInSlot(INPUT), inventory.getStackInSlot(STORAGE), true))
                {
                    inventory.getStackInSlot(STORAGE).grow(inventory.getStackInSlot(INPUT).getCount());
                    inventory.setStackInSlot(INPUT, ItemStack.EMPTY);
                }
            }
        
            if (!inventory.getStackInSlot(STORAGE).isEmpty())
            {
                int size = inventory.getStackInSlot(STORAGE).getMaxStackSize();
                if (inventory.getStackInSlot(OUTPUT) == ItemStack.EMPTY || inventory.getStackInSlot(OUTPUT).getCount() == 0)
                {
                    if (inventory.getStackInSlot(STORAGE).getCount() >= size)
                    {
                        inventory.setStackInSlot(OUTPUT, inventory.getStackInSlot(STORAGE).copy());
                        inventory.getStackInSlot(OUTPUT).setCount(size);
                        inventory.getStackInSlot(STORAGE).shrink(size);
                    } else
                    {
                        inventory.setStackInSlot(OUTPUT, inventory.getStackInSlot(STORAGE));
                        inventory.setStackInSlot(STORAGE, ItemStack.EMPTY);
                    }
                }
                if (inventory.getStackInSlot(STORAGE).getCount() != 0 && ItemUtils.isItemEqual(inventory.getStackInSlot(STORAGE), inventory.getStackInSlot(OUTPUT), true) && inventory.getStackInSlot(OUTPUT).getCount() <= size - 1)
                {
                    inventory.getStackInSlot(OUTPUT).grow(1);
                    inventory.getStackInSlot(STORAGE).shrink(1);
                }
            }
            if(level.getBlockEntity(getBlockPos()) != null)
            {
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
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
    
//    @Nullable
//    @Override
//    public SUpdateTileEntityPacket getUpdatePacket()
//    {
//        return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
//    }
//
//    @Override
//    public CompoundNBT getUpdateTag()
//    {
//        return this.write(new CompoundNBT());
//    }
//
//    @Override
//    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
//    {
//        super.onDataPacket(net, packet);
//        this.deserializeNBT(packet.getNbtCompound());
//    }


    @Override
    public void load(CompoundTag compound)
    {
        super.load(compound);
        inventory.deserializeNBT(compound);
        isLocked = compound.getBoolean("locked");
        if(!compound.getCompound("lockeditem").isEmpty()) {
            ItemStack stack = readItemStack(compound.getCompound("lockeditem"));
            lockedStack = stack;
            inventory.updateLockedStack(stack);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compound)
    {
        super.saveAdditional(compound);
        compound.merge(inventory.serializeNBT());
        compound.putBoolean("locked", isLocked);
        compound.put("lockeditem", writeItemStack(lockedStack));
    }

    private static CompoundTag writeItemStack(ItemStack i) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("count", i.getCount());
        nbt.putString("item", i.getItem().getRegistryName().toString());
        nbt.putByte("type", (byte) 0);
        return nbt;
    }

    private static ItemStack readItemStack(CompoundTag compound) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(compound.getString("item")));
        int count = compound.getInt("count");
        return new ItemStack(item, count);
    }

    @Override
    public Component getDisplayName()
    {
        return new TranslatableComponent("tile.qsu.name");
    }

    @Override
    protected Component getDefaultName()
    {
        return new TranslatableComponent("tile.qsu.name");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory)
    {
        return new ContainerQSU(id, playerInventory, this);
    }

    public boolean isLocked(TileQsu tileQsu)
    {
        return tileQsu.isLocked;
    }

    public void setLocked(TileQsu tileQsu)
    {
        tileQsu.isLocked = true;
        tileQsu.lockedStack = tileQsu.inventory.getStackInSlot(2);
        inventory.updateLockedStack(tileQsu.lockedStack);
    }

    public void setUnlocked(TileQsu tileQsu)
    {
        tileQsu.isLocked = false;
        tileQsu.lockedStack = ItemStack.EMPTY;
    }
    
    public void writeToNBTWithoutCoords(CompoundTag tagCompound)
    {
//        tagCompound = super.write(tagCompound);
        if (inventory != null)
        {
            tagCompound.merge(inventory.serializeNBT());
        }
        tagCompound.putBoolean("locked", isLocked);
    }
    
    public void readFromNBTWithoutCoords(CompoundTag compound)
    {
        inventory.deserializeNBT(compound);
        isLocked = compound.getBoolean("locked");
    }
    
    public ItemStack getDropWithNBT()
    {
        CompoundTag tileEntity = new CompoundTag();
        ItemStack dropStack = new ItemStack(ModBlocks.QSU.get(), 1);
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
