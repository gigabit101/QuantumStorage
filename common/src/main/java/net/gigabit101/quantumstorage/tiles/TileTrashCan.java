package net.gigabit101.quantumstorage.tiles;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.init.ModBlocks;
import net.gigabit101.quantumstorage.menu.MenuTrashCan;
import net.gigabit101.quantumstorage.menu.inventory.InventoryTrashcan;
import net.minecraft.core.BlockPos;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TileTrashCan extends BaseContainerBlockEntity
{
    public InventoryTrashcan inventory;

    public TileTrashCan(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.TRASH_CAN_TILE.get(), blockPos, blockState);
        inventory = new InventoryTrashcan(1);
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

    @Override
    public Component getDefaultName()
    {
        return new TranslatableComponent("container." + QuantumStorage.MOD_ID + ".trashcan");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory)
    {
        return new MenuTrashCan(i, inventory, this);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
    {
        return new MenuTrashCan(i, inventory, this);
    }
}
