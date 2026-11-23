package net.rawnet.simpledamagetint;

import java.nio.file.Path;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.rawnet.simpledamagetint.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleDamageTint implements ModInitializer {
    public static final String MOD_ID = "simpledamagetint";
    private static final Logger LOGGER = LogManager.getLogger("SimpleDamageTint");
    private static ModConfig config;

    public void onInitialize() {
        Path file = FabricLoader.getInstance().getConfigDir().resolve("simpledamagetint.json");
        config = ModConfig.load(file, LOGGER);
        LOGGER.info("SimpleDamageTint has been loaded.");
    }

    public static ModConfig getConfig() {
        return config;
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}

