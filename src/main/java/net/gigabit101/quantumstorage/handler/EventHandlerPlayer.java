package net.gigabit101.quantumstorage.handler;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.init.QSItems;
import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.gigabit101.quantumstorage.util.inventory.IterableInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = QuantumStorage.MOD_ID)
public class EventHandlerPlayer
{
    @SubscribeEvent
    public static void cloneEvent(PlayerEvent.Clone evt)
    {
//        CompoundNBT bags = evt.getOriginal().getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY).serializeNBT();
//        evt.getEntityPlayer().getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY).deserializeNBT(bags);
    }

    @SubscribeEvent
    public static void respawnEvent(PlayerEvent.PlayerRespawnEvent evt)
    {
//        evt.getPlayer().getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).sync(null, (PlayerEntity) evt.getPlayer());
    }

    @SubscribeEvent
    public static void playerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
//        event.getPlayer().getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).sync(null, (PlayerEntity) event.getPlayer());
    }

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<Entity> evt)
    {
        if (evt.getObject() instanceof PlayerEntity)
        {
//            evt.addCapability(QunatumBagImpl.Provider.NAME, new QunatumBagImpl.Provider());
        }
    }

    @SubscribeEvent
    public static void itemPickup(PlayerEvent.ItemPickupEvent evt)
    {
        if (!(evt.getEntity() instanceof PlayerEntity))
        {
            return;
        }
        PlayerEntity player = (PlayerEntity)evt.getEntity();

        IterableInventory.from(player.inventory).forEach((stack) -> {
            if (stack.getItem() != QSItems.NOMMER_ITEM.get())
            {
                return;
            }

            CompoundNBT tag = stack.getTag();

            if (tag == null)
            {
                return;
            }

            INBT filterTag = tag.get("filter");

            if (filterTag == null)
            {
                return;
            }

            if (filterTag.getType() != CompoundNBT.TYPE)
            {
                return;
            }

            NonNullList<ItemStack> filterItems = NonNullList.create();

            ItemStackHelper.loadAllItems((CompoundNBT)filterTag, filterItems);


            filterItems.forEach((filterItem) -> {
                //ItemUtils.isItemEqual()
            });

        });

    }

}
