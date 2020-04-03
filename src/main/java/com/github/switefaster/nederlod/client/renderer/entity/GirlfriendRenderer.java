package com.github.switefaster.nederlod.client.renderer.entity;

import com.github.switefaster.nederlod.entity.GirlfriendEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class GirlfriendRenderer extends MobRenderer<GirlfriendEntity, PlayerModel<GirlfriendEntity>> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/alex.png");

    public GirlfriendRenderer(EntityRendererManager renderManager) {
        super(renderManager, new PlayerModel<>(0.0f, true), 0.5f);
    }

    @Override
    protected void preRenderCallback(GirlfriendEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull GirlfriendEntity entity) {
        return TEXTURE_LOCATION;
    }
}
