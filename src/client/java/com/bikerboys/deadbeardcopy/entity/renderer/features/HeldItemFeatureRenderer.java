package com.bikerboys.deadbeardcopy.entity.renderer.features;

import net.minecraft.block.*;
import net.minecraft.client.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.cache.object.*;
import software.bernie.geckolib.renderer.*;
import software.bernie.geckolib.renderer.layer.*;

public class HeldItemFeatureRenderer<T extends LivingEntity & GeoAnimatable> extends BlockAndItemGeoLayer<T> {

    public HeldItemFeatureRenderer(GeoRenderer<T> renderer) {
        super(renderer);
    }

    @Override
    protected @Nullable ItemStack getStackForBone(GeoBone bone, T animatable) {
        // We manually handle both hands in renderStackForBone()
        return ItemStack.EMPTY;
    }

    @Override
    protected void renderStackForBone(MatrixStack matrices, GeoBone bone, ItemStack stack, T animatable,
                                      VertexConsumerProvider vertexConsumers, float partialTick, int light, int overlay) {

        ItemStack mainhand = animatable.getMainHandStack();
        ItemStack offhand = animatable.getOffHandStack();

        matrices.push();

        if (bone.getName().equals("ArmRight") || bone.getName().equals("right_arm")) {
            renderHeldItem(matrices, vertexConsumers, animatable, mainhand, false, light, overlay);
        }
        else if (bone.getName().equals("ArmLeft") || bone.getName().equals("left_arm")) {
            renderHeldItem(matrices, vertexConsumers, animatable, offhand, true, light, overlay);
        }

        matrices.pop();
    }

    private void renderHeldItem(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T animatable,
                                ItemStack stack, boolean leftHanded, int light, int overlay) {
        if (stack.isEmpty()) return;

        matrices.push();

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));

        float baseOffset = (leftHanded ? -1 : 1) / 16.0F;
        float sideOffset = (leftHanded ? 0.1f : -0.1f);
        matrices.translate(baseOffset + sideOffset, 0.125F, 0.625F);

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                animatable,
                stack,
                leftHanded ? ModelTransformationMode.THIRD_PERSON_LEFT_HAND : ModelTransformationMode.THIRD_PERSON_RIGHT_HAND,
                leftHanded,
                matrices,
                vertexConsumers,
                animatable.getWorld(),
                light,
                overlay,
                animatable.getId()
        );

        matrices.pop();
    }
}