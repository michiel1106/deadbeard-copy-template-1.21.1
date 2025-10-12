package com.bikerboys.deadbeardcopy.entities.custom;

import com.bikerboys.deadbeardcopy.entities.*;
import com.bikerboys.deadbeardcopy.items.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.*;
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

public class ZombiePirateEntity extends ZombieEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int timeTillDeath = 600;
    private int dyingTimer = 20;

    public static final TrackedData<Integer> TARGET_ID = DataTracker.registerData(ZombiePirateEntity.class, TrackedDataHandlerRegistry.INTEGER);

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("ZombieSwabbie_Idle");
    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("ZombieSwabble_Walk");
    protected static final RawAnimation SPRINT_ANIM = RawAnimation.begin().thenLoop("ZombieSwabbie_Sprint");
    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("ZombieSwabbie_Attack", Animation.LoopType.PLAY_ONCE);

    public ZombiePirateEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }


    public ZombiePirateEntity(World world) {
        super(ModCustomEntities.ZOMBIE_PIRATE, world);
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
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TARGET_ID, -1);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 3, this::predicate).triggerableAnim("attack", ATTACK_ANIM));
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
