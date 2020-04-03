package com.github.switefaster.nederlod.client.renderer.tileentity;

import com.github.switefaster.nederlod.capability.NederlodCapabilities;
import com.github.switefaster.nederlod.tileentity.ItemConveyorTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ItemConveyorTESR extends TileEntityRenderer<ItemConveyorTileEntity> {
    public ItemConveyorTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(@Nonnull ItemConveyorTileEntity tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        tileEntityIn.getCapability(NederlodCapabilities.SYNC_PROGRESSIVE).ifPresent(cap -> cap.getAll().forEach((progress, items) -> {
            matrixStackIn.push();
            matrixStackIn.translate((double) progress / cap.getMaxProgress(), 0.5d, 0.5d);
            matrixStackIn.rotate(Vector3f.XP.rotation((float) Math.PI / 2));
            items.forEach(item -> renderer.renderItem(item, TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn));
            matrixStackIn.pop();
        }));
    }
}
