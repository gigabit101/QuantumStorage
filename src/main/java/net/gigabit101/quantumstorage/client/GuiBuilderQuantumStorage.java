package net.gigabit101.quantumstorage.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.gigabit101.quantumstorage.QuantumStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 28/03/2017.
 */
public class GuiBuilderQuantumStorage
{
    public static final ResourceLocation GUI_SHEET = new ResourceLocation(QuantumStorage.MOD_ID.toLowerCase() + ":" + "textures/gui/gui_sheet.png");

    public void drawDefaultBackground(Screen gui, PoseStack matrixStack, int x, int y, int width, int height, int textureXSize, int textureYSize) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_SHEET);

        GuiComponent.blit(matrixStack, x, y, 0, 0, width / 2, height / 2, textureXSize, textureYSize);
        GuiComponent.blit(matrixStack, x + width / 2, y, 150 - width / 2, 0, width / 2, height / 2, textureXSize, textureYSize );
        GuiComponent.blit(matrixStack, x, y + height / 2, 0, 150 - height / 2, width / 2, height / 2, textureXSize, textureYSize);
        GuiComponent.blit(matrixStack, x + width / 2, y + height / 2, 150 - width / 2, 150 - height / 2, width / 2, height / 2, textureXSize, textureYSize);
    }

    public void drawPlayerSlots(Screen gui, PoseStack matrixStack, int posX, int posY, boolean center, int textureXSize, int textureYSize)
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
                GuiComponent.blit(matrixStack, posX + x * 18, posY + y * 18, 150, 0, 18, 18, textureXSize, textureYSize);
            }
        }
        for (int x = 0; x < 9; x++)
        {
            GuiComponent.blit(matrixStack, posX + x * 18, posY + 58, 150, 0, 18, 18, textureXSize, textureYSize);
        }
    }

    public void drawSlot(Screen gui, PoseStack matrixStack, int posX, int posY, int textureXSize, int textureYSize)
    {
        RenderSystem.setShaderTexture(0, GUI_SHEET);
        GuiComponent.blit(matrixStack, posX, posY, 150, 0, 18, 18, textureXSize, textureYSize);
    }

    public void drawBigBlueBar(Screen gui, PoseStack matrixStack, int x, int y, int value, int max, int mouseX, int mouseY, String suffix, String line2, String format, int textureXSize, int textureYSize)
    {
        RenderSystem.setShaderTexture(0, GUI_SHEET);
        if (!suffix.equals(""))
        {
            suffix = " " + suffix;
        }
        GuiComponent.blit(matrixStack, x, y, 0, 218, 114, 18, textureXSize, textureYSize);
        int j = (int) ((double) value / (double) max * 106);
        if (j < 0)
            j = 0;
        GuiComponent.blit(matrixStack, x + 4, y + 4, 0, 236, j, 10, textureXSize, textureYSize);
        GuiComponent.drawCenteredString(matrixStack, Minecraft.getInstance().font, format + suffix, x + 58, y + 5, 0xFFFFFF);
        //TODO
//        if (isInRect(x, y, 114, 18, mouseX, mouseY))
//        {
//            int percentage = percentage(max, value);
//            List<ITextProperties> list = new ArrayList<>();
//            list.add(new StringTextComponent("" + TextFormatting.GOLD + value + "/" + max + suffix));
//            list.add(new StringTextComponent(getPercentageColour(percentage) + "" + percentage + "%" + TextFormatting.GRAY + " Full"));
//            if(!line2.isEmpty()) list.add(new StringTextComponent(line2));
//
//            if (value > max)
//            {
//                list.add(new StringTextComponent(TextFormatting.GRAY + "Yo this is storing more than it should be able to"));
//                list.add(new StringTextComponent(TextFormatting.GRAY + "prolly a bug"));
//                list.add(new StringTextComponent(TextFormatting.GRAY + "pls report and tell how tf you did this"));
//            }
//            GuiUtils.drawHoveringText(matrixStack, list, mouseX, mouseY, gui.width, gui.height, -1, Minecraft.getInstance().fontRenderer);
//
//            GlStateManager.disableLighting();
//            GlStateManager.color4f(1, 1, 1, 1);
//        }
    }

    public void drawLock(Screen gui, PoseStack matrixStack, int posX, int posY, int mouseX, int mouseY, int textureXSize, int textureYSize, String string)
    {
        RenderSystem.setShaderTexture(0, GUI_SHEET);
        GuiComponent.blit(matrixStack, posX, posY, 168, 0, 18, 18, textureXSize, textureYSize);
        if (isInRect(posX, posY, 18, 18, mouseX, mouseY))
        {
            //TODO
//            List<ITextProperties> list = new ArrayList<>();
//            list.add(new StringTextComponent(string));
//            GuiUtils.drawHoveringText(matrixStack, list, mouseX, mouseY, gui.width, gui.height, -1, Minecraft.getInstance().fontRenderer);
        }
    }

    public ChatFormatting getPercentageColour(int percentage)
    {
        if (percentage <= 10)
        {
            return ChatFormatting.RED;
        } else if (percentage >= 75)
        {
            return ChatFormatting.GREEN;
        } else
        {
            return ChatFormatting.YELLOW;
        }
    }

    public int percentage(int MaxValue, int CurrentValue)
    {
        if (CurrentValue == 0)
            return 0;
        return (int) ((CurrentValue * 100.0f) / MaxValue);
    }

    public boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY)
    {
        return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
    }
}
