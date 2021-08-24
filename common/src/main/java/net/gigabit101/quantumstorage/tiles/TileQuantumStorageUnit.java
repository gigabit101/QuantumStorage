package net.gigabit101.quantumstorage.tiles;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.init.ModBlocks;
import net.gigabit101.quantumstorage.menu.inventory.InventoryQuantumStorageUnit;
import net.gigabit101.quantumstorage.menu.MenuQuantumStorageUnit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TileQuantumStorageUnit extends BaseContainerBlockEntity
{
    private InventoryQuantumStorageUnit inventory;
    static int INPUT = 0;
    static int STORAGE = 1;
    static int OUTPUT = 2;

    public TileQuantumStorageUnit(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.QUANTUM_STORAGE_UNIT_TILE.get(), blockPos, blockState);
        this.inventory = new InventoryQuantumStorageUnit(3);
    }

    @Override
    public Component getDisplayName()
    {
        return getDefaultName();
    }

    @Override
    protected Component getDefaultName()
    {
        return new TranslatableComponent("container." + QuantumStorage.MOD_ID + ".qsu");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory)
    {
        return new MenuQuantumStorageUnit(i, inventory, this);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
    {
        return new MenuQuantumStorageUnit(i, inventory, this);
    }

    public CompoundTag saveToTag(CompoundTag compoundTag)
    {
        compoundTag.merge(inventory.serializeNBT());
        return compoundTag;
    }

    @Override
    public void load(CompoundTag compoundTag)
    {
        super.load(compoundTag);
        inventory.deserializeNBT(compoundTag);
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag)
    {
        CompoundTag compoundTag1 = super.save(compoundTag);
        compoundTag1.merge(inventory.serializeNBT());
        return compoundTag1;
    }

    public InventoryQuantumStorageUnit getInventory()
    {
        return inventory;
    }

    @Override
    public int getContainerSize()
    {
        return inventory.getContainerSize();
    }

    @Override
    public boolean isEmpty()
    {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getItem(int i)
    {
        return inventory.getItem(i);
    }

    @Override
    public ItemStack removeItem(int i, int j)
    {
        return inventory.removeItem(i, j);
    }

    @Override
    public ItemStack removeItemNoUpdate(int i)
    {
        return inventory.removeItemNoUpdate(i);
    }

    @Override
    public void setItem(int i, ItemStack itemStack)
    {
        inventory.setItem(i, itemStack);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return inventory.stillValid(player);
    }

    @Override
    public void clearContent()
    {
        inventory.clearContent();
    }

    public static void tick(Level world, BlockPos pos, BlockState state, BlockEntity blockEntity)
    {
        TileQuantumStorageUnit tileQuantumStorageUnit = (TileQuantumStorageUnit) blockEntity;
        InventoryQuantumStorageUnit inventory = tileQuantumStorageUnit.getInventory();

        try
        {
            if (!inventory.getItem(INPUT).isEmpty())
            {
                if (inventory.getItem(STORAGE).isEmpty() && (inventory.getItem(OUTPUT).isEmpty() || ItemStack.isSame(inventory.getItem(INPUT), inventory.getItem(OUTPUT))))
                {
                    inventory.setItem(STORAGE, inventory.getItem(INPUT).copy());
                    inventory.setItem(INPUT, ItemStack.EMPTY);
                    tileQuantumStorageUnit.setChanged();
                } else if (!inventory.getItem(STORAGE).isEmpty() && ItemStack.isSame(inventory.getItem(INPUT), inventory.getItem(STORAGE)))
                {
                    inventory.getItem(STORAGE).grow(inventory.getItem(INPUT).getCount());
                    inventory.setItem(INPUT, ItemStack.EMPTY);
                    tileQuantumStorageUnit.setChanged();
                }
            }

            if (!inventory.getItem(STORAGE).isEmpty())
            {
                int size = inventory.getItem(STORAGE).getMaxStackSize();
                if (inventory.getItem(OUTPUT) == ItemStack.EMPTY || inventory.getItem(OUTPUT).getCount() == 0)
                {
                    if (inventory.getItem(STORAGE).getCount() >= size)
                    {
                        inventory.setItem(OUTPUT, inventory.getItem(STORAGE).copy());
                        inventory.getItem(OUTPUT).setCount(size);
                        inventory.getItem(STORAGE).shrink(size);
                        tileQuantumStorageUnit.setChanged();
                    } else
                    {
                        inventory.setItem(OUTPUT, inventory.getItem(STORAGE));
                        inventory.setItem(STORAGE, ItemStack.EMPTY);
                        tileQuantumStorageUnit.setChanged();
                    }
                }
                if (inventory.getItem(STORAGE).getCount() != 0 && ItemStack.isSame(inventory.getItem(STORAGE), inventory.getItem(OUTPUT)) && inventory.getItem(OUTPUT).getCount() <= size - 1)
                {
                    inventory.getItem(OUTPUT).grow(1);
                    inventory.getItem(STORAGE).shrink(1);
                    tileQuantumStorageUnit.setChanged();
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
