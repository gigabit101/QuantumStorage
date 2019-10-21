package net.gigabit101.quantumstorage.guis;

import net.gigabit101.quantumstorage.client.GuiBuilderQuantumStorage;
import net.gigabit101.quantumstorage.containers.ContainerQSU;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.IItemHandler;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class GuiQSU extends ContainerScreen<ContainerQSU>
{
    GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();

    int STORAGE = 1;
    int OUTPUT = 2;

    public GuiQSU(ContainerQSU container, PlayerInventory playerinv, ITextComponent title)
    {
        super(container, playerinv, title);
        this.xSize = 190;
        this.ySize = 220;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        builder.drawDefaultBackground(this, guiLeft, guiTop, xSize, ySize);
        builder.drawPlayerSlots(this, guiLeft + xSize / 2, guiTop + 131, true);

        builder.drawSlot(this, guiLeft + xSize / 2 - 9, guiTop + 30);

        builder.drawSlot(this, guiLeft + xSize / 2 - 9, guiTop + 80);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        
        builder.drawBigBlueBar( this, 36, 56, this.getInv().getStackInSlot(STORAGE).getCount() + this.getInv().getStackInSlot(OUTPUT).getCount(), Integer.MAX_VALUE, mouseX - guiLeft, mouseY - guiTop, "Stored", getInv().getStackInSlot(OUTPUT).getDisplayName().getString(),
                formatQuantity(this.getInv().getStackInSlot(STORAGE).getCount() + this.getInv().getStackInSlot(OUTPUT).getCount()));
    }

    public static final DecimalFormat QUANTITY_FORMATTER = new DecimalFormat("####0.#", DecimalFormatSymbols.getInstance(Locale.US));

    public static String formatQuantity(int qty)
    {
        if (qty >= 1000000)
        {
            return QUANTITY_FORMATTER.format((float) qty / 1000000F) + "M";
        }
        else if (qty >= 1000)
        {
            return QUANTITY_FORMATTER.format((float) qty / 1000F) + "K";
        }
        return String.valueOf(qty);
    }

    public IItemHandler getInv()
    {
        return container.getInv();
    }
}
