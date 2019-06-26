//package QuantumStorage.network;
//
//import QuantumStorage.multiblock.TileMultiStorage;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
//import reborncore.common.network.ExtendedPacketBuffer;
//import reborncore.common.network.INetworkPacket;
//
//import java.io.IOException;
//
//public class PacketButton implements INetworkPacket<PacketButton>
//{
//    private int ID;
//    private BlockPos blockPos;
//
//
//    public PacketButton(int ID, BlockPos blockPos)
//    {
//        this.ID = ID;
//        this.blockPos = blockPos;
//    }
//
//    public PacketButton() {}
//
//    @Override
//    public void writeData(ExtendedPacketBuffer extendedPacketBuffer) throws IOException
//    {
//        extendedPacketBuffer.writeInt(ID);
//        extendedPacketBuffer.writeBlockPos(blockPos);
//    }
//
//    @Override
//    public void readData(ExtendedPacketBuffer extendedPacketBuffer) throws IOException
//    {
//        ID = extendedPacketBuffer.readInt();
//        blockPos = extendedPacketBuffer.readBlockPos();
//    }
//
//    @Override
//    public void processData(PacketButton packetGui, MessageContext messageContext)
//    {
//        World world = messageContext.getServerHandler().player.world;
//
//        if(world.getTileEntity(blockPos) != null && world.getTileEntity(blockPos) instanceof TileMultiStorage)
//        {
//            TileMultiStorage te = (TileMultiStorage) world.getTileEntity(blockPos);
//            te.actionPerformed(ID);
//        }
//    }
//}
