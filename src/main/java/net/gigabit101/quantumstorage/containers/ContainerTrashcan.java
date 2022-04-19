package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.containers.prefab.ContainerQS;
import net.gigabit101.quantumstorage.init.ModContainers;
import net.gigabit101.quantumstorage.tiles.TileTrashcan;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class ContainerTrashcan extends ContainerQS
{
    public ContainerTrashcan(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv, (TileTrashcan) Objects.requireNonNull(Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos())));
    }

    public ContainerTrashcan(int id, Inventory playerInv, TileTrashcan te)
    {
        super(ModContainers.TRASHCAN_CONTAINER.get(), id);

        addSlot(new SlotItemHandler(te.inventory,0, 87, 61));

        drawPlayersInv(playerInv, 15, 132);
        drawPlayersHotBar(playerInv, 15, 132 + 58);
    }

    @Override
    public boolean stillValid(Player player)
    {
        return true;
    }
}
