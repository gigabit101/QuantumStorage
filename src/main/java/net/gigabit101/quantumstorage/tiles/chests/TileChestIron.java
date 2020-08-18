package net.gigabit101.quantumstorage.tiles.chests;

import net.gigabit101.quantumstorage.containers.ContainerChestIron;
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
public class TileChestIron extends TileChestBase {
    public TileChestIron()
    {
        super(QSBlocks.CHEST_IRON_TILE.get(), 36);
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return new TranslationTextComponent("tile.chestdiamond.name");
    }


    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity)
    {
        return new ContainerChestIron(id, playerEntity.inventory, this);
    }
}
