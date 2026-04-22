package com.learnchemistry.createsciences.block.entity;

import com.learnchemistry.createsciences.block.ChemistryContainerBlock;
import com.learnchemistry.createsciences.chemistry.container.ContainerCuboidParticleWorld;
import com.learnchemistry.createsciences.chemistry.container.ContainerChemistryState;
import com.learnchemistry.createsciences.chemistry.container.ContainerVolume;
import com.learnchemistry.createsciences.registry.CreateSciencesBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ChemistryContainerBlockEntity extends BlockEntity {
    private static final int MAX_CONTAINED_CUBOIDS = 512;

    private final ContainerChemistryState chemistryState = new ContainerChemistryState();
    private final ContainerCuboidParticleWorld cuboidWorld;

    public ChemistryContainerBlockEntity(BlockPos pos, BlockState blockState) {
        super(CreateSciencesBlockEntityTypes.CHEMISTRY_CONTAINER.get(), pos, blockState);
        cuboidWorld = new ContainerCuboidParticleWorld(volumeFor(blockState), MAX_CONTAINED_CUBOIDS);
    }

    public ContainerChemistryState chemistryState() {
        return chemistryState;
    }

    public ContainerCuboidParticleWorld cuboidWorld() {
        return cuboidWorld;
    }

    public void refreshContainerVolume(BlockState state) {
        cuboidWorld.setVolume(volumeFor(state));
    }

    private static ContainerVolume volumeFor(BlockState state) {
        if (state.getBlock() instanceof ChemistryContainerBlock containerBlock) {
            return containerBlock.containerVolume(state);
        }
        return ContainerVolume.beaker();
    }
}
