package net.rawnet.simpledamagetint.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.RenderPipelines;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(RenderPipelines.class)
abstract class RenderPipelinesMixin {
    private static final String SHADER_FLAG = "Lcom/mojang/renderpearl/api/pipeline/RenderPipeline$Builder;"
            + "withShaderDefine(Ljava/lang/String;)Lcom/mojang/renderpearl/api/pipeline/RenderPipeline$Builder;";

    @WrapOperation(method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=pipeline/armor_cutout_no_cull")),
            at = @At(value = "INVOKE", target = SHADER_FLAG, ordinal = 0))
    private static RenderPipeline.Builder simpleDamageTint$armor(RenderPipeline.Builder builder, String flag,
            Operation<RenderPipeline.Builder> original) {
        return withoutNoOverlay(builder, flag, original);
    }

    @WrapOperation(method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=pipeline/armor_decal_cutout_no_cull")),
            at = @At(value = "INVOKE", target = SHADER_FLAG, ordinal = 0))
    private static RenderPipeline.Builder simpleDamageTint$trim(RenderPipeline.Builder builder, String flag,
            Operation<RenderPipeline.Builder> original) {
        return withoutNoOverlay(builder, flag, original);
    }

    @WrapOperation(method = "<clinit>",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=pipeline/armor_cutout_no_cull_glint")),
            at = @At(value = "INVOKE", target = SHADER_FLAG, ordinal = 0))
    private static RenderPipeline.Builder simpleDamageTint$enchantedArmor(RenderPipeline.Builder builder, String flag,
            Operation<RenderPipeline.Builder> original) {
        return withoutNoOverlay(builder, flag, original);
    }

    @Unique
    private static RenderPipeline.Builder withoutNoOverlay(RenderPipeline.Builder builder, String flag,
            Operation<RenderPipeline.Builder> original) {
        return "NO_OVERLAY".equals(flag) ? builder.withBindGroupLayout(BindGroupLayouts.SAMPLER1) : original.call(builder, flag);
    }
}

