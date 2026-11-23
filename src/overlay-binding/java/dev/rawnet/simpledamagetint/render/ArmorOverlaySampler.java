package dev.rawnet.simpledamagetint.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.BindGroupLayouts;

public final class ArmorOverlaySampler {
    private ArmorOverlaySampler() {
    }

    public static RenderPipeline.Builder bind(RenderPipeline.Builder builder) {
        return builder.withBindGroupLayout(BindGroupLayouts.SAMPLER1);
    }
}
