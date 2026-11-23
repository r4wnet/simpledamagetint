package net.rawnet.simpledamagetint.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.rawnet.simpledamagetint.SimpleDamageTint;
import net.rawnet.simpledamagetint.render.ArmorOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
abstract class LivingEntityRendererMixin {
    @Shadow protected abstract float getWhiteOverlayProgress(LivingEntityRenderState entity);

    @WrapOperation(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/layers/RenderLayer;render"
                    + "(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;"
                    + "ILnet/minecraft/client/renderer/entity/state/EntityRenderState;FF)V"))
    private void simpleDamageTint$renderLayer(RenderLayer<?, ?> layer, PoseStack poseStack,
            MultiBufferSource output, int light, EntityRenderState entity, float headYaw, float headPitch,
            Operation<Void> original) {
        if (entity instanceof LivingEntityRenderState living
                && SimpleDamageTint.getConfig().damageTintOnArmor.isOnRegularArmor()) {
            int coords = LivingEntityRenderer.getOverlayCoords(living, getWhiteOverlayProgress(living));
            Integer previous = ArmorOverlay.enter(coords);
            try {
                original.call(layer, poseStack, output, light, entity, headYaw, headPitch);
            } finally {
                ArmorOverlay.exit(previous);
            }
        } else {
            original.call(layer, poseStack, output, light, entity, headYaw, headPitch);
        }
    }
}
