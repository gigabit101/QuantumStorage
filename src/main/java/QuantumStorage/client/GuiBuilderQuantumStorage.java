package QuantumStorage.client;

import QuantumStorage.QuantumStorage;
import net.minecraft.util.ResourceLocation;
import reborncore.client.guibuilder.GuiBuilder;

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
}
