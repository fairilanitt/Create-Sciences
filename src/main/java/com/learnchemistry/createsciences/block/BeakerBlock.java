package com.learnchemistry.createsciences.block;

import com.learnchemistry.createsciences.chemistry.container.ContainerVolume;
import net.minecraft.world.level.block.state.BlockState;

public class BeakerBlock extends ChemistryContainerBlock {
    private static final ContainerVolume VOLUME = ContainerVolume.beaker();

    public BeakerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ContainerVolume containerVolume(BlockState state) {
        return VOLUME;
    }
}
