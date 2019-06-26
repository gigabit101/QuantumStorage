//package QuantumStorage.handler;
//
//import QuantumStorage.QuantumStorage;
//import QuantumStorage.api.QuantumStorageAPI;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraftforge.event.AttachCapabilitiesEvent;
//import net.minecraftforge.event.entity.player.PlayerEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//@Mod.EventBusSubscriber(modid = QuantumStorage.MOD_ID)
//public class EventHandlerPlayer
//{
//    @SubscribeEvent
//    public static void cloneEvent(PlayerEvent.Clone evt)
//    {
//        CompoundNBT bags = evt.getOriginal().getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).serializeNBT();
//        evt.getEntityPlayer().getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).deserializeNBT(bags);
//    }
//
//    @SubscribeEvent
//    public static void respawnEvent(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent evt)
//    {
//        evt.getPlayer().getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).sync(null, (PlayerEntity) evt.getPlayer());
//    }
//
//    @SubscribeEvent
//    public static void playerChangeDimension(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event)
//    {
//        event.getPlayer().getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).sync(null, (PlayerEntity) event.getPlayer());
//    }
//
//    @SubscribeEvent
//    public static void attachCaps(AttachCapabilitiesEvent<Entity> evt)
//    {
//        if (evt.getObject() instanceof PlayerEntity)
//        {
//            evt.addCapability(QunatumBagImpl.Provider.NAME, new QunatumBagImpl.Provider());
//        }
//    }
//}
