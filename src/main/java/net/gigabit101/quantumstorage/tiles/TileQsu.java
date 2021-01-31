package net.gigabit101.quantumstorage.tiles;

import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.gigabit101.quantumstorage.containers.ContainerQSU;
import net.gigabit101.quantumstorage.init.QSBlocks;
import net.gigabit101.quantumstorage.inventory.DsuInventoryHandler;
import net.gigabit101.quantumstorage.network.VanillaPacketDispatcher;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 03/04/2017.
 */
public class TileQsu extends TileEntity implements INamedContainerProvider, ITickableTileEntity
{
    int INPUT = 0;
    int STORAGE = 1;
    int OUTPUT = 2;

    public ItemStack lockedStack = ItemStack.EMPTY;
    public DsuInventoryHandler inventory = new DsuInventoryHandler(lockedStack);
    public boolean isLocked = false;

    public TileQsu()
    {
        super(QSBlocks.QSU_TILE.get());
    }
    
    @Override
    public void onLoad()
    {
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }
    
    @Override
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
            if(world.getTileEntity(pos) != null)
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
        inventory.deserializeNBT(compound);
        isLocked = compound.getBoolean("locked");
        if(!compound.getCompound("lockeditem").isEmpty()) {
            ItemStack stack = readItemStack(compound.getCompound("lockeditem"));
            lockedStack = stack;
            inventory.updateLockedStack(stack);
        }
    }

    @Override
    @Nonnull
    public CompoundNBT write(CompoundNBT compound)
    {
        compound = super.write(compound);
        compound.merge(inventory.serializeNBT());
        compound.putBoolean("locked", isLocked);
        compound.put("lockeditem", writeItemStack(lockedStack));

        return compound;
    }

    private static CompoundNBT writeItemStack(ItemStack i) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("count", i.getCount());
        nbt.putString("item", i.getItem().getRegistryName().toString());
        nbt.putByte("type", (byte) 0);
        return nbt;
    }

    private static ItemStack readItemStack(CompoundNBT compound) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(compound.getString("item")));
        int count = compound.getInt("count");
        return new ItemStack(item, count);
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent("tile.qsu.name");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity)
    {
        return new ContainerQSU(id, playerEntity.inventory, this);
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
    
    public void writeToNBTWithoutCoords(CompoundNBT tagCompound)
    {
        tagCompound = super.write(tagCompound);
        if (inventory != null)
        {
            tagCompound.merge(inventory.serializeNBT());
        }
        tagCompound.putBoolean("locked", isLocked);
    }
    
    public void readFromNBTWithoutCoords(CompoundNBT compound)
    {
        inventory.deserializeNBT(compound);
        isLocked = compound.getBoolean("locked");
    }
    
    public ItemStack getDropWithNBT()
    {
        CompoundNBT tileEntity = new CompoundNBT();
        ItemStack dropStack = new ItemStack(QSBlocks.QSU.get(), 1);
        writeToNBTWithoutCoords(tileEntity);
        dropStack.setTag(new CompoundNBT());
        dropStack.getTag().put("tileEntity", tileEntity);
        return dropStack;
    }
}
