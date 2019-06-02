package QuantumStorage.client.keybinding;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyBindings
{
    public static KeyBinding openBag = new KeyBinding(KeyBindings.Keys.BAG, Keyboard.KEY_NUMPAD1, KeyBindings.Keys.CATEGORY);
    
    public static final class Keys
    {
        public static final String CATEGORY = "keys.quantumstorage.category";
        public static final String BAG = "keys.quantumstorage.openbag";
    }
}


