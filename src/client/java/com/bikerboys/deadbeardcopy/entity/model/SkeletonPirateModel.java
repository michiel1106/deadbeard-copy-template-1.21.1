package com.bikerboys.deadbeardcopy.entity.model;

import com.bikerboys.deadbeardcopy.*;
import com.bikerboys.deadbeardcopy.entities.custom.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.cache.object.*;
import software.bernie.geckolib.constant.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.model.data.*;

public class SkeletonPirateModel extends GeoModel<SkeletonPirateEntity> {
    @Override
    public Identifier getModelResource(SkeletonPirateEntity skeletonPirateEntity) {
        return Identifier.of(DeadbeardCopy.MOD_ID, "geo/skeleton_pirate.geo.json");
    }

    @Override
    public Identifier getTextureResource(SkeletonPirateEntity skeletonPirateEntity) {
        return Identifier.of(DeadbeardCopy.MOD_ID, "textures/entity/skeletonpirate/skeletonpirate.png");
    }

    @Override
    public Identifier getAnimationResource(SkeletonPirateEntity skeletonPirateEntity) {
        return Identifier.of(DeadbeardCopy.MOD_ID, "animations/skeleton_pirate.animation.json");
    }




    @Override
    public void setCustomAnimations(SkeletonPirateEntity animatable, long instanceId, AnimationState<SkeletonPirateEntity> animationState) {


        GeoBone rightArm = getAnimationProcessor().getBone("ArmRight");
        GeoBone leftArm = getAnimationProcessor().getBone("ArmLeft");
        GeoBone rightLeg = getAnimationProcessor().getBone("LegRight");
        GeoBone leftLeg = getAnimationProcessor().getBone("rotation");
        GeoBone head = getAnimationProcessor().getBone("Head");

        if (head != null) {
            EntityModelData data = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(data.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(data.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }

    }


}
