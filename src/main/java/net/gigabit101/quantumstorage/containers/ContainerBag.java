package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.containers.prefab.ContainerQS;
import net.gigabit101.quantumstorage.init.ModContainers;
import net.gigabit101.quantumstorage.inventory.slot.SlotAntiBag;
import net.gigabit101.quantumstorage.items.backpack.ItemQuantumBag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBag extends ContainerQS
{
    public static final int SLOTS_TE = 0;
    public static final int SLOTS_TE_SIZE = 8 * 13;
    public static final int SLOTS_INVENTORY = SLOTS_TE_SIZE;
    public static final int SLOTS_HOTBAR = SLOTS_INVENTORY + 3 * 9;

    private ItemStackHandler inv;
    private InteractionHand bagHand;
    private ItemStack bag;

    public ContainerBag(int id, Inventory pInv) {
        super(null, id);
        bag = pInv.player.getItemInHand(InteractionHand.MAIN_HAND);
        bagHand = InteractionHand.MAIN_HAND;
        if (bag.isEmpty() || !(bag.getItem() instanceof ItemQuantumBag)) {
            bag = pInv.player.getOffhandItem();
            bagHand = InteractionHand.OFF_HAND;
        }

        inv = ItemQuantumBag.getHandlerForContainer(bag);

        int j;
        int k;

        for (j = 0; j < 8; ++j) {
            for (k = 0; k < 13; ++k) {
                this.addSlot(new SlotAntiBag(inv, k + j * 13, 12 + k * 18, 5 + j * 18));
            }
        }

        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(pInv, k + j * 9 + 9, 48 + k * 18, 152 + j * 18));
            }
        }

        for (j = 0; j < 9; ++j) {
            if (pInv.selected == j) {
                //TODO
//                this.addSlot(new SlotLocked(pInv, j, 48 + j * 18, 210));
            } else {
                addSlot(new Slot(pInv, j, 48 + j * 18, 210));
            }
        }
    }

    //TODO
//    @Override
//    public boolean canInteractWith(PlayerEntity player) {
//        return !bag.isEmpty() && player.getHeldItem(bagHand).getItem() instanceof ItemQuantumBag;
//    }
//
//    @Override
//    public void onContainerClosed(PlayerEntity player) {
//        if (!bag.hasTag()) bag.setTag(new CompoundNBT());
//        bag.getTag().put("inv", inv.serializeNBT());
//        super.onContainerClosed(player);
//    }
//
//    @Override
//    public ItemStack transferStackInSlot(PlayerEntity player, int slot_index) {
//        ItemStack slot_stack = ItemStack.EMPTY;
//        Slot slot = inventorySlots.get(slot_index);
//        if (slot != null && slot.getHasStack()) {
//            ItemStack stack = slot.getStack();
//            slot_stack = stack.copy();
//            if (slot_index >= SLOTS_INVENTORY && slot_index <= SLOTS_HOTBAR + 9) {
//                if (!mergeItemStack(stack, SLOTS_TE, SLOTS_TE + SLOTS_TE_SIZE, false)) { return ItemStack.EMPTY; }
//            } else if (slot_index >= SLOTS_HOTBAR && slot_index < SLOTS_HOTBAR + 9) {
//                if (!mergeItemStack(stack, SLOTS_INVENTORY, SLOTS_INVENTORY + 3 * 9, false)) { return ItemStack.EMPTY; }
//            } else if (!mergeItemStack(stack, SLOTS_INVENTORY, SLOTS_HOTBAR + 9, true)) { return ItemStack.EMPTY; }
//            slot.onSlotChanged();
//            if (stack.getCount() == slot_stack.getCount()) { return ItemStack.EMPTY; }
//            slot.onTake(player, stack);
//        }
//        return slot_stack;
//    }
}
