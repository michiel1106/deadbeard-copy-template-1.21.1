package com.bikerboys.deadbeardcopy.entity.model;

import com.bikerboys.deadbeardcopy.*;
import com.bikerboys.deadbeardcopy.entities.*;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.*;
import software.bernie.geckolib.constant.*;
import software.bernie.geckolib.constant.dataticket.*;
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

        if (head != null) {
            EntityModelData entityModelData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityModelData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(entityModelData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }

    }




}