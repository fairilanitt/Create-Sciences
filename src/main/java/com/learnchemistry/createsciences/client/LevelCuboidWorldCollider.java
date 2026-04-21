package com.learnchemistry.createsciences.client;

import com.learnchemistry.createsciences.cuboid.CuboidVector;
import com.learnchemistry.createsciences.cuboid.CuboidWorldCollider;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public record LevelCuboidWorldCollider(ClientLevel level) implements CuboidWorldCollider {
    @Override
    public double floorYAt(CuboidVector position, double halfSize) {
        BlockPos below = BlockPos.containing(position.x(), position.y() - halfSize - 0.01, position.z());
        BlockState state = level.getBlockState(below);
        if (state.isAir()) {
            return Double.NEGATIVE_INFINITY;
        }

        VoxelShape collisionShape = state.getCollisionShape(level, below);
        if (collisionShape.isEmpty()) {
            return Double.NEGATIVE_INFINITY;
        }

        return below.getY() + collisionShape.max(Direction.Axis.Y);
    }
}
