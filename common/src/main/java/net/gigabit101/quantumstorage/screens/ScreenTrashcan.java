package net.gigabit101.quantumstorage.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.gigabit101.quantumstorage.client.GuiBuilder;
import net.gigabit101.quantumstorage.menu.MenuQuantumStorageUnit;
import net.gigabit101.quantumstorage.menu.MenuTrashCan;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

public class ScreenTrashcan extends AbstractContainerScreen<MenuTrashCan>
{
    private final GuiBuilder guiBuilder = new GuiBuilder();
    private final MenuTrashCan menuTrashCan;

    public ScreenTrashcan(MenuTrashCan menuTrashCan, Inventory inventory, Component component)
    {
        super(menuTrashCan, inventory, component);
        this.menuTrashCan = menuTrashCan;
        this.imageHeight = 220;
        this.imageWidth = 190;
        this.inventoryLabelX = 14;
        this.inventoryLabelY = topPos + 117;
        this.titleLabelX = leftPos + (imageWidth / 2) - 30;
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f)
    {
        renderBackground(poseStack);
        super.render(poseStack, i, j, f);
        renderTooltip(poseStack, i, j);
    }

    @Override
    public void renderBg(PoseStack poseStack, float f, int i, int j)
    {
        guiBuilder.drawDefaultBackground(poseStack, leftPos, topPos, imageWidth, imageHeight);

        guiBuilder.drawPlayerSlots(poseStack, leftPos + imageWidth / 2, topPos + 131, true);

        guiBuilder.drawSlot(poseStack, leftPos + imageWidth / 2 - 8, topPos + 50);
    }
}
