package QuantumStorage.compat;

import QuantumStorage.QuantumStorage;
import QuantumStorage.api.QuantumStorageAPI;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.items.prefab.ItemBase;
import com.raoulvdberge.refinedstorage.api.storage.IStorageDisk;
import com.raoulvdberge.refinedstorage.api.storage.IStorageDiskProvider;
import com.raoulvdberge.refinedstorage.api.storage.StorageDiskType;
import com.raoulvdberge.refinedstorage.apiimpl.API;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class ItemQuantumStorageDisk extends ItemBase implements IStorageDiskProvider<ItemStack>
{
    public ItemQuantumStorageDisk()
    {
        setMaxStackSize(1);
        setUnlocalizedName(QuantumStorage.MOD_ID + ".quantumstoragedisk");
        setRegistryName("quantumstoragedisk");
        QuantumStorageAPI.addAltarRecipe(new ItemStack(Items.NETHER_STAR, 1), new ItemStack(this, 1), ConfigQuantumStorage.defaultDiskTime);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        super.onUpdate(stack, world, entity, slot, selected);
        if (!stack.hasTagCompound())
        {
            API.instance().getDefaultStorageDiskBehavior().initDisk(StorageDiskType.ITEMS, stack);
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
        API.instance().getDefaultStorageDiskBehavior().initDisk(StorageDiskType.ITEMS, stack);
    }

    @Override
    public int getEntityLifespan(ItemStack stack, World world)
    {
        return Integer.MAX_VALUE;
    }

    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack)
    {
        return API.instance().getDefaultStorageDiskBehavior().getShareTag(StorageDiskType.ITEMS, stack);
    }

    @Nonnull
    @Override
    public IStorageDisk<ItemStack> create(ItemStack disk)
    {
        return API.instance().getDefaultStorageDiskBehavior().createItemStorage(disk.getTagCompound(), Integer.MAX_VALUE);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.EPIC;
    }
}
