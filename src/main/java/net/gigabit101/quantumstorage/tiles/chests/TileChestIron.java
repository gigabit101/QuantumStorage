package net.gigabit101.quantumstorage.tiles.chests;

import net.gigabit101.quantumstorage.containers.ContainerChestIron;
import net.gigabit101.quantumstorage.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 03/04/2017.
 */
public class TileChestIron extends TileChestBase {
    public TileChestIron(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.CHEST_IRON_TILE.get(), blockPos, blockState, 36);
    }

    @Override
    public Component getDisplayName()
    {
        return new TranslatableComponent("tile.chestiron.name");
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory)
    {
        return new ContainerChestIron(id, playerInventory, this);
    }
}
