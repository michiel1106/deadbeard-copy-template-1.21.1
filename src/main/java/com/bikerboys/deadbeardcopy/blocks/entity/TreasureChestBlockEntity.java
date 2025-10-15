package com.bikerboys.deadbeardcopy.blocks.entity;

import com.bikerboys.deadbeardcopy.blocks.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.network.listener.*;
import net.minecraft.network.packet.*;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.registry.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class TreasureChestBlockEntity extends BlockEntity implements LidOpenable {
    private final ChestLidAnimator lidAnimator = new ChestLidAnimator();
    private boolean isOpen = false;

    public TreasureChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TREASURE_CHEST_BLOCK_ENTITY, pos, state);

    }

    public boolean getOpen() {
        return isOpen;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putBoolean("isopen", isOpen);

    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        setLidOpen(nbt.getBoolean("isopen"));

    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return super.toInitialChunkDataNbt(registryLookup);
    }

    @Override
    public float getAnimationProgress(float tickDelta) {
        return this.lidAnimator.getProgress(tickDelta);
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, TreasureChestBlockEntity blockEntity) {
        blockEntity.lidAnimator.step();
    }

    public static void Tick(World world, BlockPos pos, BlockState state, TreasureChestBlockEntity blockEntity) {
        world.addSyncedBlockEvent(pos, state.getBlock(), 1, blockEntity.isOpen ? 1 : 0);
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            boolean open = data == 1;
            this.isOpen = open;
            this.lidAnimator.setOpen(open);
            return true;
        }
        return super.onSyncedBlockEvent(type, data);
    }

    public boolean isLidOpen() {
        return isOpen;
    }

    public void setLidOpen(boolean open) {
        this.isOpen = open;
        this.lidAnimator.setOpen(open);
        if (getWorld() != null) {
            markDirty();
            getWorld().addSyncedBlockEvent(pos, getCachedState().getBlock(), 1, open ? 1 : 0);
        }

    }

}
