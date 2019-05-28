package QuantumStorage.multiblock;

import QuantumStorage.network.PacketGui;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import reborncore.common.network.RegisterPacketEvent;

public class MultiBlockPackets
{
    @SubscribeEvent
    public static void loadPackets(RegisterPacketEvent event) {
        event.registerPacket(PacketGui.class, Side.SERVER);
    }
}
