package QuantumStorage.items;

import QuantumStorage.QuantumStorage;
import QuantumStorage.items.prefab.ItemBase;
import QuantumStorage.utils.EnergyCapabilityProvider;
import QuantumStorage.utils.RfUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;

public class ItemQuantumBattery extends ItemBase
{
    public ItemQuantumBattery()
    {
        setUnlocalizedName(QuantumStorage.MOD_ID + ".quantum_battery");
        setRegistryName("quantum_battery");
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if(playerIn.isSneaking() && !stack.isEmpty() && !isActive(stack))
        {
            stack.setItemDamage(1);
        }
        else
        {
            stack.setItemDamage(0);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    public boolean isActive(ItemStack stack)
    {
        if(stack.getMetadata() == 1)
        {
            return true;
        }
        return false;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        if(entity instanceof EntityPlayer && !isSelected && isActive(stack))
        {
            EntityPlayer player = (EntityPlayer)entity;
            for(int i = 0; i < player.inventory.getSizeInventory(); i++)
            {
                ItemStack slot = player.inventory.getStackInSlot(i);
                if(RfUtils.isPoweredItem(slot))
                {
                    int extractable = RfUtils.dischargeItem(stack, Integer.MAX_VALUE, true);
                    int received = 0;

                    if(slot.hasCapability(CapabilityEnergy.ENERGY, null))
                    {
                        IEnergyStorage cap = slot.getCapability(CapabilityEnergy.ENERGY, null);
                        if(cap != null)
                        {
                            received = cap.receiveEnergy(extractable, false);
                        }
                    }

                    if(received > 0)
                    {
                        RfUtils.dischargeItem(stack, received, false);
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new EnergyCapabilityProvider(Integer.MAX_VALUE);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return RfUtils.getDurabilityForDisplay(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(RfUtils.addPowerTooltip(stack));
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.EPIC;
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return isActive(stack);
    }
}
