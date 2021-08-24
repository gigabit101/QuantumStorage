package net.gigabit101.quantumstorage.menu;

import net.gigabit101.quantumstorage.init.ModMenus;
import net.gigabit101.quantumstorage.menu.slots.SlotNoTake;
import net.gigabit101.quantumstorage.menu.slots.SlotOutput;
import net.gigabit101.quantumstorage.tiles.TileQuantumStorageUnit;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MenuQuantumStorageUnit extends AbstractContainerMenu
{
    private final InventoryQuantumStorageUnit inventory;

    public MenuQuantumStorageUnit(int id, Inventory playerInventory, TileQuantumStorageUnit te)
    {
        super(ModMenus.QUANTUM_STORAGE_UNIT_MENU.get(), id);
        inventory = te.getInventory();

        addSlot(new Slot(te.getInventory(),0, 159, 33));

        addSlot(new SlotNoTake(te.getInventory(),1, 132, 76));

        addSlot(new SlotOutput(te.getInventory(),2, 159, 77));

        drawPlayersInv(playerInventory, 15, 132);
        drawPlayersHotBar(playerInventory, 15, 132 + 58);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }

    public Container getInventory()
    {
        return inventory;
    }

    public void drawPlayersInv(Inventory player, int x, int y)
    {
        int i;
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(player, j + i * 9 + 9, x + j * 18, y + i * 18));
            }
        }

    }

    public void drawPlayersHotBar(Inventory player, int x, int y)
    {
        int i;
        for (i = 0; i < 9; ++i)
        {
            this.addSlot(new Slot(player, i, x + i * 18, y));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex)
    {
        ItemStack originalStack = ItemStack.EMPTY;
        Slot slot = (Slot) slots.get(slotIndex);
        int numSlots = slots.size();
        if (slot != null && slot.hasItem())
        {
            ItemStack stackInSlot = slot.getItem();
            originalStack = stackInSlot.copy();
            if (slotIndex >= numSlots - 9 * 4 && tryShiftItem(stackInSlot, numSlots))
            {
                // NOOP
            } else if (slotIndex >= numSlots - 9 * 4 && slotIndex < numSlots - 9)
            {
                if (!shiftItemStack(stackInSlot, numSlots - 9, numSlots))
                {
                    return ItemStack.EMPTY;
                }
            } else if (slotIndex >= numSlots - 9 && slotIndex < numSlots)
            {
                if (!shiftItemStack(stackInSlot, numSlots - 9 * 4, numSlots - 9))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!shiftItemStack(stackInSlot, numSlots - 9 * 4, numSlots))
            {
                return ItemStack.EMPTY;
            }
            slot.onQuickCraft(stackInSlot, originalStack);
            if (stackInSlot.getCount() <= 0)
            {
                slot.set(ItemStack.EMPTY);
            }
            else
            {
                slot.setChanged();
            }
            if (stackInSlot.getCount() == originalStack.getCount())
            {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, stackInSlot);
        }
        return originalStack;
    }

    protected boolean shiftItemStack(ItemStack stackToShift, int start, int end)
    {
        boolean changed = false;
        if (stackToShift.isStackable())
        {
            for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < end; slotIndex++)
            {
                Slot slot = (Slot) slots.get(slotIndex);
                ItemStack stackInSlot = slot.getItem();
                if (!stackInSlot.isEmpty() && canStacksMerge(stackInSlot, stackToShift))
                {
                    int resultingStackSize = stackInSlot.getCount() + stackToShift.getCount();
                    int max = Math.min(stackToShift.getMaxStackSize(), slot.getMaxStackSize());
                    if (resultingStackSize <= max)
                    {
                        stackToShift.setCount(0);
                        stackInSlot.setCount(resultingStackSize);
                        slot.setChanged();
                        changed = true;
                    } else if (stackInSlot.getCount() < max)
                    {
                        stackToShift.setCount(stackToShift.getCount()-(max-stackInSlot.getCount()));
                        stackInSlot.setCount(max);
                        slot.setChanged();
                        changed = true;
                    }
                }
            }
        }
        if (stackToShift.getCount() > 0)
        {
            for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < end; slotIndex++)
            {
                Slot slot = (Slot) slots.get(slotIndex);
                ItemStack stackInSlot = slot.getItem();
                if (stackInSlot.isEmpty())
                {
                    int max = Math.min(stackToShift.getMaxStackSize(), slot.getMaxStackSize());
                    stackInSlot = stackToShift.copy();
                    stackInSlot.setCount(Math.min(stackToShift.getCount(), max));
                    stackToShift.setCount(stackToShift.getCount()-stackInSlot.getCount());
                    slot.set(stackInSlot);
                    slot.setChanged();
                    changed = true;
                }
            }
        }
        return changed;
    }


    private boolean tryShiftItem(ItemStack stackToShift, int numSlots)
    {
        for (int machineIndex = 0; machineIndex < numSlots - 9 * 4; machineIndex++)
        {
            Slot slot = (Slot) slots.get(machineIndex);
            if (!slot.mayPlace(stackToShift)) continue;
            if (shiftItemStack(stackToShift, machineIndex, machineIndex + 1)) return true;
        }
        return false;
    }

    public static boolean canStacksMerge(ItemStack stack1, ItemStack stack2)
    {
        if (stack1.isEmpty() || stack2.isEmpty()) return false;
        if (!stack1.sameItem(stack2)) return false;
        if (!ItemStack.isSameItemSameTags(stack1, stack2)) return false;
        return true;
    }
}
