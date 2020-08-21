package net.gigabit101.quantumstorage.guis;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.gigabit101.quantumstorage.client.GuiBuilderQuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerChestBase;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;

public class GuiChestBase<T extends ContainerChestBase> extends ContainerScreen<T> {
    GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();

    int playerInventoryPosY = Integer.MAX_VALUE;
    int playerInventoryPosX = Integer.MAX_VALUE;

    public GuiChestBase(T container, PlayerInventory playerinv, ITextComponent title, int width, int height) {
        super(container, playerinv, title);

        this.xSize = width;
        this.ySize = height;

        for(Slot slot : container.inventorySlots){
            if(!slot.inventory.equals(playerinv))
                continue;

            playerInventoryPosY = Math.min(playerInventoryPosY, slot.yPos);
            playerInventoryPosX = Math.min(playerInventoryPosX, slot.xPos);
        }
        playerInventoryPosY-=1;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_)
    {
        builder.drawDefaultBackground(this, matrixStack, guiLeft, guiTop, xSize, ySize, 256, 256);

        drawPlayerInventorySlots(matrixStack);
        drawInventorySlots(matrixStack);
    }

    private void drawInventorySlots(MatrixStack matrixStack){
        for(Slot slot : container.inventorySlots) {
            if(slot.inventory instanceof PlayerInventory)
                continue;

            builder.drawSlot(this, matrixStack, getGuiLeft() + slot.xPos - 1, getGuiTop() + slot.yPos - 1, 256, 256);
        }
    }

    private void drawPlayerInventorySlots(MatrixStack matrixStack){
        for(Slot slot : container.inventorySlots) {
            if(!(slot.inventory instanceof PlayerInventory))
                continue;

            builder.drawSlot(this, matrixStack, getGuiLeft() + slot.xPos - 1, getGuiTop() + slot.yPos - 1, 256, 256);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY)
    {
        font.drawString(matrixStack, this.title.getString(), 13, 5, Color.DARK_GRAY.getRGB());
        font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), playerInventoryPosX, playerInventoryPosY - 10, Color.DARK_GRAY.getRGB());
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.func_230459_a_(matrixStack, mouseX, mouseY);
    }
}
