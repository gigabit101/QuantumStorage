package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.containers.prefab.ContainerQS;
import net.gigabit101.quantumstorage.tiles.chests.TileChestGold;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class ContainerChestGold extends ContainerQS
{
    public ContainerChestGold(int id, PlayerInventory playerInv, PacketBuffer extraData)
    {
        this(id, playerInv, (TileChestGold) Objects.requireNonNull(Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos())));
    }

    public ContainerChestGold(int id, PlayerInventory playerInv, TileChestGold te)
    {
        super(QuantumStorage.containerChestGoldContainerType, id);

        int i = 0;
        for (int l = 0; l < 6; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                addSlot(new SlotItemHandler(te.inventory, i, 14 + j1 * 18, 8 + l * 18));
                i++;
            }
        }

        drawPlayersInv(playerInv, 15, 132);
        drawPlayersHotBar(playerInv, 15, 132 + 58);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }
}
