package QuantumStorage.containers;

import QuantumStorage.QuantumStorage;
import QuantumStorage.containers.prefab.ContainerQS;
import QuantumStorage.inventory.slot.SlotOutputItemHandler;
import QuantumStorage.tiles.TileQsu;
import QuantumStorage.tiles.TileTrashcan;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class ContainerQSU extends ContainerQS
{
    public IItemHandler inv;

    public ContainerQSU(int id, PlayerInventory playerInv, PacketBuffer extraData)
    {
        this(id, playerInv, (TileQsu) Objects.requireNonNull(Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos())));
    }

    public ContainerQSU(int id, PlayerInventory playerInv, TileQsu te)
    {
        super(QuantumStorage.containerQsuContainerType, id);
        this.inv = te.inventory;

        addSlot(new SlotItemHandler(te.inventory,0, 87, 31));
        addSlot(new SlotOutputItemHandler(te.inventory,2, 87, 81));
    
        drawPlayersInv(playerInv, 15, 132);
        drawPlayersHotBar(playerInv, 15, 132 + 58);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }
    
    public IItemHandler getInv()
    {
        return this.inv;
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
