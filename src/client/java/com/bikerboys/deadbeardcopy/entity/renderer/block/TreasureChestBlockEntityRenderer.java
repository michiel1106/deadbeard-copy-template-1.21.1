package com.bikerboys.deadbeardcopy.entity.renderer.block;

import com.bikerboys.deadbeardcopy.*;
import com.bikerboys.deadbeardcopy.blocks.*;
import com.bikerboys.deadbeardcopy.blocks.custom.*;
import com.bikerboys.deadbeardcopy.blocks.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class TreasureChestBlockEntityRenderer implements BlockEntityRenderer<TreasureChestBlockEntity> {

    private static final Identifier TEXTURE =
            Identifier.of("deadbeardcopy", "textures/entity/treasure_chest.png");

    private final TreasureChestModel model;

    public TreasureChestBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.model = new TreasureChestModel(ctx.getLayerModelPart(DeadbeardCopyClient.TREASURE_CHEST));
    }

    @Override
    public void render(TreasureChestBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        boolean bl = world != null;
        BlockState blockState = bl ? entity.getCachedState() : (BlockState) ModBlocks.TREASURE_CHEST.getDefaultState().with(ChestBlock.FACING, Direction.SOUTH);

        matrices.push();
        matrices.translate(0.5, 1.5, 0.5);
        matrices.scale(1.0F, -1.0F, -1.0F);
        float f = ((Direction)blockState.get(TreasureChestBlock.FACING)).asRotation();

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(f));

        float openProgress = entity.getAnimationProgress(tickDelta);
        float angle = openProgress * ((float) Math.PI / 2F);

        model.setLidAngle(angle);

        VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
        model.render(matrices, consumer, light, overlay);
        matrices.pop();
    }
}