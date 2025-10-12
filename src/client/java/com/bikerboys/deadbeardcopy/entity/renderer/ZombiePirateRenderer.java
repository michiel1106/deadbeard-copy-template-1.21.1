package com.bikerboys.deadbeardcopy.entity.renderer;

import com.bikerboys.deadbeardcopy.*;
import com.bikerboys.deadbeardcopy.entities.*;
import com.bikerboys.deadbeardcopy.entities.custom.*;
import com.bikerboys.deadbeardcopy.entity.renderer.features.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.util.*;
import software.bernie.geckolib.renderer.*;

public class ZombiePirateRenderer extends GeoEntityRenderer<ZombiePirateEntity> {
    public ZombiePirateRenderer(EntityRendererFactory.Context context) {
        super(context, ModCustomEntities.ZOMBIE_PIRATE);
        this.addRenderLayer(new BipedEntityHeldItemFeatureRenderer<ZombiePirateEntity>(this));
    }


    @Override
    public Identifier getTexture(ZombiePirateEntity animatable) {
        return Identifier.of(DeadbeardCopy.MOD_ID, "textures/entity/zombiepirate/zombiepirate.png");
    }
}
