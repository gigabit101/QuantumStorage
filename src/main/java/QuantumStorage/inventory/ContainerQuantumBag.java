package QuantumStorage.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.items.IItemHandlerModifiable;

//@ChestContainer(isLargeChest = true, rowSize = 13)
public class ContainerQuantumBag extends Container
{
    public ContainerQuantumBag(PlayerEntity player, IItemHandlerModifiable invBag)
    {
        super(new ContainerType<>(this));
//        int i = 0;
//        for (int l = 0; l < 7; ++l)
//        {
//            for (int j1 = 0; j1 < 13; ++j1)
//            {
//                this.addSlotToContainer(new SlotBlacklist(invBag, i, 8 + j1 * 18, 11 + l * 18, new ItemStack(ModItems.BAG)));
//                i++;
//            }
//        }
//        drawPlayersInv(player, 45, 151);
//        drawPlayersHotBar(player, 45, 151 + 58);
    }
    
    @Override
    public boolean canInteractWith(PlayerEntity playerIn)
    {
        return true;
    }
}
