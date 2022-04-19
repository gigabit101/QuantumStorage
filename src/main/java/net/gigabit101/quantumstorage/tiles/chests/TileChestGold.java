package net.gigabit101.quantumstorage.tiles.chests;

import net.gigabit101.quantumstorage.containers.ContainerChestGold;
import net.gigabit101.quantumstorage.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

/**
 * Created by Gigabit101 on 03/04/2017.
 */
public class TileChestGold extends TileChestBase
{
    public TileChestGold(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.CHEST_GOLD_TILE.get(), blockPos, blockState, 54);
    }

    @Override
    public Component getDisplayName()
    {
        return new TranslatableComponent("tile.chestgold.name");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory)
    {
        return new ContainerChestGold(id, playerInventory, this);
    }

    @Override
    public ItemStack getDropWithNBT()
    {
        CompoundTag tileEntityNBT = new CompoundTag();
        ItemStack dropStack = new ItemStack(ModBlocks.CHEST_GOLD.get(), 1);
        writeToNBTWithoutCoords(tileEntityNBT);
        dropStack.getOrCreateTag().put("tileEntity", tileEntityNBT);
        return dropStack;
    }
}
