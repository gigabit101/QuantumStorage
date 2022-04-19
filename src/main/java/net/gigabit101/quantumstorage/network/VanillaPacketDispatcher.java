package net.gigabit101.quantumstorage.network;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class VanillaPacketDispatcher
{
    //TODO
    public static void dispatchTEToNearbyPlayers(BlockEntity tile)
    {
//        SUpdateTileEntityPacket packet = tile.getUpdatePacket();
//        BlockPos pos = tile.getPos();
//
//        if(packet != null && tile.getWorld() instanceof ServerWorld)
//        {
//            ((ServerChunkProvider)tile.getWorld().getChunkProvider()).chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
//                    .filter(p -> p.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) < 64 * 64).forEach(e -> e.connection.sendPacket(packet));
//        }
    }
    
    public static void dispatchTEToNearbyPlayers(Level world, BlockPos pos)
    {
        BlockEntity tile = world.getBlockEntity(pos);
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
