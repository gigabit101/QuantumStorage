package net.gigabit101.quantumstorage.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.gigabit101.quantumstorage.QuantumStorage;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;

public class GuiBuilder
{
    public static final ResourceLocation GUI_SHEET = new ResourceLocation(QuantumStorage.MOD_ID.toLowerCase() + ":" + "textures/gui/gui_sheet.png");

    public void drawDefaultBackground(PoseStack matrixStack, int x, int y, int width, int height)
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_SHEET);

        AbstractContainerScreen.blit(matrixStack, x, y, 0, 0, width / 2, height / 2, 256, 256);
        AbstractContainerScreen.blit(matrixStack, x + width / 2, y, 150 - width / 2, 0, width / 2, height / 2, 256, 256 );
        AbstractContainerScreen.blit(matrixStack, x, y + height / 2, 0, 150 - height / 2, width / 2, height / 2, 256, 256);
        AbstractContainerScreen.blit(matrixStack, x + width / 2, y + height / 2, 150 - width / 2, 150 - height / 2, width / 2, height / 2, 256, 256);
    }

    public void drawBlackBox(PoseStack matrixStack, int x, int y, int width, int height)
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_SHEET);

        int textureX = 0;
        int textureY = 150;

        AbstractContainerScreen.blit(matrixStack, x, y, textureX, textureY, width / 2, height / 2, 256, 256);
//        AbstractContainerScreen.blit(matrixStack, x + width / 2, y, textureX + 150 - width / 2, textureY, width / 2, height / 2, 256, 256 );
//        AbstractContainerScreen.blit(matrixStack, x, y + height / 2, textureX, textureY + 150 - height / 2, width / 2, height / 2, 256, 256);
//        AbstractContainerScreen.blit(matrixStack, x + width / 2, y + height / 2, textureX + 150 - width / 2, textureY + 150 - height / 2, width / 2, height / 2, 256, 256);
    }

    public void drawPlayerSlots(PoseStack matrixStack, int posX, int posY, boolean center)
    {
        RenderSystem.setShaderTexture(0, GUI_SHEET);
        if (center)
        {
            posX -= 81;
        }
        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 9; x++)
            {
                AbstractContainerScreen.blit(matrixStack, posX + x * 18, posY + y * 18, 150, 0, 18, 18, 256, 256);
            }
        }
        for (int x = 0; x < 9; x++)
        {
            AbstractContainerScreen.blit(matrixStack, posX + x * 18, posY + 58, 150, 0, 18, 18, 256, 256);
        }
    }

    public void drawSlot(PoseStack matrixStack, int posX, int posY)
    {
        RenderSystem.setShaderTexture(0, GUI_SHEET);
        AbstractContainerScreen.blit(matrixStack, posX, posY, 150, 0, 18, 18, 256, 256);
    }
}
