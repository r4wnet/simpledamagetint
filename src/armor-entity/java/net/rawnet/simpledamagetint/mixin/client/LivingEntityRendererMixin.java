package net.rawnet.simpledamagetint.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.rawnet.simpledamagetint.SimpleDamageTint;
import net.rawnet.simpledamagetint.render.ArmorOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
abstract class LivingEntityRendererMixin {
    @Shadow protected abstract float getWhiteOverlayProgress(LivingEntity entity, float partialTick);

    @WrapOperation(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/layers/RenderLayer;render"
                    + "(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;"
                    + "ILnet/minecraft/world/entity/Entity;FFFFFF)V"))
    private void simpleDamageTint$renderLayer(RenderLayer<?, ?> layer, PoseStack poseStack,
            MultiBufferSource output, int light, Entity entity, float limbSwing, float limbAmount, float partialTick, float age, float headYaw, float headPitch,
            Operation<Void> original) {
        if (entity instanceof LivingEntity living
                && SimpleDamageTint.getConfig().damageTintOnArmor.isOnRegularArmor()) {
            int coords = LivingEntityRenderer.getOverlayCoords(living, getWhiteOverlayProgress(living, partialTick));
            Integer previous = ArmorOverlay.enter(coords);
            try {
                original.call(layer, poseStack, output, light, entity, limbSwing, limbAmount, partialTick, age, headYaw, headPitch);
            } finally {
                ArmorOverlay.exit(previous);
            }
        } else {
            original.call(layer, poseStack, output, light, entity, limbSwing, limbAmount, partialTick, age, headYaw, headPitch);
        }
    }
}
