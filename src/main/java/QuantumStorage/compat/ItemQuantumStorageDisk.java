package QuantumStorage.compat;

import QuantumStorage.QuantumStorage;
import QuantumStorage.api.QuantumStorageAPI;
import QuantumStorage.config.ConfigQuantumStorage;
import QuantumStorage.items.prefab.ItemBase;
import com.raoulvdberge.refinedstorage.RSItems;;
import com.raoulvdberge.refinedstorage.api.storage.StorageType;
import com.raoulvdberge.refinedstorage.api.storage.disk.IStorageDisk;
import com.raoulvdberge.refinedstorage.api.storage.disk.IStorageDiskProvider;
import com.raoulvdberge.refinedstorage.api.storage.disk.IStorageDiskSyncData;
import com.raoulvdberge.refinedstorage.apiimpl.API;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gigabit101 on 07/03/2017.
 */
public class ItemQuantumStorageDisk extends ItemBase implements IStorageDiskProvider
{
    private static final String NBT_ID = "Id";

    public ItemQuantumStorageDisk()
    {
        setMaxStackSize(1);
        setUnlocalizedName(QuantumStorage.MOD_ID + ".quantumstoragedisk");
        setRegistryName("quantumstoragedisk");
//        QuantumStorageAPI.addAltarRecipe(new ItemStack(RSItems.STORAGE_PART, 1, 3), new ItemStack(CompatHandler.DISK, 1), ConfigQuantumStorage.defaultDiskTime);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.onUpdate(stack, world, entity, slot, selected);

        if (!world.isRemote) {
            if (!isValid(stack)) {
                API.instance().getOneSixMigrationHelper().migrateDisk(world, stack);
            }

            if (!stack.hasTagCompound()) {
                UUID id = UUID.randomUUID();

                API.instance().getStorageDiskManager(world).set(id, API.instance().createDefaultItemDisk(world, Integer.MAX_VALUE));
                API.instance().getStorageDiskManager(world).markForSaving();

                setId(stack, id);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        if (isValid(stack)) {
            UUID id = getId(stack);

            API.instance().getStorageDiskSync().sendRequest(id);

            IStorageDiskSyncData data = API.instance().getStorageDiskSync().getData(id);
            if (data != null) {
                if (data.getCapacity() == -1) {
                    tooltip.add(I18n.format("misc.refinedstorage:storage.stored", API.instance().getQuantityFormatter().format(data.getStored())));
                } else {
                    tooltip.add(I18n.format("misc.refinedstorage:storage.stored_capacity", API.instance().getQuantityFormatter().format(data.getStored()), API.instance().getQuantityFormatter().format(data.getCapacity())));
                }
            }

            if (flag.isAdvanced()) {
                tooltip.add(id.toString());
            }
        }
    }

    @Override
    public int getEntityLifespan(ItemStack stack, World world)
    {
        return Integer.MAX_VALUE;
    }


    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.EPIC;
    }

    @Override
    public UUID getId(ItemStack disk) {
        return disk.getTagCompound().getUniqueId(NBT_ID);
    }

    @Override
    public void setId(ItemStack disk, UUID id) {
        disk.setTagCompound(new NBTTagCompound());
        disk.getTagCompound().setUniqueId(NBT_ID, id);
    }

    @Override
    public boolean isValid(ItemStack disk) {
        return disk.hasTagCompound() && disk.getTagCompound().hasUniqueId(NBT_ID);
    }

    @Override
    public int getCapacity(ItemStack disk) {
        return Integer.MAX_VALUE;
    }

    @Override
    public StorageType getType() {
        return StorageType.ITEM;
    }
}
