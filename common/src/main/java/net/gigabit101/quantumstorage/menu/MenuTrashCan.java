package net.gigabit101.quantumstorage.menu;

import net.gigabit101.quantumstorage.init.ModMenus;
import net.gigabit101.quantumstorage.inventory.InventoryTrashcan;
import net.gigabit101.quantumstorage.menu.prefab.MenuBase;
import net.gigabit101.quantumstorage.tiles.TileTrashCan;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public class MenuTrashCan extends MenuBase
{
    public InventoryTrashcan inventory;

    public MenuTrashCan(int id, Inventory playerInventory, TileTrashCan te)
    {
        super(ModMenus.TRASHCAN_MENU.get(), id);
        this.inventory = te.inventory;
        addSlot(new Slot(te.inventory,1, 88, 51));

        drawPlayersInv(playerInventory, 15, 132);
        drawPlayersHotBar(playerInventory, 15, 132 + 58);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }
}
