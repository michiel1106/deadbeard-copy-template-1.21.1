package com.bikerboys.deadbeardcopy.entities;


import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.animatable.instance.*;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.*;

public class DeadBeardEntity extends HostileEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("Deadbeard_walkNormal");
    protected static final RawAnimation WALK_AGGRO_ANIM = RawAnimation.begin().thenLoop("Deadbeard_walkAggro");
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("Deadbeard_IdleNormal");
    protected static final RawAnimation IDLE_AGRO = RawAnimation.begin().thenLoop("Deadbeard_IdleAggro");
    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("Deadbeard_attack", Animation.LoopType.PLAY_ONCE);
    protected static final RawAnimation TNT_DEATH = RawAnimation.begin().thenLoop("Deadbeard_tntDeath");

    protected DeadBeardEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }


    public static DefaultAttributeContainer.Builder setAttributes() {
        return createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0f)
                .add(EntityAttributes.GENERIC_ARMOR, 0.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f);
    }


    /*
    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new ZombieAttackGoal(this, 0.5D, false));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.5f, 3));

        this.goalSelector.add(4, new LookAroundGoal(this));

        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, MerchantEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, ChickenEntity.class, true));
    }

     */




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 5, this::predicate).triggerableAnim("Deadbeard_attack", ATTACK_ANIM));
    }

    @Override
    protected void attackLivingEntity(LivingEntity target) {
        super.attackLivingEntity(target);


    }




    private PlayState predicate(AnimationState<DeadBeardEntity> event) {

        if (this.isAttacking()) {
            return event.setAndContinue(ATTACK_ANIM);
        }


        if (event.isMoving()) {
            if (this.getTarget() != null) {
                System.out.println(this.getTarget());
                return event.setAndContinue(WALK_AGGRO_ANIM);
            }

            return event.setAndContinue(WALK_ANIM);

        }



        if (this.getTarget() != null) {
            return event.setAndContinue(IDLE_AGRO);
        }
        return event.setAndContinue(IDLE_ANIM);

        
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
