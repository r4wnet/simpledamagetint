package net.rawnet.simpledamagetint.render;

import com.mojang.blaze3d.platform.NativeImage;

public final class OverlayPixels {
    private OverlayPixels() {
    }

    public static void set(NativeImage image, int x, int y, int argb) {
        int abgr = (argb & 0xFF00FF00) | ((argb & 0xFF0000) >>> 16) | ((argb & 0xFF) << 16);
        image.setPixelRGBA(x, y, abgr);
    }
}
