	package com.bikerboys.deadbeardcopy;

	import com.bikerboys.deadbeardcopy.entities.*;
	import com.bikerboys.deadbeardcopy.items.*;
	import net.fabricmc.api.ModInitializer;

	import net.fabricmc.fabric.api.biome.v1.*;
	import net.minecraft.entity.*;
	import net.minecraft.entity.mob.*;
	import net.minecraft.world.*;
	import net.minecraft.world.biome.*;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	public class DeadbeardCopy implements ModInitializer {
		public static final String MOD_ID = "deadbeardcopy";

		public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

		@Override
		public void onInitialize() {

			ModCustomEntities.initialize();
			ModItems.initialize();

			BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.BEACH), SpawnGroup.MONSTER, ModCustomEntities.DEADBEARD, 500, 1, 1);

			SpawnRestriction.register(ModCustomEntities.DEADBEARD, SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

		}
	}