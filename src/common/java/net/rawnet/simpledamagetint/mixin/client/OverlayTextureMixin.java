package net.rawnet.simpledamagetint.mixin.client;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.rawnet.simpledamagetint.SimpleDamageTint;
import net.rawnet.simpledamagetint.event.OverlayReloadListener;
import net.rawnet.simpledamagetint.render.OverlayPixels;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OverlayTexture.class)
abstract class OverlayTextureMixin implements OverlayReloadListener {
    @Shadow @Final private DynamicTexture texture;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void simpleDamageTint$initialize(CallbackInfo ci) {
        simpleDamageTint$onOverlayReload();
        OverlayReloadListener.register(this);
    }

    @Inject(method = "close", at = @At("HEAD"))
    private void simpleDamageTint$unregister(CallbackInfo ci) {
        OverlayReloadListener.unregister(this);
    }

    @Override
    public void simpleDamageTint$onOverlayReload() {
        NativeImage image = texture.getPixels();
        if (image == null) {
            return;
        }

        int color = SimpleDamageTint.getConfig().currentTintColor();
        int pixel = ((255 - (color >>> 24)) << 24) | (color & 0xFFFFFF);
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 16; x++) {
                OverlayPixels.set(image, x, y, pixel);
            }
        }
        texture.upload();
    }
}
