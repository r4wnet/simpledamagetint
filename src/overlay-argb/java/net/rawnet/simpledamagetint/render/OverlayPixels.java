package net.rawnet.simpledamagetint.render;

import com.mojang.blaze3d.platform.NativeImage;

public final class OverlayPixels {
    private OverlayPixels() {
    }

    public static void set(NativeImage image, int x, int y, int argb) {
        image.setPixel(x, y, argb);
    }
}
