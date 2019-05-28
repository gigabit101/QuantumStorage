package QuantumStorage.network;

import QuantumStorage.QuantumStorage;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncBagData implements IMessage
{
    private NBTTagCompound nbt;

    public SyncBagData()
    {
    }

    public SyncBagData(NBTTagCompound nbt)
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
