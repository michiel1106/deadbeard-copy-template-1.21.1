package com.bikerboys.deadbeardcopy.entities.deadbeard;


import com.bikerboys.deadbeardcopy.entities.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.data.*;
import net.minecraft.entity.mob.*;
import net.minecraft.item.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.animatable.instance.*;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.*;

public class DeadBeardEntity extends ZombieEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final TrackedData<Boolean> TNT_ACTIVE = DataTracker.registerData(DeadBeardEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Integer> TNT_FUSE = DataTracker.registerData(DeadBeardEntity.class, TrackedDataHandlerRegistry.INTEGER);


    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("Deadbeard_walkNormal");
    protected static final RawAnimation WALK_AGGRO_ANIM = RawAnimation.begin().thenLoop("Deadbeard_walkAggro");
    protected static final RawAnimation WALK_ANIM_OTHER = RawAnimation.begin().thenLoop("walk");
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("Deadbeard_IdleNormal");
    protected static final RawAnimation IDLE_AGRO = RawAnimation.begin().thenLoop("Deadbeard_IdleAggro");
    protected static final RawAnimation IDLE_OTHER = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("Deadbeard_attack", Animation.LoopType.PLAY_ONCE);
    protected static final RawAnimation ATTACK_ANIM_OTHER = RawAnimation.begin().then("attack", Animation.LoopType.PLAY_ONCE);
    protected static final RawAnimation TNT_DEATH = RawAnimation.begin().thenLoop("tnt");

    public DeadBeardEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
        this.activeItemStack = new ItemStack(Items.STONE_SWORD);



    }

    public DeadBeardEntity(World world) {
        super(ModCustomEntities.DEADBEARD, world);
        this.activeItemStack = new ItemStack(Items.STONE_SWORD);

    }


    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TNT_ACTIVE, false);
        builder.add(TNT_FUSE, 100);

    }



    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0)
                .add(EntityAttributes.GENERIC_ARMOR, 2.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 70.0)
                .add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
    }

    @Override
    public void tick() {




        if (this.isAlive()) {
            if (getHealth() <= 10) {
                setTntBombing();
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
        }
        super.tick();

    }



    public void explode() {
        this.getWorld().createExplosion(this, getX(), getY(), getZ(), 3, World.ExplosionSourceType.TNT);
        this.kill();

    }






    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 5, this::predicate).triggerableAnim("attack", ATTACK_ANIM_OTHER));
    }

    @Override
    protected void attackLivingEntity(LivingEntity target) {
        super.attackLivingEntity(target);


    }

    @Override
    protected boolean burnsInDaylight() {
        return false;
    }

    private PlayState predicate(AnimationState<DeadBeardEntity> event) {

        if (isTntBombing()) {
            return event.setAndContinue(TNT_DEATH);
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

    public void setTntBombing() {
        this.getDataTracker().set(TNT_ACTIVE, true);
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
