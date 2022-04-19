package net.gigabit101.quantumstorage.guis;

import net.gigabit101.quantumstorage.containers.ContainerChestIron;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiChestIron extends GuiChestBase<ContainerChestIron>
{
    public GuiChestIron(ContainerChestIron container, Inventory playerinv, Component title)
    {
        super(container, playerinv, title, 190, 190);
    }
}
