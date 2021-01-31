package net.gigabit101.quantumstorage.troll;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class FollowGazGoal extends Goal
{
    private final AnimalEntity animal;
    private PlayerEntity parentAnimal;
    private final double moveSpeed;
    private int delayCounter;

    public FollowGazGoal(AnimalEntity animal, double speed)
    {
        this.animal = animal;
        this.moveSpeed = speed;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute()
    {
        List<PlayerEntity> list = this.animal.world.getEntitiesWithinAABB(PlayerEntity.class, this.animal.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
        PlayerEntity gazEntity = null;
        double d0 = Double.MAX_VALUE;

        for(PlayerEntity playerEntity : list)
        {
            String UUID = playerEntity.getCachedUniqueIdString();//playerEntity.getUniqueID().toString();
            if (UUID.equalsIgnoreCase("e6aef4a5-48b8-475b-af37-c64d813d1790")) {
                double d1 = this.animal.getDistanceSq(playerEntity);
                if (!(d1 > d0)) {
                    d0 = d1;
                    gazEntity = playerEntity;
                }
            }
        }

        if (animal == null) {
            return false;
        } else if (d0 < 9.0D) {
            return false;
        } else {
            this.parentAnimal = gazEntity;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        if(parentAnimal == null) return false;

        if (!this.parentAnimal.isAlive()) {
            return false;
        } else {
            double d0 = this.animal.getDistanceSq(this.parentAnimal);
            return !(d0 < 9.0D) && !(d0 > 256.0D);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.delayCounter = 0;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.parentAnimal = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = 10;
            if(parentAnimal != null) this.animal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
        }
    }
}
