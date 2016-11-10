package QuantumStorage.client.container;

import QuantumStorage.tile.prefab.TileQChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.common.container.RebornContainer;

/**
 * Created by Gigabit101 on 01/11/2016.
 */
public class ContainerQChest extends RebornContainer
{
    int size;
    private static final EntityEquipmentSlot[] equipmentSlots = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};

    public ContainerQChest(TileQChest tile, EntityPlayer player)
    {
        super();
        this.size = tile.getInvSize();

        int j;
        int k;
        int i = 0;

        for (j = 0; j < 6; ++j)
        {
            for (k = 0; k < 13; ++k)
            {
                i++;
                if(i <= size)
                    this.addSlotToContainer(new Slot(tile, i -1 , 9 + k * 18, 21 + j * 18));
            }
        }

        //Armour slots
        for (int k1 = 0; k1 < 4; k1++)
        {
            final EntityEquipmentSlot slot = equipmentSlots[k1];
            this.addSlotToContainer(new Slot(player.inventory, 36 + (3 - k1), 9, 141 + k1 * 18)
            {
                @Override
                public int getSlotStackLimit()
                {
                    return 1;
                }

                @Override
                public boolean isItemValid(ItemStack stack)
                {
                    if (stack == null)
                    {
                        return false;
                    } else
                    {
                        return stack.getItem().isValidArmor(stack, slot, player);
                    }
                }

                @Override
                @SideOnly(Side.CLIENT)
                public String getSlotTexture()
                {
                    return ItemArmor.EMPTY_SLOT_NAMES[slot.getIndex()];
                }
            });
        }
        drawPlayersInv(player, 45, 141);
        drawPlayersHotBar(player, 45, 199);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }
}
