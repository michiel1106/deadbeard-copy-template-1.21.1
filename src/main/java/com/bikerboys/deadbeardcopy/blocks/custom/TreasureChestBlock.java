package com.bikerboys.deadbeardcopy.blocks.custom;

import com.bikerboys.deadbeardcopy.*;
import com.bikerboys.deadbeardcopy.blocks.*;
import com.bikerboys.deadbeardcopy.blocks.entity.*;
import com.mojang.serialization.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.state.*;
import net.minecraft.state.property.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

@SuppressWarnings("ALL")
public class TreasureChestBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public TreasureChestBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(TreasureChestBlock.FACING)) {
            case NORTH -> Block.createCuboidShape(3, 0, 4, 13, 8, 12);
            case SOUTH -> Block.createCuboidShape(3, 0, 4, 13, 8, 12);

            case EAST  -> Block.createCuboidShape(4, 0, 3, 12, 8, 13);
            case WEST  -> Block.createCuboidShape(4, 0, 3, 12, 8, 13);
            default -> Block.createCuboidShape(3, 0, 4, 13, 8, 12);
        };
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(TreasureChestBlock::new);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? validateTicker(type, ModBlockEntities.TREASURE_CHEST_BLOCK_ENTITY, TreasureChestBlockEntity::clientTick) : validateTicker(type, ModBlockEntities.TREASURE_CHEST_BLOCK_ENTITY, TreasureChestBlockEntity::Tick);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof TreasureChestBlockEntity chest) {
                boolean newState = !chest.isLidOpen();
                chest.setLidOpen(newState);
                world.addSyncedBlockEvent(pos, this, 1, newState ? 1 : 0);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TreasureChestBlockEntity(pos, state);
    }
}
