package net.gigabit101.quantumstorage.menu;

import net.gigabit101.quantumstorage.init.ModMenus;
import net.gigabit101.quantumstorage.inventory.InventoryQuantumStorageUnit;
import net.gigabit101.quantumstorage.menu.prefab.MenuBase;
import net.gigabit101.quantumstorage.menu.slots.SlotFiltered;
import net.gigabit101.quantumstorage.menu.slots.SlotNoTake;
import net.gigabit101.quantumstorage.menu.slots.SlotOutput;
import net.gigabit101.quantumstorage.tiles.TileQuantumStorageUnit;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class MenuQuantumStorageUnit extends MenuBase
{
    private final InventoryQuantumStorageUnit inventory;
    public boolean hasMemoryCard;

    public MenuQuantumStorageUnit(int id, Inventory playerInventory, TileQuantumStorageUnit te)
    {
        super(ModMenus.QUANTUM_STORAGE_UNIT_MENU.get(), id);
        inventory = te.getInventory();
        hasMemoryCard = te.hasMemoryCard();

        if(hasMemoryCard)
        {
            addSlot(new SlotFiltered(te.getInventory(), 0, 159, 33, () -> te.getItem(1)));

            addSlot(new SlotNoTake(te.getInventory(), 1, 132, 76));

            addSlot(new SlotOutput(te.getInventory(), 2, 159, 77));
        }

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
}
