//package QuantumStorage.inventory;
//
//import QuantumStorage.tiles.AdvancedTileEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.inventory.container.Container;
//import net.minecraft.inventory.container.Slot;
//
///**
// * Created by Gigabit101 on 17/03/2017.
// */
////@ChestContainer(isLargeChest = true)
//public class AdvancedContainer extends Container
//{
//    public AdvancedTileEntity machine;
//
//    public AdvancedContainer(PlayerEntity player, AdvancedTileEntity machine)
//    {
//        super();
//        this.machine = machine;
//        if (machine.getSlots() != null)
//        {
//            for (Slot s : machine.getSlots())
//            {
//                addSlotToContainer(s);
//            }
//        }
//        drawPlayersInv(player, machine.inventoryOffsetX(), machine.inventoryOffsetY());
//        drawPlayersHotBar(player, machine.inventoryOffsetX(), machine.inventoryOffsetY() + 58);
//    }
//
////    @ChestContainer.RowSizeCallback
////    public int getNumColumns()
////    {
////        if (machine instanceof TileChestIron)
////        {
////            return 9;
////        }
////        if (machine instanceof TileChestGold)
////        {
////            return 9;
////        }
////        if (machine instanceof TileChestDiamond)
////        {
////            return 13;
////        }
////        return 0;
////    }
//
//    @Override
//    public boolean canInteractWith(PlayerEntity playerIn) {
//        return machine != null;
//    }
//}
