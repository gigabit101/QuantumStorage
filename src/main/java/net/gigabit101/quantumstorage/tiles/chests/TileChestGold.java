package net.gigabit101.quantumstorage.tiles.chests;

import net.gigabit101.quantumstorage.containers.ContainerChestGold;
import net.gigabit101.quantumstorage.init.QSBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 03/04/2017.
 */
public class TileChestGold extends TileChestBase
{
    public TileChestGold()
    {
        super(QSBlocks.CHEST_GOLD_TILE.get(), 54);
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent("tile.chestgold.name");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity)
    {
        return new ContainerChestGold(id, playerEntity.inventory, this);
    }
}
