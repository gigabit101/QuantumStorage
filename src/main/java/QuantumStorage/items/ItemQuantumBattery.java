package QuantumStorage.items;

import QuantumStorage.items.prefab.ItemBase;
import QuantumStorage.utils.RfUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

//@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")
public class ItemQuantumBattery extends ItemBase// implements IBauble
{
    public ItemQuantumBattery(Item.Properties properties)
    {
        super(properties);
        setRegistryName("quantum_battery");
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

//    @Override
//    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
//    {
//        if (!world.isRemote && entity instanceof EntityPlayer && !isSelected && isActive(stack))
//        {
//            EntityPlayer player = (EntityPlayer) entity;
//            for (int i = 0; i < player.inventory.getSizeInventory(); i++)
//            {
//                ItemStack slot = player.inventory.getStackInSlot(i);
//                if (RfUtils.isPoweredItem(slot))
//                {
//                    int extractable = RfUtils.dischargeItem(stack, Integer.MAX_VALUE, true);
//                    int received = 0;
//
//                    if (slot.hasCapability(CapabilityEnergy.ENERGY, null))
//                    {
//                        IEnergyStorage cap = slot.getCapability(CapabilityEnergy.ENERGY, null);
//                        if (cap != null)
//                        {
//                            received = cap.receiveEnergy(extractable, false);
//                        }
//                    }
//
//                    if (received > 0)
//                    {
//                        RfUtils.dischargeItem(stack, received, false);
//                    }
//                }
//            }
//        }
//    }

    
    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
//        tooltip.add("" + RfUtils.addPowerTooltip(stack));
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
    
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt)
//    {
//        return new EnergyCapabilityProvider(stack, this);
//    }
    
//    @Override
//    public BaubleType getBaubleType(ItemStack itemStack)
//    {
//        return BaubleType.TRINKET;
//    }
//
//    @Override
//    public void onWornTick(ItemStack itemstack, EntityLivingBase player)
//    {
//        onUpdate(itemstack, player.world, player, 0, false);
//    }
//
//    private static class EnergyCapabilityProvider implements ICapabilityProvider
//    {
//        public final CustomEnergyStorage storage;
//
//        public EnergyCapabilityProvider(final ItemStack stack, ItemQuantumBattery item)
//        {
//            this.storage = new CustomEnergyStorage(Integer.MAX_VALUE, 500000, 500000)
//            {
//                @Override
//                public int getEnergyStored()
//                {
//                    if (stack.hasTagCompound())
//                    {
//                        return stack.getTagCompound().getInteger("Energy");
//                    } else
//                    {
//                        return 0;
//                    }
//                }
//
//                @Override
//                public void setEnergyStored(int energy)
//                {
//                    if (!stack.hasTagCompound())
//                    {
//                        stack.setTagCompound(new NBTTagCompound());
//                    }
//                    stack.getTagCompound().setInteger("Energy", energy);
//                }
//            };
//        }
//
//        @Nonnull
//        @Override
//        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side)
//        {
//            if (capability == CapabilityEnergy.ENERGY)
//            {
//                return ((CapabilityEnergy.ENERGY) this.storage);
//            }
//            return null;
//        }
//    }
}
