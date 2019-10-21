//package net.gigabit101.quantumstorage.client.keybinding;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.client.event.InputEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//
//public class KeyInputEventHandler
//{
//    private static Key getPressedKeybinding()
//    {
//        if (KeyBindings.openBag.isPressed())
//        {
//            return Key.CONFIG;
//        }
//        return Key.UNKNOWN;
//    }
//
//    @SubscribeEvent
//    public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
//    {
//        if(KeyBindings.openBag.isPressed())
//            onKeyPressed();
//    }
//
//    private void onKeyPressed()
//    {
//        Minecraft mc = Minecraft.getInstance();
//        if (mc.currentScreen != null)
//            return;
//
//        if(mc.player.inventory.hasItemStack(new ItemStack(ModItems.BAG)))
//        {
//            int slot = mc.player.inventory.getSlotFor(new ItemStack(ModItems.BAG));
////            int colour = mc.player.inventory.getStackInSlot(slot).getItemDamage();
//
////            mc.player.openGui(QuantumStorage.INSTANCE, GuiHandler.BAG_ID_PACKET, mc.world, 0, 0, colour);
////            NetworkManager.sendToServer(new PacketGuiBag(colour));
//        }
//    }
//}
