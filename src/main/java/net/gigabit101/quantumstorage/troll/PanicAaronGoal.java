package net.gigabit101.quantumstorage.troll;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;
import java.util.List;

public class PanicAaronGoal extends Goal
{
    protected final CreatureEntity entity;
    private final double farSpeed;
    private final double nearSpeed;
    protected PlayerEntity avoidTarget;
    protected final float avoidDistance;
    protected Path path;
    protected final PathNavigator navigation;

    public PanicAaronGoal(CreatureEntity entityIn, float distance, double nearSpeedIn, double farSpeedIn) {
        this.entity = entityIn;
        this.avoidDistance = distance;
        this.farSpeed = nearSpeedIn;
        this.nearSpeed = farSpeedIn;
        this.navigation = entityIn.getNavigator();
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean shouldExecute() {
        List<PlayerEntity> list = this.entity.world.getEntitiesWithinAABB(PlayerEntity.class, this.entity.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
        double d0 = Double.MAX_VALUE;

        for(PlayerEntity playerEntity : list)
        {
            String UUID = playerEntity.getCachedUniqueIdString();
            if (UUID.equalsIgnoreCase("6d074736-b1e9-4378-a99b-bd8777821c9c")) {
                double d1 = this.entity.getDistanceSq(playerEntity);
                if (!(d1 > d0)) {
                    d0 = d1;
                    this.avoidTarget = playerEntity;
                    break;
                }
            }
        }
        if(avoidTarget != null) {
            Vector3d vector3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.entity, 16, 7, this.avoidTarget.getPositionVec());
            if (vector3d == null) {
                return false;
            } else if (this.avoidTarget.getDistanceSq(vector3d.x, vector3d.y, vector3d.z) < this.avoidTarget.getDistanceSq(this.entity)) {
                return false;
            } else {
                this.path = this.navigation.getPathToPos(vector3d.x, vector3d.y, vector3d.z, 0);
                return this.path != null;
            }
        }
        return false;
    }

    public boolean shouldContinueExecuting() {
        return !this.navigation.noPath();
    }

    public void startExecuting() {
        this.navigation.setPath(this.path, this.farSpeed);
    }

    public void resetTask() {
        this.avoidTarget = null;
    }

    public void tick()
    {
        if(avoidTarget != null) {
            if (this.entity.getDistanceSq(this.avoidTarget) < 49.0D) {
                this.entity.getNavigator().setSpeed(this.nearSpeed);
            } else {
                this.entity.getNavigator().setSpeed(this.farSpeed);
            }
        }
    }
}
