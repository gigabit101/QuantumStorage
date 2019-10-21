package net.gigabit101.quantumstorage.network;

import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;

public class VanillaPacketDispatcher
{
    public static void dispatchTEToNearbyPlayers(TileEntity tile)
    {
        SUpdateTileEntityPacket packet = tile.getUpdatePacket();
        BlockPos pos = tile.getPos();
        
        if(packet != null && tile.getWorld() instanceof ServerWorld)
        {
            ((ServerChunkProvider)tile.getWorld().getChunkProvider()).chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
                    .filter(p -> p.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64).forEach(e -> e.connection.sendPacket(packet));
        }
    }
    
    public static void dispatchTEToNearbyPlayers(World world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null)
        {
            dispatchTEToNearbyPlayers(tile);
        }
    }
    
    public static float pointDistancePlane(double x1, double y1, double x2, double y2)
    {
        return (float) Math.hypot(x1 - x2, y1 - y2);
    }
}
