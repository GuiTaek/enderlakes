package com.gmail.guitaekm.enderlakes;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gmail.guitaekm.enderlakes.Config;

public class Enderlakes implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("enderlakes");

	public static final Config CONFIG = Config.createAndLoad();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		System.out.println(CONFIG.mySampleString());
		LOGGER.info("Hello Fabric world!");
	}
}