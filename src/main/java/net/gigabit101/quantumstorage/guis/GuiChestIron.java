package net.gigabit101.quantumstorage.guis;

import net.gigabit101.quantumstorage.containers.ContainerChestIron;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class GuiChestIron extends GuiChestBase<ContainerChestIron> {

    public GuiChestIron(ContainerChestIron container, PlayerInventory playerinv, ITextComponent title) {
        super(container, playerinv, title, 190, 190);
    }


}
