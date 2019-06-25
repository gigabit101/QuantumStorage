package QuantumStorage.network;

import QuantumStorage.QuantumStorage;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class SyncBagData implements IMessage
{
    private CompoundNBT nbt;
    
    public SyncBagData() {}
    
    public SyncBagData(CompoundNBT nbt)
    {
        this.nbt = nbt;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        nbt = ByteBufUtils.readTag(buf);
    }
    
    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, nbt);
    }
    
    public static class Handler implements IMessageHandler<SyncBagData, IMessage>
    {
        @Override
        public IMessage onMessage(final SyncBagData message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() -> QuantumStorage.proxy.getClientBagProps().deserializeNBT(message.nbt));
            
            return null;
        }
    }
}
