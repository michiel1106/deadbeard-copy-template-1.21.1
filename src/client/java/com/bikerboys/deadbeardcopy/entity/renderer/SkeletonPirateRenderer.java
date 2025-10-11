package com.bikerboys.deadbeardcopy.entity.renderer;

import com.bikerboys.deadbeardcopy.*;
import com.bikerboys.deadbeardcopy.entities.*;
import com.bikerboys.deadbeardcopy.entity.renderer.features.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import software.bernie.geckolib.renderer.*;

public class SkeletonPirateRenderer extends GeoEntityRenderer<SkeletonPirateEntity> {

    public SkeletonPirateRenderer(EntityRendererFactory.Context context) {
        super(context, ModCustomEntities.SKELETON_PIRATE);
        this.addRenderLayer(new BipedEntityHeldItemFeatureRenderer<SkeletonPirateEntity>(this));
    }

    @Override
    public Identifier getTexture(SkeletonPirateEntity animatable) {
        return Identifier.of(DeadbeardCopy.MOD_ID, "textures/entity/skeletonpirate/skeletonpirate.png");
    }
}
