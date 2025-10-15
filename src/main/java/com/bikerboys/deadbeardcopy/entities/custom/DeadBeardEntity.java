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
import net.minecraft.sound.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.animatable.instance.*;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.*;

import java.util.*;

public class DeadBeardEntity extends ZombieEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean spawnLoot = false;
    private static final TrackedData<Boolean> TNT_ACTIVE = DataTracker.registerData(DeadBeardEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> TNT_FUSE = DataTracker.registerData(DeadBeardEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> TARGET_ID = DataTracker.registerData(DeadBeardEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private final Random random = new Random();
    private int spawnCooldown = 400;
    protected static final RawAnimation IDLE_OTHER = RawAnimation.begin().thenLoop("Deadbeard_IdleNormal");
    protected static final RawAnimation IDLE_AGGRO = RawAnimation.begin().thenLoop("Deadbeard_walkAggro");

    protected static final RawAnimation TNT_DEATH = RawAnimation.begin().thenLoop("tnt");
    protected static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    protected static final RawAnimation ATTACK = RawAnimation.begin().then("Deadbeard_attack", Animation.LoopType.PLAY_ONCE);
    protected static final RawAnimation WALK_AGGRO = RawAnimation.begin().thenLoop("Deadbeard_walkAggro");

    public DeadBeardEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
        this.activeItemStack = new ItemStack(ModItems.STONE_CUTLASS);
    }

    public DeadBeardEntity(World world) {
        super(ModCustomEntities.DEADBEARD, world);
        this.activeItemStack = new ItemStack(ModItems.STONE_CUTLASS);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TNT_ACTIVE, false);
        builder.add(TNT_FUSE, 100);
        builder.add(TARGET_ID, -1);
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
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("tnt_active", isTntBombing());
        nbt.putInt("tnt_fuse", dataTracker.get(TNT_FUSE));
        nbt.putInt("spawn_cooldown", spawnCooldown);
        nbt.putBoolean("spawn_loot", spawnLoot);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean canAttack = super.tryAttack(target) && !isTntBombing();
        if (canAttack) {
            triggerAnim("controller", "attack");
        }
        return canAttack;
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.spawnCooldown = nbt.contains("spawn_cooldown") ? nbt.getInt("spawn_cooldown") : 20;
        setTntBombing(nbt.contains("tnt_active") ? nbt.getBoolean("tnt_active") : false);
        setFuse(nbt.contains("tnt_fuse") ? nbt.getInt("tnt_fuse") : 100);
        this.spawnLoot = nbt.contains("spawn_loot") ? nbt.getBoolean("spawn_loot") : true;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 70.0)
                .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS, 0);
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            if (getHealth() <= 10) {
                setTntBombing(true);
            }
            if (isTntBombing()) {
                if (getFuse() >= 1) {
                    decreaseFuse();
                } else if (getFuse() == 0) {
                    explode();
                }
                if (getFuse() == 99) {
                    this.playSound(SoundEvents.ENTITY_TNT_PRIMED);
                }
            }
            if (getTarget() != null) {
                if (this.spawnCooldown > 0) {
                    spawnCooldown--;
                }
                if (spawnCooldown == 0) {
                    spawnLackeys();
                    spawnCooldown = random.nextInt(1200, 1500);
                }
            }
        }
        super.tick();
    }

    @Override
    protected void initEquipment(net.minecraft.util.math.random.Random random, LocalDifficulty localDifficulty) {
        equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.STONE_CUTLASS));
    }

    @Override
    public void setBaby(boolean baby) {
    }

    @Override
    public void tickMovement() {
        if (!isTntBombing()) {
            super.tickMovement();
        }
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
    }

    public void spawnLackeys() {
        int skeleLackeys;
        int zombieLackeys;

        skeleLackeys = random.nextInt(2, 4);
        zombieLackeys = random.nextInt(2, 4);

        for (int i = 0; i < skeleLackeys; i++) {
            SkeletonPirateEntity skeletonPirateEntity = new SkeletonPirateEntity(getWorld());
            double x = this.getX();
            double z = this.getZ();

            double newx = random.nextDouble(-2, i + i);
            double newz = random.nextDouble(-2, i + i);

            x += newx;
            z += newz;

            skeletonPirateEntity.setPos(x, this.getY(), z);
            skeletonPirateEntity.initEquipment(net.minecraft.util.math.random.Random.create(), getWorld().getLocalDifficulty(getBlockPos()));

            getWorld().spawnEntity(skeletonPirateEntity);
        }

        for (int i = 0; i < zombieLackeys; i++) {
            ZombiePirateEntity zombiePirateEntity = new ZombiePirateEntity(getWorld());
            double x = this.getX();
            double z = this.getZ();

            double newx = random.nextDouble(-2, i + i);
            double newz = random.nextDouble(-2, i + i);

            x += newx;
            z += newz;

            zombiePirateEntity.setPos(x, this.getY(), z);
            zombiePirateEntity.initEquipment(net.minecraft.util.math.random.Random.create(), getWorld().getLocalDifficulty(getBlockPos()));

            getWorld().spawnEntity(zombiePirateEntity);
        }
    }

    @Override
    protected void initAttributes() {
    }

    public void explode() {
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, getX(), getY(), getZ(), 3, World.ExplosionSourceType.TNT);
        }
        this.spawnLoot = false;
        this.kill();
    }

    @Override
    protected void dropLoot(DamageSource damageSource, boolean causedByPlayer) {
        if (spawnLoot) {
            super.dropLoot(damageSource, causedByPlayer);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 3, this::predicate).triggerableAnim("attack", ATTACK));
    }

    @Override
    protected boolean burnsInDaylight() {
        return false;
    }

    private PlayState predicate(AnimationState<DeadBeardEntity> event) {
        if (isTntBombing()) {
            return event.setAndContinue(TNT_DEATH);
        }

        if (this.getClientTarget() != null) {
            if (event.isMoving()) {
                return event.setAndContinue(WALK_AGGRO);
            }
        }

        if (event.isMoving()) {
            return event.setAndContinue(WALK);
        }

        if (this.getClientTarget() != null) {
            return event.setAndContinue(IDLE_AGGRO);
        }
        return event.setAndContinue(IDLE_OTHER);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public boolean isTntBombing() {
        return this.getDataTracker().get(TNT_ACTIVE);
    }

    public void setTntBombing(boolean val) {
        this.getDataTracker().set(TNT_ACTIVE, val);
    }

    public int getFuse() {
        return this.dataTracker.get(TNT_FUSE);
    }

    public void setFuse(int fuse) {
        this.dataTracker.set(TNT_FUSE, fuse);
    }

    public void decreaseFuse() {
        setFuse(getFuse() - 1);
    }

    @Override
    protected void convertInWater() {
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
