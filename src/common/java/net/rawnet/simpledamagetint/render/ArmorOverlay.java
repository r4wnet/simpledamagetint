package net.rawnet.simpledamagetint.render;

import net.minecraft.client.renderer.texture.OverlayTexture;
import net.rawnet.simpledamagetint.SimpleDamageTint;
import net.rawnet.simpledamagetint.config.ArmorDamageRenderSelection;

public final class ArmorOverlay {
    private static final ThreadLocal<Integer> COORDS = new ThreadLocal<>();

    private ArmorOverlay() {
    }

    public static Integer enter(int coords) {
        Integer previous = COORDS.get();
        COORDS.set(coords);
        return previous;
    }

    public static void exit(Integer previous) {
        if (previous == null) {
            COORDS.remove();
        } else {
            COORDS.set(previous);
        }
    }

    public static int regular(int original) {
        return SimpleDamageTint.getConfig().damageTintOnArmor.isOnRegularArmor() ? coords(original) : original;
    }

    public static int trim(int original) {
        return SimpleDamageTint.getConfig().damageTintOnArmor == ArmorDamageRenderSelection.ARMOR_AND_TRIM
                ? coords(original) : original;
    }

    private static int coords(int original) {
        Integer coords = COORDS.get();
        return coords != null && coords != OverlayTexture.NO_OVERLAY ? coords : original;
    }
}
