package QuantumStorage.client.keybinding;

import QuantumStorage.GuiHandler;
import QuantumStorage.QuantumStorage;
import QuantumStorage.init.ModItems;
import QuantumStorage.network.PacketGuiBag;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import reborncore.common.network.NetworkManager;

public class KeyInputEventHandler
{
    private static Key getPressedKeybinding()
    {
        if (KeyBindings.openBag.isPressed())
        {
            return Key.CONFIG;
        }
        return Key.UNKNOWN;
    }
    
    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
    {
        if(KeyBindings.openBag.isPressed())
            onKeyPressed();
    }
    
    private void onKeyPressed()
    {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen != null)
            return;
        
        if(mc.player.inventory.hasItemStack(new ItemStack(ModItems.BAG)))
        {
            int slot = mc.player.inventory.getSlotFor(new ItemStack(ModItems.BAG));
            int colour = mc.player.inventory.getStackInSlot(slot).getItemDamage();
            
            mc.player.openGui(QuantumStorage.INSTANCE, GuiHandler.BAG_ID_PACKET, mc.world, 0, 0, colour);
            NetworkManager.sendToServer(new PacketGuiBag(colour));
        }
    }
}
