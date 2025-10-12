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

public class ZombiePirateModel extends GeoModel<ZombiePirateEntity> {
    @Override
    public Identifier getModelResource(ZombiePirateEntity zombiePirateEntity) {
        return Identifier.of(DeadbeardCopy.MOD_ID, "geo/zombie_pirate.geo.json");
    }

    @Override
    public Identifier getTextureResource(ZombiePirateEntity zombiePirateEntity) {
        return Identifier.of(DeadbeardCopy.MOD_ID, "textures/entity/zombiepirate/zombiepirate.png");
    }

    @Override
    public Identifier getAnimationResource(ZombiePirateEntity zombiePirateEntity) {
        return Identifier.of(DeadbeardCopy.MOD_ID, "animations/zombie_pirate.animation.json");
    }

    @Override
    public void setCustomAnimations(ZombiePirateEntity animatable, long instanceId, AnimationState<ZombiePirateEntity> animationState) {
        GeoBone head = getAnimationProcessor().getBone("Head");

        if (head != null) {
            EntityModelData data = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(data.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setRotY(data.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
