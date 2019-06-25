package QuantumStorage.containers;

import QuantumStorage.QuantumStorage;
import QuantumStorage.tiles.chests.TileChestDiamond;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class ContainerChestDiamond extends Container
{
    private IItemHandler inv;

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
            for (int j1 = 0; j1 < 9; ++j1)
            {
                addSlot(new SlotItemHandler(inv, i, 14 + j1 * 18, 8 + l * 18));
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
