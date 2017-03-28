package QuantumStorage.client;

import QuantumStorage.QuantumStorage;
import QuantumStorage.rewrite.AdvancedGui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;
import reborncore.client.guibuilder.GuiBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gigabit101 on 28/03/2017.
 */
public class GuiBuilderQuantumStorage extends GuiBuilder
{
    public static final ResourceLocation GUI_SHEET = new ResourceLocation(QuantumStorage.MOD_ID.toLowerCase() + ":" + "textures/gui/gui_sheet.png");

    public GuiBuilderQuantumStorage()
    {
        super(GUI_SHEET);
    }

    public void drawBigBlueBar(AdvancedGui gui, int x, int y, int value, int max, int mouseX, int mouseY, String suffix, String line2, String format)
    {
        gui.mc.getTextureManager().bindTexture(GUI_SHEET);
        if (!suffix.equals(""))
        {
            suffix = " " + suffix;
        }
        gui.drawTexturedModalRect(x, y, 0, 218, 114, 18);
        int j = (int) ((double) value / (double) max * 106);
        if (j < 0)
            j = 0;
        gui.drawTexturedModalRect(x + 4, y + 4, 0, 236, j, 10);
        gui.drawCentredString(format + suffix, y + 5, 0xFFFFFF);
        if (isInRect(x, y, 114, 18, mouseX, mouseY))
        {
            int percentage = percentage(max, value);
            List<String> list = new ArrayList<>();
            list.add("" + TextFormatting.GOLD + value + "/" + max + suffix);
            list.add(getPercentageColour(percentage) + "" + percentage + "%" + TextFormatting.GRAY + " Full");
            list.add(line2);

            if (value > max)
            {
                list.add(TextFormatting.GRAY + "Yo this is storing more than it should be able to");
                list.add(TextFormatting.GRAY + "prolly a bug");
                list.add(TextFormatting.GRAY + "pls report and tell how tf you did this");
            }
            net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(list, mouseX, mouseY, gui.width, gui.height, -1, gui.mc.fontRendererObj);
            GlStateManager.disableLighting();
            GlStateManager.color(1, 1, 1, 1);
        }
    }

    public TextFormatting getPercentageColour(int percentage)
    {
        if (percentage <= 10) {
            return TextFormatting.RED;
        } else if (percentage >= 75) {
            return TextFormatting.GREEN;
        } else {
            return TextFormatting.YELLOW;
        }
    }

    public int percentage(int MaxValue, int CurrentValue)
    {
        if (CurrentValue == 0)
            return 0;
        return (int) ((CurrentValue * 100.0f) / MaxValue);
    }

    public void drawProgressBar(GuiContainer gui, int progress, int maxProgress, int x, int y, int mouseX, int mouseY)
    {
        gui.mc.getTextureManager().bindTexture(GUI_SHEET);
        gui.drawTexturedModalRect(x, y, 150, 20, 17, 16);

        int j = (int) ((double) progress / (double) maxProgress * 24);
        if (j < 0)
            j = 0;
        gui.drawTexturedModalRect(x, y, 166, 20, j, 16);

        if (isInRect(x, y, 26, 5, mouseX, mouseY)) {
            int percentage = percentage(maxProgress, progress);
            List<String> list = new ArrayList<>();
            list.add(getPercentageColour(percentage) + "" + percentage + "%");
            GuiUtils.drawHoveringText(list, mouseX, mouseY, gui.width, gui.height, -1, gui.mc.fontRendererObj);
            GlStateManager.disableLighting();
            GlStateManager.color(1, 1, 1, 1);
        }
    }
}
