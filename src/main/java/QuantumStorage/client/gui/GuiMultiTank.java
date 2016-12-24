package QuantumStorage.client.gui;

import QuantumStorage.client.container.ContainerMultiTank;
import QuantumStorage.tile.TileMultiTank;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import reborncore.client.guibuilder.GuiBuilder;

import java.io.IOException;

/**
 * Created by Gigabit101 on 16/11/2016.
 */
public class GuiMultiTank extends GuiContainer
{
    EntityPlayer player;
    GuiBuilder builder = new GuiBuilder(GuiBuilder.defaultTextureSheet);
    TileMultiTank tank;

    public GuiMultiTank(TileMultiTank tank, EntityPlayer player)
    {
        super(new ContainerMultiTank(tank, player));
        this.player = player;
        this.tank = tank;
        buttonList.clear();
        buttonList.add(new GuiButton(0, 20, 9, ""));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        builder.drawDefaultBackground(this, guiLeft, guiTop, xSize, ySize);
        builder.drawPlayerSlots(this, guiLeft + xSize / 2, guiTop + ySize / 2, true);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        builder.drawTank(this, tank.tank4, 80, 9, zLevel, 14, 50, mouseX - guiLeft, mouseY - guiTop);
        builder.drawTank(this, tank.tank3, 60, 9, zLevel, 14, 50, mouseX - guiLeft, mouseY - guiTop);
        builder.drawTank(this, tank.tank2, 40, 9, zLevel, 14, 50, mouseX - guiLeft, mouseY - guiTop);
        builder.drawTank(this, tank.tank1, 20, 9, zLevel, 14, 50, mouseX - guiLeft, mouseY - guiTop);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
    }

    public void updateButtonText(GuiButton button)
    {
        String currentname = button.displayString;
        if(currentname == "N") button.displayString = "E";
        if(currentname == "E") button.displayString = "S";
        if(currentname == "S") button.displayString = "W";
        if(currentname == "W") button.displayString = "U";
        if(currentname == "U") button.displayString = "D";
        if(currentname == "D") button.displayString = "N";
    }

    public EnumFacing getFacingFromButton(GuiButton button)
    {
        String currentname = button.displayString;
        if(currentname == "N") return EnumFacing.NORTH;
        if(currentname == "E") return EnumFacing.EAST;
        if(currentname == "S") return EnumFacing.SOUTH;
        if(currentname == "W") return EnumFacing.WEST;
        if(currentname == "U") return EnumFacing.UP;
        if(currentname == "D") return EnumFacing.DOWN;
        else return null;
    }
}
