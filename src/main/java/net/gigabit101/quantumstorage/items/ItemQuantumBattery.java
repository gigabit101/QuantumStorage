package net.gigabit101.quantumstorage.items;

import net.gigabit101.quantumstorage.client.CreativeTabQuantumStorage;
import net.gigabit101.quantumstorage.items.prefab.ItemBase;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).tab(CreativeTabQuantumStorage.INSTANCE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn)
    {
        getEnergyStorage(playerIn.getItemInHand(handIn)).receiveEnergy(666666666, false);
        return super.use(worldIn, playerIn, handIn);
    }
    
    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int p_77663_4_, boolean isSelected)
    {
        if (entity instanceof Player && !isSelected)
        {
            Player player = (Player) entity;
            for (int i = 0; i < player.getInventory().getContainerSize(); i++)
            {
                ItemStack slot = player.getInventory().getItem(i);
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
    public boolean isFoil(ItemStack stack)
    {
        return true;
    }


    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TextComponent(RfUtils.addPowerTooltip(getEnergyStorage(stack))));
    }

    //TODO
//    @Override
//    public boolean showDurabilityBar(ItemStack stack)
//    {
//        return true;
//    }
//
//    @Override
//    public double getDurabilityForDisplay(ItemStack stack)
//    {
//        double max = getEnergyStorage(stack).getMaxEnergyStored();
//        double diff = max - getEnergyStorage(stack).getEnergyStored();
//        return diff / max;
//    }
    
    public IEnergyStorage getEnergyStorage(ItemStack stack)
    {
        if (CapabilityEnergy.ENERGY == null)
            return EMPTY_ENERGY_STORAGE;
        
        return stack.getCapability(CapabilityEnergy.ENERGY).orElse(EMPTY_ENERGY_STORAGE);
    }
    
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt)
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
                        stack.setTag(new CompoundTag());
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
