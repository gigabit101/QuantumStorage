package QuantumStorage.containers;

import QuantumStorage.QuantumStorage;
import QuantumStorage.containers.prefab.ContainerQS;
import QuantumStorage.tiles.chests.TileChestIron;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class ContainerChestIron extends ContainerQS
{
    public ContainerChestIron(int id, PlayerInventory playerInv, PacketBuffer extraData)
    {
        this(id, playerInv, (TileChestIron) Objects.requireNonNull(Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos())));
    }

    public ContainerChestIron(int id, PlayerInventory playerInv, TileChestIron te)
    {
        super(QuantumStorage.containerChestIronContainerType, id);

        int i = 0;
        for (int l = 0; l < 4; ++l)
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