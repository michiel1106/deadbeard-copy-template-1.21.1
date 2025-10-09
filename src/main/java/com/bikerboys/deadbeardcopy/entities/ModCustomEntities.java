package com.bikerboys.deadbeardcopy.entities;

import com.bikerboys.deadbeardcopy.*;

import net.fabricmc.fabric.api.object.builder.v1.entity.*;
import net.minecraft.entity.*;
import net.minecraft.registry.*;
import net.minecraft.registry.Registry;
import net.minecraft.util.*;

public class ModCustomEntities {


    public static final EntityType<DeadBeardEntity> DEADBEARD = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(DeadbeardCopy.MOD_ID, "deadbeard"),
            EntityType.Builder.create(DeadBeardEntity::new, SpawnGroup.MONSTER).dimensions(1.25f, 2.0f).build("deadbeard")
    );


    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(DEADBEARD, DeadBeardEntity.setAttributes());
    }

    public static void initialize() {
        registerAttributes();
    }

}
