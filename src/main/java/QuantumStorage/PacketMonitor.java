package QuantumStorage;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class PacketMonitor
{
    @SubscribeEvent
    public void catchPackets(FMLNetworkEvent.ServerCustomPacketEvent event)
    {
        System.out.println(event.getListenerList());
    }
}
