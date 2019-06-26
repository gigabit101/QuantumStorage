package QuantumStorage.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by Gigabit101 on 01/07/2017.
 */
public class RfUtils
{
    public static int transferPower(@Nullable IEnergyStorage source, @Nullable IEnergyStorage destination, int maxAmount, boolean simulate)
    {
        if (source == null || destination == null)
            return 0;

        int amount = source.extractEnergy(destination.receiveEnergy(maxAmount, true), true);

        return destination.receiveEnergy(source.extractEnergy(amount, simulate), simulate);
    }

    //Item
    public static boolean isPoweredItem(ItemStack stack)
    {
        return stack.getCapability(CapabilityEnergy.ENERGY, null).isPresent();
    }

    public static double getDurabilityForDisplay(ItemStack stack)
    {
        IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY).orElse((IEnergyStorage) new ItemQuantumBattery.EnergyCapabilityProvider(stack, (ItemQuantumBattery) stack.getItem()));
        
        double max = storage.getMaxEnergyStored();
        double diff = max - storage.getEnergyStored();
        return diff / max;
    }

    public static final DecimalFormat QUANTITY_FORMATTER = new DecimalFormat("####0.#", DecimalFormatSymbols.getInstance(Locale.US));

    public static String formatQuantity(int qty)
    {
        if (qty >= 1000000)
        {
            return QUANTITY_FORMATTER.format((float) qty / 1000000F) + "M";
        } else if (qty >= 1000)
        {
            return QUANTITY_FORMATTER.format((float) qty / 1000F) + "K";
        }
        return String.valueOf(qty);
    }

    public static String addPowerTooltip(ItemStack stack)
    {
        IEnergyStorage storage = (IEnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY, null);

        return formatQuantity(storage.getEnergyStored()) + " / " + formatQuantity(storage.getMaxEnergyStored());
    }

    public static boolean isItemFull(ItemStack stack)
    {
        if (isPoweredItem(stack))
        {
            IEnergyStorage storage = (IEnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage.getEnergyStored() != storage.getMaxEnergyStored())
            {
                return false;
            } else return true;
        }
        return false;
    }

    //Untested
    public static void chargeItem(ItemStack stack, IEnergyStorage storage)
    {
        if (isPoweredItem(stack) && !isItemFull(stack))
        {
            IEnergyStorage storageItem = (IEnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY, null);

            int amount2send = storageItem.receiveEnergy(1000, true);
            if (storage.getEnergyStored() >= amount2send)
            {
                storageItem.receiveEnergy(amount2send, false);
                storage.extractEnergy(amount2send, false);
            }
        }
    }

    public static int dischargeItem(ItemStack stack, int amount, boolean sim)
    {
        if (isPoweredItem(stack))
        {
            final IEnergyStorage storage = (IEnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY, null);
            return storage.extractEnergy(amount, sim);
        }
        return 0;
    }
}
