package net.gigabit101.quantumstorage.guis;

import com.mojang.blaze3d.vertex.PoseStack;
import net.gigabit101.quantumstorage.client.GuiBuilderQuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerChestBase;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.awt.*;

public class GuiChestBase<T extends ContainerChestBase> extends AbstractContainerScreen<T>
{
    GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();
    int playerInventoryPosY = Integer.MAX_VALUE;
    int playerInventoryPosX = Integer.MAX_VALUE;

    T container;

    public GuiChestBase(T container, Inventory playerinv, Component title, int width, int height) {
        super(container, playerinv, title);
        this.container = container;

        this.imageWidth = width;
        this.imageHeight = height;

        for(Slot slot : container.slots){
            if(!slot.container.equals(playerinv))
                continue;

            playerInventoryPosY = Math.min(playerInventoryPosY, slot.x);
            playerInventoryPosX = Math.min(playerInventoryPosX, slot.y);
        }
        playerInventoryPosY-=1;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float p_97788_, int p_97789_, int p_97790_)
    {
        builder.drawDefaultBackground(this, poseStack, leftPos, topPos, getXSize(), getYSize(), 256, 256);

        drawPlayerInventorySlots(poseStack);
        drawInventorySlots(poseStack);
    }

    private void drawInventorySlots(PoseStack matrixStack){
        for(Slot slot : container.slots) {
            if(slot.container instanceof Inventory)
                continue;

            builder.drawSlot(this, matrixStack, getGuiLeft() + slot.x - 1, getGuiTop() + slot.y - 1, 256, 256);
        }
    }

    private void drawPlayerInventorySlots(PoseStack matrixStack){
        for(Slot slot : container.slots) {
            if(!(slot.container instanceof Inventory))
                continue;

            builder.drawSlot(this, matrixStack, getGuiLeft() + slot.x - 1, getGuiTop() + slot.y - 1, 256, 256);
        }
    }

    @Override
    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_)
    {
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }
}
