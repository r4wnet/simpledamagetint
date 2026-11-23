package net.rawnet.simpledamagetint.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.Logger;

public class ModConfig {
    public static final int DEFAULT_DAMAGE_COLOR = 0x4DFF0000;
    public static final ArmorDamageRenderSelection DEFAULT_DAMAGE_TINT_ON_ARMOR = ArmorDamageRenderSelection.NONE;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public boolean rainbowTint;
    public int damageColor = DEFAULT_DAMAGE_COLOR;
    public ArmorDamageRenderSelection damageTintOnArmor = DEFAULT_DAMAGE_TINT_ON_ARMOR;
    private transient Path file;
    private transient Logger logger;

    public int currentTintColor() {
        if (!rainbowTint) {
            return damageColor;
        }
        // One hue cycle in 3.6 seconds, with opacity taken from the user's color.
        float hue = Math.floorMod(System.nanoTime(), 3_600_000_000L) / 3_600_000_000f;
        int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
        return (damageColor & 0xFF000000) | (rgb & 0x00FFFFFF);
    }

    public static ModConfig load(Path file, Logger logger) {
        ModConfig config = new ModConfig();
        if (Files.exists(file)) {
            try (BufferedReader reader = Files.newBufferedReader(file)) {
                ModConfig read = GSON.fromJson(reader, ModConfig.class);
                if (read != null) {
                    config = read;
                }
            }
            catch (IOException | RuntimeException e) {
                logger.error("Failed to read SimpleDamageTint config, falling back to defaults", e);
            }
        }
        if (config.damageTintOnArmor == null) {
            config.damageTintOnArmor = DEFAULT_DAMAGE_TINT_ON_ARMOR;
        }
        config.file = file;
        config.logger = logger;
        config.save();
        return config;
    }

    public void save() {
        try {
            Files.createDirectories(this.file.getParent());
            try (BufferedWriter writer = Files.newBufferedWriter(this.file)) {
                GSON.toJson(this, writer);
            }
        }
        catch (IOException e) {
            this.logger.error("Failed to save SimpleDamageTint config", e);
        }
    }
}

