package net.gigabit101.quantumstorage.client.keybinding;

import net.minecraft.client.settings.KeyBinding;

public class KeyBindings
{
    public static KeyBinding openBag = new KeyBinding(KeyBindings.Keys.BAG, 42, KeyBindings.Keys.CATEGORY);
    
    public static final class Keys
    {
        public static final String CATEGORY = "keys.quantumstorage.category";
        public static final String BAG = "keys.quantumstorage.openbag";
    }
}


