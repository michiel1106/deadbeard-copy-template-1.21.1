package com.bikerboys.deadbeardcopy.blocks;

import com.bikerboys.deadbeardcopy.*;
import com.bikerboys.deadbeardcopy.blocks.custom.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;

import java.util.function.*;

public class ModBlocks {



    public static final Block TREASURE_CHEST = register(
            new TreasureChestBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).nonOpaque().requiresTool().strength(1.5f, 6.0f)),
            "treasure_chest",
            true
    );


    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        Identifier id = Identifier.of(DeadbeardCopy.MOD_ID, name);

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }



    public static void initialize() {

    }



}
