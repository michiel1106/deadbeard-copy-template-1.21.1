package com.bikerboys.deadbeardcopy.entity.renderer;

import com.bikerboys.deadbeardcopy.*;
import com.bikerboys.deadbeardcopy.entities.deadbeard.*;
import com.bikerboys.deadbeardcopy.entity.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.util.math.*;
import net.minecraft.util.*;
import software.bernie.geckolib.renderer.*;
import software.bernie.geckolib.renderer.layer.*;

import java.util.function.*;

public class DeadBeardRenderer extends GeoEntityRenderer<DeadBeardEntity> {

    public DeadBeardRenderer(EntityRendererFactory.Context context) {
        super(context, new DeadBeardModel());
        this.addRenderLayer(new TntFeatureRenderer(this));
    }



    @Override
    public Identifier getTexture(DeadBeardEntity entity) {
        return Identifier.of(DeadbeardCopy.MOD_ID, "textures/entity/deadbeard/deadbeard.png");
    }


    @Override
    public void render(DeadBeardEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }


}
