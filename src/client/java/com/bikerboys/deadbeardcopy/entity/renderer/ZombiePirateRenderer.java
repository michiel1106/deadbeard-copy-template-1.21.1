package com.bikerboys.deadbeardcopy.entity.renderer;

import com.bikerboys.deadbeardcopy.*;
import com.bikerboys.deadbeardcopy.entities.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import software.bernie.geckolib.renderer.*;

public class ZombiePirateRenderer extends GeoEntityRenderer<ZombiePirateEntity> {
    public ZombiePirateRenderer(EntityRendererFactory.Context context) {
        super(context, ModCustomEntities.ZOMBIE_PIRATE);
    }


    @Override
    public Identifier getTexture(ZombiePirateEntity animatable) {
        return Identifier.of(DeadbeardCopy.MOD_ID, "textures/entity/zombiepirate/zombiepirate.png");
    }
}
