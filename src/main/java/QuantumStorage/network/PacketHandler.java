package QuantumStorage.network;

import QuantumStorage.QuantumStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class PacketHandler
{
    public static final SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(QuantumStorage.MOD_ID);
    
    public static void register()
    {
        int disc = 0;
        wrapper.registerMessage(SyncBagData.Handler.class, SyncBagData.class, disc++, Side.CLIENT);
    }
    
    public static void sendTo(IMessage msg, EntityPlayerMP player)
    {
        if (!(player instanceof FakePlayer))
        {
            wrapper.sendTo(msg, player);
        }
    }
}
