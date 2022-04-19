package net.gigabit101.quantumstorage.guis;

import net.gigabit101.quantumstorage.containers.ContainerChestDiamond;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiChestDiamond extends GuiChestBase<ContainerChestDiamond>
{
    public GuiChestDiamond(ContainerChestDiamond container, Inventory playerinv, Component title)
    {
        super(container, playerinv, title, 240, 280);
    }
}
