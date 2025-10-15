package com.bikerboys.deadbeardcopy.blocks;

import com.bikerboys.deadbeardcopy.*;
import com.bikerboys.deadbeardcopy.blocks.custom.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;

public class ModBlocks {
    public static final Block TREASURE_CHEST = register(
            new TreasureChestBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).nonOpaque().requiresTool().strength(1.5f, 6.0f)),
            "treasure_chest",
            true
    );

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        Identifier id = Identifier.of(DeadbeardCopy.MOD_ID, name);
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void initialize() {
    }



}
