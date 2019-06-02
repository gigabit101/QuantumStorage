package QuantumStorage.network;

import QuantumStorage.GuiHandler;
import QuantumStorage.QuantumStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import reborncore.common.network.ExtendedPacketBuffer;
import reborncore.common.network.INetworkPacket;

import java.io.IOException;

public class PacketGuiBag implements INetworkPacket<PacketGuiBag>
{
    private int colour = -1;
    
    public PacketGuiBag(int colour)
    {
        this.colour = colour;
    }
    
    public PacketGuiBag() {}
    
    @Override
    public void writeData(ExtendedPacketBuffer extendedPacketBuffer) throws IOException
    {
        extendedPacketBuffer.writeInt(colour);
    }
    
    @Override
    public void readData(ExtendedPacketBuffer extendedPacketBuffer) throws IOException
    {
        colour = extendedPacketBuffer.readInt();
    }
    
    @Override
    public void processData(PacketGuiBag packetGui, MessageContext messageContext)
    {
        if(colour != -1)
        {
            EntityPlayer player = messageContext.getServerHandler().player;
            player.openGui(QuantumStorage.INSTANCE, GuiHandler.BAG_ID_PACKET, player.world, 0, 0, colour);
        }
    }
}
