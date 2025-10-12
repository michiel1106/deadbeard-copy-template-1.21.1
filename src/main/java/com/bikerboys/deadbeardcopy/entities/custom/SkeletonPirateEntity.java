package com.bikerboys.deadbeardcopy.entities.custom;

import com.bikerboys.deadbeardcopy.entities.*;
import com.bikerboys.deadbeardcopy.items.*;
import net.minecraft.entity.*;
import net.minecraft.entity.data.*;
import net.minecraft.entity.mob.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
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

    private int timeTillDeath = 600;
    private int dyingTimer = 20;

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("SkeletonSwabbie_Idle");
    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("SkeletonSwabbie_Walk");
    protected static final RawAnimation SPRINT_ANIM = RawAnimation.begin().thenLoop("SkeletonSwabbie_Sprint");
    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("SkeletonSwabbie_Attack", Animation.LoopType.PLAY_ONCE);

    public SkeletonPirateEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    public SkeletonPirateEntity(World world) {
        super(ModCustomEntities.SKELETON_PIRATE, world);
    }

    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.GOLD_CUTLASS));
        this.equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModItems.GOLD_CUTLASS));
    }

    @Override
    public void tick() {
        super.tick();

        if (timeTillDeath > 0) {
            timeTillDeath--;
        } else {
            // Only start "dying" after countdown hits 0
            if (dyingTimer-- <= 0) {
                this.damage(getWorld().getDamageSources().magic(), 2);
                dyingTimer = 20; // Repeat damage every 10 ticks
            }
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("timeTillDeath", this.timeTillDeath);
        nbt.putInt("dyingTimer", this.dyingTimer);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.timeTillDeath = nbt.contains("timeTillDeath") ? nbt.getInt("timeTillDeath") : 600;
        this.dyingTimer = nbt.contains("dyingTimer") ? nbt.getInt("dyingTimer") : 20;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 3, this::predicate).triggerableAnim("attack", ATTACK_ANIM));

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

    private PlayState predicate(AnimationState<SkeletonPirateEntity> event) {
        if (this.getClientTarget() != null) {
            if (event.isMoving()) {
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
