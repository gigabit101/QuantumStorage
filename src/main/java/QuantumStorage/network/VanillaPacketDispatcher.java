package QuantumStorage.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;

public class VanillaPacketDispatcher
{
    public static void dispatchTEToNearbyPlayers(TileEntity tile) {
        if (tile.getWorld() instanceof ServerWorld) {
            ServerWorld ws = ((ServerWorld) tile.getWorld());
            SUpdateTileEntityPacket packet = tile.getUpdatePacket();
            
            if (packet == null)
                return;
            
            for (PlayerEntity player : ws.getPlayers()) {
                ServerPlayerEntity playerMP = (ServerPlayerEntity) player;
                //TODO
//                if (playerMP.getDistanceSq(tile.getPos()) < 64 * 64 && ws.getPlayerChunkMap().isPlayerWatchingChunk(playerMP, tile.getPos().getX() >> 4, tile.getPos().getZ() >> 4)) {
                    playerMP.connection.sendPacket(packet);
//                }
            }
        }
    }
    
    public static void dispatchTEToNearbyPlayers(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null)
            dispatchTEToNearbyPlayers(tile);
    }
    
    public static float pointDistancePlane(double x1, double y1, double x2, double y2) {
        return (float) Math.hypot(x1 - x2, y1 - y2);
    }
}
