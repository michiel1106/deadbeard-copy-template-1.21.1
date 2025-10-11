package com.bikerboys.deadbeardcopy.entities;

import net.minecraft.entity.*;
import net.minecraft.entity.data.*;
import net.minecraft.entity.mob.*;
import net.minecraft.item.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.animatable.instance.*;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.*;

public class SkeletonPirateEntity extends SkeletonEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public static final TrackedData<Integer> TARGET_ID = DataTracker.registerData(SkeletonPirateEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public boolean isAnimatingMoving;
    public double animationTick;

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("SkeletonSwabbie_Idle");
    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("SkeletonSwabbie_Walk");
    protected static final RawAnimation SPRINT_ANIM = RawAnimation.begin().thenLoop("SkeletonSwabbie_Sprint");
    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("SkeletonSwabbie_Attack", Animation.LoopType.PLAY_ONCE);

    public SkeletonPirateEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 5, this::predicate).triggerableAnim("attack", ATTACK_ANIM));

    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TARGET_ID, -1);

    }

    @Override
    protected boolean isAffectedByDaylight() {
        return false;
    }


    @Nullable
    public LivingEntity getClientTarget() {
        int id = this.getDataTracker().get(TARGET_ID);
        if (id == -1) return null;
        Entity e = this.getWorld().getEntityById(id);
        return e instanceof LivingEntity le ? le : null;
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        this.getDataTracker().set(TARGET_ID, target == null ? -1 : target.getId());
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean b = super.tryAttack(target);

        if (b) {
            triggerAnim("controller", "attack");
        }

        return b;

    }

    @Override
    public void onAttacking(Entity target) {
        super.onAttacking(target);

    }

    @Override
    public void tick() {

        super.tick();
    }

    private PlayState predicate(AnimationState<SkeletonPirateEntity> event) {
        animationTick = event.getAnimationTick();



        if (this.getClientTarget() != null) {
            if (event.isMoving()) {
                isAnimatingMoving = event.isCurrentAnimation(SPRINT_ANIM) && event.isMoving();

                return event.setAndContinue(SPRINT_ANIM);



            }
        }

        if (event.isMoving()) {
            return event.setAndContinue(WALK_ANIM);
        }



        return event.setAndContinue(IDLE_ANIM);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }


    @Override
    protected void convertToStray() {
    }

    @Override
    public boolean isConverting() {
        return false;
    }

    @Override
    public void setConverting(boolean converting) {
    }


    @Override
    public void shootAt(LivingEntity target, float pullProgress) {

    }
}
