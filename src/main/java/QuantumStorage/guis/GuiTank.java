package QuantumStorage.guis;

import QuantumStorage.client.GuiBuilderQuantumStorage;
import QuantumStorage.containers.ContainerQSU;
import QuantumStorage.containers.ContainerTank;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandler;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class GuiTank extends ContainerScreen<ContainerTank>
{
    GuiBuilderQuantumStorage builder = new GuiBuilderQuantumStorage();
    
    public GuiTank(ContainerTank container, PlayerInventory playerinv, ITextComponent title)
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
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    
        int amount = 0;
        String name = "Empty";

        if (getTank().getFluid() != null)
        {
            amount = getTank().getFluidAmount();
            name = getTank().getFluid().getFluid().getName();
        }
    
        builder.drawBigBlueBar(this, 36, 56, amount, getTank().getCapacity(), mouseX - guiLeft, mouseY - guiTop, "", "Fluid Type: " + name, amount + " mb " + name);
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
    
    public IFluidTank getTank()
    {
        return container.getTank();
    }
}
