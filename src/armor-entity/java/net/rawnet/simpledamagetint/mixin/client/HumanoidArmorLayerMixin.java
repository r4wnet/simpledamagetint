package net.rawnet.simpledamagetint.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.rawnet.simpledamagetint.render.ArmorOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({HumanoidArmorLayer.class})
abstract class HumanoidArmorLayerMixin {
    @ModifyExpressionValue(method = {"renderModel"}, at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/renderer/texture/OverlayTexture;NO_OVERLAY:I"))
    private int simpleDamageTint$regular(int original) {
        return ArmorOverlay.regular(original);
    }

    @ModifyExpressionValue(method = {"renderTrim"}, at = @At(value = "FIELD",
            target = "Lnet/minecraft/client/renderer/texture/OverlayTexture;NO_OVERLAY:I"))
    private int simpleDamageTint$trim(int original) {
        return ArmorOverlay.trim(original);
    }
}
