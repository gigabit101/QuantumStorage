package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.containers.prefab.ContainerQS;
import net.gigabit101.quantumstorage.inventory.slot.SlotAntiCrate;
import net.gigabit101.quantumstorage.tiles.chests.TileChestBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class ContainerChestBase extends ContainerQS {
    public ContainerChestBase(@Nullable ContainerType<?> containerType, int id){
        super(containerType, id);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }

    public void addSlots(TileChestBase te, int columns, PlayerInventory playerInv){
        int row = 0;

        for (int slot = 0; slot < te.inventory.getSlots(); ++row)
            for (int column = 0; column < columns && slot < te.inventory.getSlots(); ++column, slot++)
                addSlot(new SlotAntiCrate(te.inventory, slot, 14 + column * 18, 18 + row * 18));


        final int playerInventoryPosY = row*18 + 35;
        final int playerInventoryPosX = 14 + ((columns - 9) * 18 / 2);

        drawPlayersInv(playerInv, playerInventoryPosX, playerInventoryPosY);
        drawPlayersHotBar(playerInv, playerInventoryPosX, playerInventoryPosY + 58);
    }
}
