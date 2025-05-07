package com.gmail.guitaekm.enderlakes;

import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.registry.tag.TagKey;

class SuspiciousFluidBlock extends FluidBlock {
    public SuspiciousFluidBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }

    //@Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext) {
            Entity contextEntity = entityShapeContext.getEntity();
            Vec3d targetPosition = new Vec3d(0.0, 128.0, 0.0);
            if (contextEntity == null) {
                return getFluidState(state).getShape(world, pos);
            }
            if (contextEntity.getType().isIn(Enderlakes.TELEPORTED_BY_SUSPICIOUS_LAKE)) {
                contextEntity.speed = 0;
                contextEntity.refreshPositionAndAngles(targetPosition, 0.0f, 0.0f);
                if (contextEntity instanceof EnderPearlEntity enderPearl) {
                    if (enderPearl.getWorld() instanceof ServerWorld serverWorld) {
                        Entity owner = enderPearl.getOwner();
                        if (owner == null) {
                            return getFluidState(state).getShape(world, pos);
                        }
                        owner.teleportTo(new TeleportTarget(serverWorld, targetPosition, new Vec3d(0, 0, 0), owner.getYaw(), owner.getPitch(), TeleportTarget.NO_OP));
                        enderPearl.kill();
                    }
                }
            }
        }
        return getFluidState(state).getShape(world, pos);
    }

    @Override
    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override public boolean receiveNeighborFluids(World world, BlockPos pos, BlockState state) {
        if (world.getFluidState(pos).isIn(Enderlakes.SUSPICIOUS_LIQUID)) {
            boolean bl = world.getBlockState(pos.down()).isOf(Blocks.BEDROCK);

            for (Direction direction : FLOW_DIRECTIONS) {
                BlockPos blockPos = pos.offset(direction.getOpposite());
                if (world.getFluidState(blockPos).isIn(FluidTags.WATER) && !world.getFluidState(blockPos).isIn(Enderlakes.SUSPICIOUS_LIQUID)) {
                    Block block = Blocks.END_STONE;
                    world.setBlockState(pos, block.getDefaultState());
                    this.playExtinguishSound(world, pos);
                    return false;
                }
                if (world.getFluidState(blockPos).isIn(FluidTags.LAVA)) {
                    Block block = bl ? Blocks.OBSIDIAN : Blocks.END_STONE;
                    world.setBlockState(pos, block.getDefaultState());
                    this.playExtinguishSound(world, pos);
                    return false;
                }
            }
            return true;
        }
        return super.receiveNeighborFluids(world, pos, state);
    }
}
