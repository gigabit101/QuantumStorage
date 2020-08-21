package net.gigabit101.quantumstorage.guis;

import net.gigabit101.quantumstorage.containers.ContainerChestGold;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiChestGold extends GuiChestBase<ContainerChestGold> {

    public GuiChestGold(ContainerChestGold container, PlayerInventory playerinv, ITextComponent title) {
        super(container, playerinv, title, 190, 225);
    }


}
