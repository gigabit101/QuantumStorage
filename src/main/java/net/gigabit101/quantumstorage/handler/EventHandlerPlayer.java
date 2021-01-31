package net.gigabit101.quantumstorage.handler;

import net.gigabit101.quantumstorage.QuantumStorage;
import net.gigabit101.quantumstorage.items.backpack.ItemQuantumBag;
import net.gigabit101.quantumstorage.troll.FollowGazGoal;
import net.gigabit101.quantumstorage.troll.PanicAaronGoal;
import net.gigabit101.quantumstorage.util.inventory.ItemUtils;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.SCollectItemPacket;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
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
    public static void onEntityLoad(LivingSpawnEvent entityJoinWorldEvent)
    {
        if(entityJoinWorldEvent != null)
        {
            if(entityJoinWorldEvent.getEntity() instanceof SheepEntity) {
                SheepEntity sheepEntity = (SheepEntity) entityJoinWorldEvent.getEntity();
                sheepEntity.goalSelector.addGoal(1, new FollowGazGoal((AnimalEntity) entityJoinWorldEvent.getEntity(), 1.1D));
            }
            if(entityJoinWorldEvent.getEntity() instanceof AgeableEntity) {
                AgeableEntity animalEntity = (AgeableEntity) entityJoinWorldEvent.getEntity();
                if(animalEntity.isChild()) animalEntity.goalSelector.addGoal(0, new PanicAaronGoal((CreatureEntity) entityJoinWorldEvent.getEntity(), 6.0F, 2.2D, 1.8D));
            }
        }
    }

    @SubscribeEvent
    public static void itemPickup(EntityItemPickupEvent evt)
    {
        if (!(evt.getEntity() instanceof PlayerEntity))
        {
            return;
        }
        PlayerEntity player = (PlayerEntity)evt.getEntity();
        //Prevent dupes
        if(player.getHeldItemMainhand().getItem() instanceof ItemQuantumBag || player.getHeldItemOffhand().getItem() instanceof ItemQuantumBag)
        {
            return;
        }
        AtomicReference<ItemStack> bag = new AtomicReference<ItemStack>(null);

        player.inventory.mainInventory.forEach((stack) ->
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
                if (!bag.get().hasTag()) bag.get().setTag(new CompoundNBT());
                    bag.get().getTag().put("inv", bagInv.serializeNBT());

                    int numPickedUp = evt.getItem().getItem().getCount();

                evt.getItem().setItem(insert);
                evt.setCanceled(true);

                if (!evt.getItem().isSilent())
                {
                    evt.getItem().world.playSound(null, evt.getPlayer().getPosX(), evt.getPlayer().getPosY(), evt.getPlayer().getPosZ(),
                            SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F,
                            ((evt.getItem().world.rand.nextFloat() - evt.getItem().world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                }
                ((ServerPlayerEntity) evt.getPlayer()).connection.sendPacket(new SCollectItemPacket(evt.getItem().getEntityId(), evt.getPlayer().getEntityId(), numPickedUp));
            }
        }
    }
}
