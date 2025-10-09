package com.bikerboys.deadbeardcopy.entity.model;

import com.bikerboys.deadbeardcopy.*;
import com.bikerboys.deadbeardcopy.entities.deadbeard.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.*;
import software.bernie.geckolib.constant.*;
import software.bernie.geckolib.model.*;
import software.bernie.geckolib.model.data.*;

public class DeadBeardModel extends GeoModel<DeadBeardEntity> {


    @Override
    public Identifier getModelResource(DeadBeardEntity deadBeardEntity) {
        return Identifier.of(DeadbeardCopy.MOD_ID, "geo/deadbeard.geo.json");
    }

    @Override
    public Identifier getTextureResource(DeadBeardEntity deadBeardEntity) {
        return Identifier.of(DeadbeardCopy.MOD_ID, "textures/entity/deadbeard/deadbeard.png");
    }

    @Override
    public Identifier getAnimationResource(DeadBeardEntity deadBeardEntity) {
        return Identifier.of(DeadbeardCopy.MOD_ID, "animations/deadbeard.animation.json");
    }


    @Override
    public void setCustomAnimations(DeadBeardEntity animatable, long instanceId, AnimationState<DeadBeardEntity> animationState) {

        GeoBone head = getAnimationProcessor().getBone("Head");
        GeoBone rightArm = getAnimationProcessor().getBone("ArmRight");
        GeoBone leftArm = getAnimationProcessor().getBone("ArmLeft");
        GeoBone rightLeg = getAnimationProcessor().getBone("LegRight");
        GeoBone leftLeg = getAnimationProcessor().getBone("rotation");


        if (head != null) {
            EntityModelData data = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(data.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(data.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }

        float limbSwing = animatable.limbAnimator.getPos(animationState.getPartialTick());
        float limbSwingAmount = animatable.limbAnimator.getSpeed(animationState.getPartialTick());
        if (limbSwingAmount > 1.0F) limbSwingAmount = 1.0F;

        float walkSpeed = 1F;
        float swing = MathHelper.cos(limbSwing * walkSpeed) * 1.4F * limbSwingAmount;
        float swingOpposite = MathHelper.cos(limbSwing * walkSpeed + (float)Math.PI) * 1.4F * limbSwingAmount;



        if (!animatable.isTntBombing()) {
            if (rightArm != null) rightArm.setRotX(swing);
            if (leftArm != null) leftArm.setRotX(swingOpposite);
        }

        if (rightLeg != null) rightLeg.setRotX(swingOpposite);
        if (leftLeg != null) leftLeg.setRotX(swing);

    }






}