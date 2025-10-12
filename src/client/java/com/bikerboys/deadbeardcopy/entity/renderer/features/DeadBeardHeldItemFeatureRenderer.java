package com.bikerboys.deadbeardcopy.entity.renderer.features;

import net.minecraft.client.render.*;
import net.minecraft.client.render.model.json.*;
import net.minecraft.client.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;
import software.bernie.geckolib.animatable.*;
import software.bernie.geckolib.cache.object.*;
import software.bernie.geckolib.renderer.*;
import software.bernie.geckolib.renderer.layer.*;

public class DeadBeardHeldItemFeatureRenderer<T extends LivingEntity & GeoAnimatable> extends BlockAndItemGeoLayer<T> {

    public DeadBeardHeldItemFeatureRenderer(GeoRenderer<T> renderer) {
        super(renderer);
    }

    @Override
    protected @Nullable ItemStack getStackForBone(GeoBone bone, T animatable) {

        ItemStack mainhand = animatable.getMainHandStack();
        ItemStack offhand = animatable.getOffHandStack();

        if (bone.getName().equals("ArmRight") || bone.getName().equals("right_arm")) {
            return mainhand;
        }
        else if (bone.getName().equals("ArmLeft") || bone.getName().equals("left_arm")) {
            return offhand;
        }

        return ItemStack.EMPTY;
    }

    @Override
    protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack, T animatable,
                                      VertexConsumerProvider bufferSource, float partialTick, int packedLight, int packedOverlay) {

        if (stack.isEmpty()) return;

        poseStack.push();


        poseStack.multiply(RotationAxis.POSITIVE_X.rotation(-bone.getRotX()));
        poseStack.multiply(RotationAxis.POSITIVE_Z.rotation(-bone.getRotZ()));


        poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90f));

        // --- Step 3: Translate to hand position (tune these offsets to your model) ---
        if (bone.getName().equals("ArmRight") || bone.getName().equals("right_arm")) {
            poseStack.translate(0.0F, 0.15F, -0.55F);
        } else if (bone.getName().equals("ArmLeft") || bone.getName().equals("left_arm")) {
            poseStack.translate(0.0F, 0.15F, -0.55F);
        }

        // --- Step 4: Render the item in this corrected orientation ---
        super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);

        poseStack.pop();
    }


    @Override
    protected ModelTransformationMode getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
        if (bone.getName().equals("ArmRight") || bone.getName().equals("right_arm")) {
            return ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
        } else {
            return ModelTransformationMode.THIRD_PERSON_RIGHT_HAND;
        }
    }
}