package dev.rawnet.simpledamagetint.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;

public final class ArmorOverlaySampler {
    private ArmorOverlaySampler() {
    }

    public static RenderPipeline.Builder bind(RenderPipeline.Builder builder) {
        return builder.withSampler("Sampler1");
    }
}
