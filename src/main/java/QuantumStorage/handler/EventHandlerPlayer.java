package QuantumStorage.handler;

import QuantumStorage.QuantumStorage;
import QuantumStorage.api.QuantumStorageAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = QuantumStorage.MOD_ID)
public class EventHandlerPlayer
{
    @SubscribeEvent
    public static void cloneEvent(PlayerEvent.Clone evt)
    {
        NBTTagCompound bags = evt.getOriginal().getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).serializeNBT();
        evt.getEntityPlayer().getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).deserializeNBT(bags);
    }

    @SubscribeEvent
    public static void respawnEvent(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent evt)
    {
        evt.player.getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).sync(null, (EntityPlayerMP) evt.player);
    }

    @SubscribeEvent
    public static void playerChangeDimension(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event)
    {
        event.player.getCapability(QuantumStorageAPI.QUANTUM_BAG_PROVIDER_CAPABILITY, null).sync(null, (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<Entity> evt)
    {
        if (evt.getObject() instanceof EntityPlayer)
        {
            evt.addCapability(QunatumBagImpl.Provider.NAME, new QunatumBagImpl.Provider());
        }
    }
}
