package QuantumStorage.handler;

import QuantumStorage.QuantumStorage;
import QuantumStorage.api.IQuantumBagProvider;
import QuantumStorage.api.QuantumStorageAPI;
import QuantumStorage.network.PacketHandler;
import QuantumStorage.network.SyncBagData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

public final class QunatumBagImpl
{
    public static void init()
    {
        CapabilityManager.INSTANCE.register(IQuantumBagProvider.class, new Capability.IStorage<IQuantumBagProvider>()
        {
            @Override
            public NBTTagCompound writeNBT(Capability<IQuantumBagProvider> capability, IQuantumBagProvider instance, EnumFacing side)
            {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IQuantumBagProvider> capability, IQuantumBagProvider instance, EnumFacing side, NBTBase nbt) {
                if (nbt instanceof NBTTagCompound)
                    instance.deserializeNBT(((NBTTagCompound) nbt));
            }
        }, DefaultImpl.class);
    }

    private static class DefaultImpl implements IQuantumBagProvider
    {
        private final Map<EnumDyeColor, IItemHandler> inventories = new EnumMap<>(EnumDyeColor.class);

        @Nonnull
        @Override
        public IItemHandler getBag(@Nonnull EnumDyeColor color)
        {
            if (!inventories.containsKey(color))
            {
                inventories.put(color, new ItemStackHandler(104));
            }

            return inventories.get(color);
        }

        @Override
        public void sync(@Nullable EnumDyeColor color, @Nonnull EntityPlayerMP player)
        {
            PacketHandler.sendTo(new SyncBagData(writeNBT(color)), player);
        }

        private NBTTagCompound writeNBT(EnumDyeColor color)
        {
            NBTTagCompound ret = new NBTTagCompound();
            EnumDyeColor[] colors = color == null ? EnumDyeColor.values() : new EnumDyeColor[] { color };
            for (EnumDyeColor c : colors)
            {
                if (inventories.containsKey(c))
                {
                    NBTBase inv = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventories.get(c), null);
                    ret.setTag(c.getName(), inv);
                }
            }
            return ret;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return writeNBT(null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            for (EnumDyeColor e : EnumDyeColor.values())
            {
                if (nbt.hasKey(e.getName()))
                {
                    IItemHandler inv = new ItemStackHandler(104);
                    CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inv, null, nbt.getTag(e.getName()));
                    inventories.put(e, inv);
                }
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {

        public static final ResourceLocation NAME = new ResourceLocation(QuantumStorage.MOD_NAME, "quantum_bags");

        private final IQuantumBagProvider cap = new DefaultImpl();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing)
        {
            return capability == QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY;
        }

        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing)
        {
            if (capability == QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY)
            {
                return QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY.cast(cap);
            }

            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return cap.serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            cap.deserializeNBT(nbt);
        }
    }

    private QunatumBagImpl() {}
}
