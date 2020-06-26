package net.gigabit101.quantumstorage.containers;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.containers.prefab.ContainerQS;
import net.gigabit101.quantumstorage.tiles.chests.TileChestDiamond;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class ContainerChestDiamond extends ContainerQS
{
    public ContainerChestDiamond(int id, PlayerInventory playerInv, PacketBuffer extraData)
    {
        this(id, playerInv, (TileChestDiamond) Objects.requireNonNull(Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos())));
    }

    public ContainerChestDiamond(int id, PlayerInventory playerInv, TileChestDiamond te)
    {
        super(QuantumStorage.containerChestDiamondContainerType, id);

        int i = 0;
        for (int l = 0; l < 6; ++l)
        {
            for (int j1 = 0; j1 < 12; ++j1)
            {
                addSlot(new SlotItemHandler(te.inventory, i, 14 + j1 * 18, 18 + l * 18));
                i++;
            }
        }

        drawPlayersInv(playerInv, 40, 142);
        drawPlayersHotBar(playerInv, 40, 142 + 58);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }
}
