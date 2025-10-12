package com.bikerboys.deadbeardcopy.entities.custom;

import com.bikerboys.deadbeardcopy.items.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
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

public class ZombiePirateEntity extends ZombieEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static final TrackedData<Integer> TARGET_ID = DataTracker.registerData(ZombiePirateEntity.class, TrackedDataHandlerRegistry.INTEGER);

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("ZombieSwabbie_Idle");
    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("ZombieSwabble_Walk");
    protected static final RawAnimation SPRINT_ANIM = RawAnimation.begin().thenLoop("ZombieSwabbie_Sprint");
    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("ZombieSwabbie_Attack", Animation.LoopType.PLAY_ONCE);

    public ZombiePirateEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }




    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TARGET_ID, -1);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 3, this::predicate).triggerableAnim("attack", ATTACK_ANIM));
    }


    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {

        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.STONE_CUTLASS));
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
        if (b) {triggerAnim("controller", "attack");}
        return b;

    }

    private PlayState predicate(AnimationState<ZombiePirateEntity> event) {
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


    public static DefaultAttributeContainer.Builder createZombiePirateAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0)
                .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0);
    }


    @Override
    protected void initAttributes() {

    }

    @Override
    protected boolean isAffectedByDaylight() {
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected boolean canConvertInWater() {
        return false;
    }


    @Override
    public boolean isConvertingInWater() {
        return false;
    }

}
