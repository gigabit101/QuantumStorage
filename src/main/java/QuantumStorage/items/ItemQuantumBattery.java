package QuantumStorage.items;

import QuantumStorage.client.CreativeTabQuantumStorage;
import QuantumStorage.items.prefab.ItemBase;
import net.minecraft.client.resources.I18n;
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
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemQuantumBattery extends ItemBase
{
    public ItemQuantumBattery()
    {
        super(new Item.Properties().rarity(Rarity.EPIC).maxStackSize(1).group(CreativeTabQuantumStorage.INSTANCE));
        setRegistryName("quantum_battery");
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean isSelected)
    {
        //TODO
//        if (!world.isRemote && entity instanceof PlayerEntity && !isSelected)
//        {
//            PlayerEntity player = (PlayerEntity) entity;
//            for (int i = 0; i < player.inventory.getSizeInventory(); i++)
//            {
//                ItemStack slot = player.inventory.getStackInSlot(i);
//                if (RfUtils.isPoweredItem(slot))
//                {
//                    int extractable = RfUtils.dischargeItem(stack, Integer.MAX_VALUE, true);
//                    int received = 0;
//
//                    if (RfUtils.isPoweredItem(stack))
//                    {
//                        IEnergyStorage cap = (IEnergyStorage) slot.getCapability(CapabilityEnergy.ENERGY, null);
//                        if (cap != null)
//                        {
//                            received = cap.receiveEnergy(extractable, false);
//                        }
//                    }
//                    if (received > 0)
//                    {
//                        RfUtils.dischargeItem(stack, received, false);
//                    }
//                }
//            }
//        }
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
//        tooltip.add(I18n.format(""))
//        tooltip.add("" + RfUtils.addPowerTooltip(stack));
    }
    
    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }
    
//    @Override
//    public double getDurabilityForDisplay(ItemStack stack)
//    {
//        IEnergyStorage storage = (IEnergyStorage) stack.getCapability(CapabilityEnergy.ENERGY);
//
//        double max = storage.getMaxEnergyStored();
//        double diff = max - storage.getEnergyStored();
//        return diff / max;
//    }
    
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
            this.storage = new CustomEnergyStorage(Integer.MAX_VALUE, 500000, 500000)
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
