package com.bikerboys.deadbeardcopy.entity.renderer.block;

import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.*;

public class TreasureChestModel {
    private final ModelPart root;
    private final ModelPart lid;

    public TreasureChestModel(ModelPart root) {
        this.root = root;
        ModelPart base = root.getChild("base");
        this.lid = base.getChild("lid");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData base = root.addChild("base",
                ModelPartBuilder.create().uv(0, 19)
                        .cuboid(-5.0F, -5.0F, -4.0F, 10.0F, 5.0F, 8.0F),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );

        base.addChild("lid",
                ModelPartBuilder.create().uv(0, 5)
                        .cuboid(-5.0F, -3.0F, -8.0F, 10.0F, 3.0F, 8.0F)
                        .uv(1, 7).cuboid(-1.0F, -1.0F, -9.0F, 2.0F, 2.0F, 1.0F),
                ModelTransform.pivot(0.0F, -5.0F, 4.0F)
        );

        return TexturedModelData.of(modelData, 48, 32);
    }

    public void setLidAngle(float angle) {
        lid.pitch = -angle;
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        root.render(matrices, vertices, light, overlay);
    }
}
