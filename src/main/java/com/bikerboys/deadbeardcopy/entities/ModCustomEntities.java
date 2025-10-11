package com.bikerboys.deadbeardcopy.entities;

import com.bikerboys.deadbeardcopy.*;

import net.fabricmc.fabric.api.object.builder.v1.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.registry.*;
import net.minecraft.registry.Registry;
import net.minecraft.util.*;

public class ModCustomEntities {


    public static final EntityType<DeadBeardEntity> DEADBEARD = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DeadbeardCopy.MOD_ID, "deadbeard"),
            EntityType.Builder.<DeadBeardEntity>create(DeadBeardEntity::new, SpawnGroup.MONSTER)
                    .dimensions(1.25f, 2.0f)
                    .maxTrackingRange(8)

                    .build("deadbeard")
    );

    public static final EntityType<SkeletonPirateEntity> SKELETON_PIRATE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DeadbeardCopy.MOD_ID, "skeleton_pirate"),
            EntityType.Builder.<SkeletonPirateEntity>create(SkeletonPirateEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.99F)
                    .eyeHeight(1.74F
                    ).vehicleAttachment(-0.7F)
                    .maxTrackingRange(8)

                    .build("deadbeard")
    );

    public static final EntityType<ZombiePirateEntity> ZOMBIE_PIRATE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DeadbeardCopy.MOD_ID, "zombie_pirate"),
            EntityType.Builder.<ZombiePirateEntity>create(ZombiePirateEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.6F, 1.95F)
                    .eyeHeight(1.74F)
                    .passengerAttachments(2.0125F)
                    .vehicleAttachment(-0.7F)
                    .maxTrackingRange(8)

                    .build("deadbeard")
    );


    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(DEADBEARD, DeadBeardEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(SKELETON_PIRATE, AbstractSkeletonEntity.createAbstractSkeletonAttributes());
        FabricDefaultAttributeRegistry.register(ZOMBIE_PIRATE, ZombieEntity.createZombieAttributes());

    }

    public static void initialize() {
        registerAttributes();


    }

}
