package QuantumStorage.compat;

import QuantumStorage.QuantumStorage;
import QuantumStorage.api.QuantumStorageAPI;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.items.prefab.ItemBase;
import com.raoulvdberge.refinedstorage.RSItems;
import com.raoulvdberge.refinedstorage.api.storage.IStorageDisk;
import com.raoulvdberge.refinedstorage.api.storage.IStorageDiskProvider;
import com.raoulvdberge.refinedstorage.api.storage.StorageDiskType;
import com.raoulvdberge.refinedstorage.apiimpl.API;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class ItemQuantumStorageDiskFluid extends ItemBase implements IStorageDiskProvider<FluidStack>
{
    public ItemQuantumStorageDiskFluid()
    {
        setMaxStackSize(1);
        setUnlocalizedName(QuantumStorage.MOD_ID + ".quantumstoragediskfluid");
        setRegistryName("quantumstoragediskfluid");
        QuantumStorageAPI.addAltarRecipe(new ItemStack(RSItems.FLUID_STORAGE_DISK, 1, 3), new ItemStack(this, 1), ConfigQuantumStorage.defaultDiskTime);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        super.onUpdate(stack, world, entity, slot, selected);
        if (!stack.hasTagCompound())
        {
            API.instance().getDefaultStorageDiskBehavior().initDisk(StorageDiskType.FLUIDS, stack);
        }
    }


    @Override
    public void addInformation(ItemStack disk, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        IStorageDisk storage = create(disk);
        if (storage.isValid(disk))
        {
            if (storage.getCapacity() == -1)
            {
                tooltip.add(I18n.format("misc.refinedstorage:storage.stored", storage.getStored()));
            } else
            {
                tooltip.add(I18n.format("misc.refinedstorage:storage.stored_capacity", storage.getStored(), storage.getCapacity()));
            }
        }
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        super.onCreated(stack, world, player);
        API.instance().getDefaultStorageDiskBehavior().initDisk(StorageDiskType.FLUIDS, stack);
    }

    @Override
    public int getEntityLifespan(ItemStack stack, World world)
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack)
    {
        return API.instance().getDefaultStorageDiskBehavior().getShareTag(StorageDiskType.FLUIDS, stack);
    }

    @Nonnull
    @Override
    public IStorageDisk<FluidStack> create(ItemStack disk)
    {
        return API.instance().getDefaultStorageDiskBehavior().createFluidStorage(disk.getTagCompound(), Integer.MAX_VALUE);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.EPIC;
    }
}
