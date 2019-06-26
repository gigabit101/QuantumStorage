package QuantumStorage.containers;

import QuantumStorage.QuantumStorage;
import QuantumStorage.containers.prefab.ContainerQS;
import QuantumStorage.tiles.TileTrashcan;
import QuantumStorage.tiles.chests.TileChestIron;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class ContainerTrashcan extends ContainerQS
{
    private IItemHandler inv;

    public ContainerTrashcan(int id, PlayerInventory playerInv, PacketBuffer extraData)
    {
        this(id, playerInv, (TileTrashcan) Objects.requireNonNull(Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos())));
    }

    public ContainerTrashcan(int id, PlayerInventory playerInv, TileTrashcan te)
    {
        super(QuantumStorage.containerTrashcanContainerType, id);

        addSlot(new SlotItemHandler(te.inventory,0, 87, 61));

        drawPlayersInv(playerInv, 15, 132);
        drawPlayersHotBar(playerInv, 15, 132 + 58);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }

    public void drawPlayersInv(PlayerInventory player, int x, int y)
    {
        int i;
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlot(new Slot(player, j + i * 9 + 9, x + j * 18, y + i * 18));
            }
        }

    }

    public void drawPlayersHotBar(PlayerInventory player, int x, int y)
    {
        int i;
        for (i = 0; i < 9; ++i)
        {
            this.addSlot(new Slot(player, i, x + i * 18, y));
        }
    }
}
