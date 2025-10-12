package com.bikerboys.deadbeardcopy.entity.renderer.features;

import com.bikerboys.deadbeardcopy.entities.custom.*;
import net.minecraft.block.*;
import net.minecraft.client.*;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
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

        if (!entity.isTntBombing()) return;
        if (entity.isAlive()) {

            poseStack.push();
            poseStack.scale(2, 2, 2);
            poseStack.translate(0, 1, 0);


            int fuse = entity.getFuse();

            float time = (float) fuse - partialTick + 1.0F;
            if (time < 10.0F) {
                float scaleFactor = 1.0F - time / 10.0F;
                scaleFactor = MathHelper.clamp(scaleFactor, 0.0F, 1.0F);
                scaleFactor *= scaleFactor;
                scaleFactor *= scaleFactor;
                float finalScale = 1.0F + scaleFactor * 0.3F;
                poseStack.scale(finalScale, finalScale, finalScale);
            }


            poseStack.push();
            poseStack.translate(-0.25F, -0.25F, -0.25F);
            poseStack.scale(0.5F, 0.5F, 0.5F);


            // this is the nromal uv overlay
            int uvOverlay = 655360;

            if ((fuse / 5) % 2 == 0) {
                // here its white.
                uvOverlay = 655375;
            }

            MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, poseStack, bufferSource, packedLight, uvOverlay);


        poseStack.pop();


        poseStack.pop();
    }

    }
}
