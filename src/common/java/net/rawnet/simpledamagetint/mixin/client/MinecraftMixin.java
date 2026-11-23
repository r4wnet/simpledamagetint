package net.rawnet.simpledamagetint.mixin.client;

import net.minecraft.client.Minecraft;
import net.rawnet.simpledamagetint.SimpleDamageTint;
import net.rawnet.simpledamagetint.event.OverlayReloadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
abstract class MinecraftMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void simpleDamageTint$animateRainbow(CallbackInfo ci) {
        if (SimpleDamageTint.getConfig().rainbowTint) {
            OverlayReloadListener.callEvent();
        }
    }
}
