package net.gigabit101.quantumstorage.handler;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.api.IQuantumBagProvider;
import net.gigabit101.quantumstorage.api.QuantumStorageAPI;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
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
            @Nullable
            @Override
            public INBT writeNBT(Capability<IQuantumBagProvider> capability, IQuantumBagProvider instance, Direction side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IQuantumBagProvider> capability, IQuantumBagProvider instance, Direction side, INBT nbt) {
                if (nbt instanceof CompoundNBT)
                    instance.deserializeNBT(((CompoundNBT) nbt));
            }
        }, DefaultImpl::new);
    }

    private static class DefaultImpl implements IQuantumBagProvider
    {
        private final Map<DyeColor, IItemHandler> inventories = new EnumMap<>(DyeColor.class);

        @Nonnull
        @Override
        public IItemHandler getBag(@Nonnull DyeColor color)
        {
            if (!inventories.containsKey(color))
            {
                inventories.put(color, new ItemStackHandler(104));
            }

            return inventories.get(color);
        }

        @Override
        public void sync(@Nullable DyeColor color, @Nonnull PlayerEntity player)
        {
//            PacketHandler.sendTo(new SyncBagData(writeNBT(color)), player);
        }

        private CompoundNBT writeNBT(DyeColor color)
        {
            CompoundNBT ret = new CompoundNBT();
            DyeColor[] colors = color == null ? DyeColor.values() : new DyeColor[]{color};
            for (DyeColor c : colors)
            {
                if (inventories.containsKey(c))
                {
                    INBT inv = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventories.get(c), null);
                    ret.put(c.getName(), inv);
                }
            }
            return ret;
        }

        @Override
        public CompoundNBT serializeNBT()
        {
            return writeNBT(null);
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt)
        {
            for (DyeColor e : DyeColor.values())
            {
                if (nbt.hasUniqueId(e.getName()))
                {
                    IItemHandler inv = new ItemStackHandler(104);
                    CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inv, null, nbt.getCompound(e.getName()));
                    inventories.put(e, inv);
                }
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT>
    {
        public static final ResourceLocation NAME = new ResourceLocation(QuantumStorage.MOD_ID, "quantum_bags");

        private final IQuantumBagProvider cap = new DefaultImpl();

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side)
        {
            if (capability == QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY)
            {
                return LazyOptional.of(() -> cap).cast();
            }
            return LazyOptional.empty();
        }

        @Override
        public CompoundNBT serializeNBT()
        {
            return cap.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt)
        {
            cap.deserializeNBT(nbt);
        }
    }

    private QunatumBagImpl()
    {
    }
}
