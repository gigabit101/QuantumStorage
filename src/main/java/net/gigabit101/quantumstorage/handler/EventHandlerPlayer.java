package net.gigabit101.quantumstorage.handler;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.items.backpack.ItemQuantumBag;
import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.minecraft.nbt.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = QuantumStorage.MOD_ID)
public class EventHandlerPlayer
{
    @SubscribeEvent
    public static void itemPickup(EntityItemPickupEvent evt)
    {
        if (!(evt.getEntity() instanceof Player))
        {
            return;
        }
        Player player = (Player)evt.getEntity();
        //Prevent dupes
        if(player.getMainHandItem().getItem() instanceof ItemQuantumBag || player.getOffhandItem().getItem() instanceof ItemQuantumBag)
        {
            return;
        }
        AtomicReference<ItemStack> bag = new AtomicReference<ItemStack>(null);

        player.getInventory().items.forEach((stack) ->
        {
            if (stack.getItem() instanceof ItemQuantumBag)
            {
                ItemQuantumBag itemQuantumBag = (ItemQuantumBag) stack.getItem();
                if(itemQuantumBag.isActive(stack))
                {
                    bag.set(stack);
                }
            }
        });

        if(bag.get() != null)
        {
            ItemStackHandler bagInv = ItemQuantumBag.getHandlerForContainer(bag.get());
            ItemStack insert = ItemHandlerHelper.insertItem(bagInv, evt.getItem().getItem(), false);

            if(!ItemUtils.isItemEqual(insert, evt.getItem().getItem(), false))
            {
                if (!bag.get().hasTag()) bag.get().setTag(new CompoundTag());
                    bag.get().getTag().put("inv", bagInv.serializeNBT());

                    int numPickedUp = evt.getItem().getItem().getCount();

                evt.getItem().setItem(insert);
                evt.setCanceled(true);

                if (!evt.getItem().isSilent())
                {
                    evt.getItem().level.playSound(null, evt.getPlayer().getX(), evt.getPlayer().getY(), evt.getPlayer().getZ(),
                            SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F,
                            ((evt.getItem().level.random.nextFloat() - evt.getItem().level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                }
                //TODO
//                ((ServerPlayer) evt.getPlayer()).connection.send(new ServerboundPickItemPacket(evt.getItem().getEntityId(), evt.getPlayer().getEntityId(), numPickedUp));
            }
        }
    }
}
