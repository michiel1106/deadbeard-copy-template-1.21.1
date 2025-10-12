	package com.bikerboys.deadbeardcopy;

	import com.bikerboys.deadbeardcopy.entities.*;
	import com.bikerboys.deadbeardcopy.items.*;
	import net.fabricmc.api.ModInitializer;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;

	public class DeadbeardCopy implements ModInitializer {
		public static final String MOD_ID = "deadbeardcopy";

		public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

		@Override
		public void onInitialize() {

			ModCustomEntities.initialize();
			ModItems.initialize();
		}
	}