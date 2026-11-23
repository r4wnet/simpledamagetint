package net.rawnet.simpledamagetint.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.rawnet.simpledamagetint.render.ArmorOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EquipmentLayerRenderer.class)
abstract class EquipmentLayerRendererMixin {
    private static final String NO_OVERLAY = "Lnet/minecraft/client/renderer/texture/OverlayTexture;NO_OVERLAY:I";

    @ModifyExpressionValue(method = "renderLayers*", at = @At(value = "FIELD", target = NO_OVERLAY, ordinal = 0))
    private int simpleDamageTint$armor(int original) {
        return ArmorOverlay.regular(original);
    }

    @ModifyExpressionValue(method = "renderLayers*", at = @At(value = "FIELD", target = NO_OVERLAY, ordinal = 1))
    private int simpleDamageTint$trim(int original) {
        return ArmorOverlay.trim(original);
    }
}

