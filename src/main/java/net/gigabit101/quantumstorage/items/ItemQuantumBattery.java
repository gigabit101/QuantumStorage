package net.gigabit101.quantumstorage.items;

import net.gigabit101.quantumstorage.client.CreativeTabQuantumStorage;
import net.gigabit101.quantumstorage.items.prefab.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ItemQuantumBattery extends ItemBase
{
    private static final IEnergyStorage EMPTY_ENERGY_STORAGE = new EnergyStorage(Integer.MAX_VALUE);
    
    public ItemQuantumBattery()
    {
        super(new Item.Properties().rarity(Rarity.EPIC).maxStackSize(1).group(CreativeTabQuantumStorage.INSTANCE));
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        getEnergyStorage(playerIn.getHeldItem(handIn)).receiveEnergy(666666666, false);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean isSelected)
    {
        if (entity instanceof PlayerEntity && !isSelected)
        {
            PlayerEntity player = (PlayerEntity) entity;
            for (int i = 0; i < player.inventory.getSizeInventory(); i++)
            {
                ItemStack slot = player.inventory.getStackInSlot(i);
                if (getEnergyStorage(stack).getEnergyStored() > 0)
                {
                    AtomicBoolean charging = new AtomicBoolean(false);
                    slot.getCapability(CapabilityEnergy.ENERGY).ifPresent(energyStorage ->
                    {
                        if (energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored())
                        {
                            charging.set(true);
                            int insert = Math.min(1000, getEnergyStorage(stack).getEnergyStored());
                            insert = energyStorage.receiveEnergy(insert, false);
                            getEnergyStorage(stack).extractEnergy(insert, false);
                        }
                    });
                }
            }
        }
    }

    
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(RfUtils.addPowerTooltip(getEnergyStorage(stack))));
    }
    
    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }
    
    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        double max = getEnergyStorage(stack).getMaxEnergyStored();
        double diff = max - getEnergyStorage(stack).getEnergyStored();
        return diff / max;
    }
    
    public IEnergyStorage getEnergyStorage(ItemStack stack)
    {
        if (CapabilityEnergy.ENERGY == null)
            return EMPTY_ENERGY_STORAGE;
        
        return stack.getCapability(CapabilityEnergy.ENERGY).orElse(EMPTY_ENERGY_STORAGE);
    }
    
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt)
    {
        return new EnergyCapabilityProvider(stack, this);
    }
    
    public static class EnergyCapabilityProvider implements ICapabilityProvider
    {
        public final CustomEnergyStorage storage;

        public EnergyCapabilityProvider(final ItemStack stack, ItemQuantumBattery item)
        {
            this.storage = new CustomEnergyStorage(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE)
            {
                @Override
                public int getEnergyStored()
                {
                    if (stack.hasTag())
                    {
                        return stack.getTag().getInt("Energy");
                    } else
                    {
                        return 0;
                    }
                }

                @Override
                public void setEnergyStored(int energy)
                {
                    if (!stack.hasTag())
                    {
                        stack.setTag(new CompoundNBT());
                    }
                    stack.getTag().putInt("Energy", energy);
                }
            };
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side)
        {
            if (capability == CapabilityEnergy.ENERGY)
            {
                return LazyOptional.of(() -> storage).cast();
            }
            return LazyOptional.empty();
        }
    }
}
