package com.bikerboys.deadbeardcopy.entity.renderer;

import com.bikerboys.deadbeardcopy.entities.deadbeard.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.*;
import net.minecraft.item.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.cache.object.*;
import software.bernie.geckolib.renderer.*;
import software.bernie.geckolib.renderer.layer.*;

public class TntFeatureRenderer extends BlockAndItemGeoLayer<DeadBeardEntity> {


    public TntFeatureRenderer(GeoRenderer<DeadBeardEntity> renderer) {
        super(renderer);
    }

    @Override
    protected @Nullable ItemStack getStackForBone(GeoBone bone, DeadBeardEntity animatable) {
        return null;
    }

    @Override
    protected @Nullable BlockState getBlockForBone(GeoBone bone, DeadBeardEntity animatable) {
        if (bone.getName().equals("Body")) {
            return Blocks.TNT.getDefaultState();
        }

        return null;

    }

    @Override
    protected void renderBlockForBone(MatrixStack poseStack, GeoBone bone, BlockState state, DeadBeardEntity entity, VertexConsumerProvider bufferSource, float partialTick, int packedLight, int packedOverlay) {
        poseStack.push();
        poseStack.scale(2, 2, 2);
        poseStack.translate(0, 1, 0);


        poseStack.push();
        poseStack.translate(-0.25F, -0.25F, -0.25F);
        poseStack.scale(0.5F, 0.5F, 0.5F);


        if (entity.getFuse())

        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, poseStack, bufferSource, packedLight, 655375);
        poseStack.pop();


        poseStack.pop();


    }
}
