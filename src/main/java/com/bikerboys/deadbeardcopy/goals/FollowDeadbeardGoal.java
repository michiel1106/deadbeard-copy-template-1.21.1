package com.bikerboys.deadbeardcopy.goals;

import com.bikerboys.deadbeardcopy.entities.custom.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.*;

import java.util.*;

public class FollowDeadbeardGoal extends Goal {

    HostileEntity entity;
    double speed;
    private int delay;
    DeadBeardEntity target;


    public FollowDeadbeardGoal(HostileEntity entity, double speed) {
        this.entity = entity;
        this.speed = speed;
    }


    @Override
    public boolean canStart() {
        if (this.entity.getTarget() != null) return false;

        List<DeadBeardEntity> list = this.entity
                .getWorld()
                .getNonSpectatingEntities(DeadBeardEntity.class, this.entity.getBoundingBox().expand(8.0, 4.0, 8.0));

        DeadBeardEntity closest = null;
        double minDistance = Double.MAX_VALUE;

        for (DeadBeardEntity deadBeard : list) {
            double distance = this.entity.squaredDistanceTo(deadBeard);
            if (distance < minDistance) {
                minDistance = distance;
                closest = deadBeard;
            }
        }

        if (closest == null || minDistance < 9.0) {
            return false;
        } else {
            this.target = closest;
            return true;
        }
    }




    @Override
    public boolean shouldContinue() {


        double d = this.entity.squaredDistanceTo(this.target);
        return !(d < 9.0) && !(d > 256.0);
    }


    @Override
    public void start() {
        if (this.entity.getTarget() != null) return;
        this.delay = 0;
    }

    @Override
    public void stop() {
        this.target = null;
    }

    @Override
    public void tick() {
        if (this.entity.getTarget() != null) return;

        if (--this.delay <= 0) {
            this.delay = this.getTickCount(20);
            this.entity.getNavigation().startMovingTo(this.target, this.speed);
        }
    }

}
