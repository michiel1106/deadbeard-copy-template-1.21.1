package com.bikerboys.deadbeardcopy.blocks;

import com.bikerboys.deadbeardcopy.*;
import com.bikerboys.deadbeardcopy.blocks.custom.*;
import com.bikerboys.deadbeardcopy.blocks.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.registry.*;
import net.minecraft.util.*;

public class ModBlockEntities {



    public static final BlockEntityType<TreasureChestBlockEntity> TREASURE_CHEST_BLOCK_ENTITY =
            register("treasure_chest", TreasureChestBlockEntity::new, ModBlocks.TREASURE_CHEST);



    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntityFactory<? extends T> entityFactory, Block... blocks) {
        Identifier id = Identifier.of(DeadbeardCopy.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, BlockEntityType.Builder.<T>create(entityFactory, blocks).build());
    }


    public static void initialize() {

    }



}
