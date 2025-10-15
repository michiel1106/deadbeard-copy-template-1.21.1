package com.bikerboys.deadbeardcopy;

import static com.bikerboys.deadbeardcopy.DeadbeardCopy.MOD_ID;
import com.bikerboys.deadbeardcopy.blocks.*;
import com.bikerboys.deadbeardcopy.entities.*;
import com.bikerboys.deadbeardcopy.entity.model.*;
import com.bikerboys.deadbeardcopy.entity.renderer.*;
import com.bikerboys.deadbeardcopy.entity.renderer.block.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.*;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.util.*;

public class DeadbeardCopyClient implements ClientModInitializer {
	public static final EntityModelLayer TREASURE_CHEST =
			new EntityModelLayer(Identifier.of("deadbeardcopy", "treasure_chest"), "main");

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModCustomEntities.DEADBEARD, (DeadBeardRenderer::new));
		EntityRendererRegistry.register(ModCustomEntities.SKELETON_PIRATE, (SkeletonPirateRenderer::new));
		EntityRendererRegistry.register(ModCustomEntities.ZOMBIE_PIRATE, (ZombiePirateRenderer::new));

		EntityModelLayerRegistry.registerModelLayer(TREASURE_CHEST, TreasureChestModel::getTexturedModelData);

		BlockEntityRendererFactories.register(ModBlockEntities.TREASURE_CHEST_BLOCK_ENTITY, TreasureChestBlockEntityRenderer::new);
	}
}