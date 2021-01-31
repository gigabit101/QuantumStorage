package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.containers.prefab.ContainerQS;
import net.gigabit101.quantumstorage.containers.slots.SlotLocked;
import net.gigabit101.quantumstorage.items.backpack.ItemQuantumBag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBag extends ContainerQS
{
    public static final int SLOTS_TE = 0;
    public static final int SLOTS_TE_SIZE = 8 * 13;
    public static final int SLOTS_INVENTORY = SLOTS_TE_SIZE;
    public static final int SLOTS_HOTBAR = SLOTS_INVENTORY + 3 * 9;

    private ItemStackHandler inv;
    private Hand bagHand;
    private ItemStack bag;

    public ContainerBag(int id, PlayerInventory pInv) {
        super(QuantumStorage.quantumBag, id);
        bag = pInv.player.getHeldItemMainhand();
        bagHand = Hand.MAIN_HAND;
        if (bag.isEmpty() || !(bag.getItem() instanceof ItemQuantumBag)) {
            bag = pInv.player.getHeldItemOffhand();
            bagHand = Hand.OFF_HAND;
        }

        inv = ItemQuantumBag.getHandlerForContainer(bag);

        int j;
        int k;

        for (j = 0; j < 8; ++j) {
            for (k = 0; k < 13; ++k) {
                this.addSlot(new SlotItemHandler(inv, k + j * 13, 12 + k * 18, 5 + j * 18));
            }
        }

        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(pInv, k + j * 9 + 9, 48 + k * 18, 152 + j * 18));
            }
        }

        for (j = 0; j < 9; ++j) {
            if (pInv.currentItem == j) {
                this.addSlot(new SlotLocked(pInv, j, 48 + j * 18, 210));
            } else {
                addSlot(new Slot(pInv, j, 48 + j * 18, 210));
            }
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return !bag.isEmpty() && player.getHeldItem(bagHand).getItem() instanceof ItemQuantumBag;
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        if (!bag.hasTag()) bag.setTag(new CompoundNBT());
        bag.getTag().put("inv", inv.serializeNBT());
        super.onContainerClosed(player);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slot_index) {
        ItemStack slot_stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(slot_index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            slot_stack = stack.copy();
            if (slot_index >= SLOTS_INVENTORY && slot_index <= SLOTS_HOTBAR + 9) {
                if (!mergeItemStack(stack, SLOTS_TE, SLOTS_TE + SLOTS_TE_SIZE, false)) { return ItemStack.EMPTY; }
            } else if (slot_index >= SLOTS_HOTBAR && slot_index < SLOTS_HOTBAR + 9) {
                if (!mergeItemStack(stack, SLOTS_INVENTORY, SLOTS_INVENTORY + 3 * 9, false)) { return ItemStack.EMPTY; }
            } else if (!mergeItemStack(stack, SLOTS_INVENTORY, SLOTS_HOTBAR + 9, true)) { return ItemStack.EMPTY; }
            slot.onSlotChanged();
            if (stack.getCount() == slot_stack.getCount()) { return ItemStack.EMPTY; }
            slot.onTake(player, stack);
        }
        return slot_stack;
    }
}
