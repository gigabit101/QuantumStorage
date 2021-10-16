package net.gigabit101.quantumstorage.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.gigabit101.quantumstorage.client.GuiBuilder;
import net.gigabit101.quantumstorage.menu.MenuQuantumStorageUnit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;

public class ScreenQuantumStorageUnit extends AbstractContainerScreen<MenuQuantumStorageUnit>
{
    private final GuiBuilder guiBuilder = new GuiBuilder();
    private final MenuQuantumStorageUnit menuQuantumStorageUnit;

    public ScreenQuantumStorageUnit(MenuQuantumStorageUnit menuQuantumStorageUnit, Inventory inventory, Component component)
    {
        super(menuQuantumStorageUnit, inventory, component);
        this.menuQuantumStorageUnit = menuQuantumStorageUnit;
        this.imageHeight = 220;
        this.imageWidth = 190;
        this.inventoryLabelX = 14;
        this.inventoryLabelY = topPos + 117;
        this.titleLabelX = leftPos + (imageWidth / 2) -  50;
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
        guiBuilder.drawBlackBox(poseStack, leftPos + 12, topPos + 30, 280, 140);

        guiBuilder.drawPlayerSlots(poseStack, leftPos + imageWidth / 2, topPos + 131, true);

        guiBuilder.drawSlot(poseStack, leftPos + imageWidth - 32, topPos + 32);
        guiBuilder.drawSlot(poseStack, leftPos + imageWidth - 32, topPos + 76);

        String itemStack = "Empty";
        if(menuQuantumStorageUnit != null)
        {
            if (!menuQuantumStorageUnit.getInventory().getItem(2).isEmpty()) itemStack = menuQuantumStorageUnit.getInventory().getItem(2).getDisplayName().getString();

            if (menuQuantumStorageUnit.hasMemoryCard)
            {
                drawString(poseStack, font, itemStack, leftPos + 16, topPos + 40, ChatFormatting.WHITE.getColor());
                drawString(poseStack, font, getTotalCount() + "/" + Integer.MAX_VALUE, leftPos + 16, topPos + 60, ChatFormatting.WHITE.getColor());
            }
            else
            {
                drawString(poseStack, font, "Missing memory card", leftPos + 20, topPos + 56, ChatFormatting.RED.getColor());
            }
        }
    }

    public int getTotalCount()
    {
        Container container = menuQuantumStorageUnit.getInventory();
        int count = 0;
        for (int i = 0; i < container.getContainerSize(); i++)
        {
            if(!container.getItem(i).isEmpty()) count += container.getItem(i).getCount();
        }
        return count;
    }
}
