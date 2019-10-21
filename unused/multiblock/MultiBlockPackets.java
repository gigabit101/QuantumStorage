package QuantumStorage.multiblock;

import net.gigabit101.quantumstorage.network.PacketButton;
import net.gigabit101.quantumstorage.network.PacketGui;
import net.gigabit101.quantumstorage.network.PacketGuiBag;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import reborncore.common.network.RegisterPacketEvent;

public class MultiBlockPackets
{
    @SubscribeEvent
    public static void loadPackets(RegisterPacketEvent event)
    {
        event.registerPacket(PacketGui.class, Side.SERVER);
        event.registerPacket(PacketGuiBag.class, Side.SERVER);
        event.registerPacket(PacketButton.class, Side.SERVER);
    }
}
