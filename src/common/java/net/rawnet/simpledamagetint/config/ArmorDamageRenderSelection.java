package net.rawnet.simpledamagetint.config;

import net.minecraft.network.chat.Component;

public enum ArmorDamageRenderSelection {
    NONE("options.simpledamagetint.showDamageTintOnArmor.none", false),
    ARMOR_ONLY("options.simpledamagetint.showDamageTintOnArmor.armor_only", true),
    ARMOR_AND_TRIM("options.simpledamagetint.showDamageTintOnArmor.armor_and_trim", true);

    private final String translationKey;
    private final boolean onRegularArmor;

    ArmorDamageRenderSelection(String translationKey, boolean onRegularArmor) {
        this.translationKey = translationKey;
        this.onRegularArmor = onRegularArmor;
    }

    public Component getDisplayName() {
        return Component.translatable(this.translationKey);
    }

    public boolean isOnRegularArmor() {
        return this.onRegularArmor;
    }
}

