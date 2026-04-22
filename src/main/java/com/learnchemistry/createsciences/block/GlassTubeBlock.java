package com.learnchemistry.createsciences.block;

import com.learnchemistry.createsciences.chemistry.container.ContainerVolume;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class GlassTubeBlock extends ChemistryContainerBlock {
    public static final Map<Direction, BooleanProperty> CONNECTION_PROPERTIES = createConnectionProperties();

    public GlassTubeBlock(Properties properties) {
        super(properties);
        BlockState state = stateDefinition.any();
        for (BooleanProperty property : CONNECTION_PROPERTIES.values()) {
            state = state.setValue(property, false);
        }
        registerDefaultState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = defaultBlockState();
        BlockGetter level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        for (Direction direction : Direction.values()) {
            state = state.setValue(CONNECTION_PROPERTIES.get(direction), canConnectTo(level.getBlockState(pos.relative(direction))));
        }
        return state;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return state.setValue(CONNECTION_PROPERTIES.get(direction), canConnectTo(neighborState));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(CONNECTION_PROPERTIES.values().toArray(BooleanProperty[]::new));
    }

    @Override
    public ContainerVolume containerVolume(BlockState state) {
        List<Direction> connections = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (state.getValue(CONNECTION_PROPERTIES.get(direction))) {
                connections.add(direction);
            }
        }
        return ContainerVolume.glassTube(connections);
    }

    public static boolean canConnectTo(BlockState state) {
        return state.getBlock() instanceof ChemistryContainerBlock;
    }

    private static Map<Direction, BooleanProperty> createConnectionProperties() {
        EnumMap<Direction, BooleanProperty> properties = new EnumMap<>(Direction.class);
        properties.put(Direction.NORTH, BooleanProperty.create("north"));
        properties.put(Direction.EAST, BooleanProperty.create("east"));
        properties.put(Direction.SOUTH, BooleanProperty.create("south"));
        properties.put(Direction.WEST, BooleanProperty.create("west"));
        properties.put(Direction.UP, BooleanProperty.create("up"));
        properties.put(Direction.DOWN, BooleanProperty.create("down"));
        return Map.copyOf(properties);
    }
}
