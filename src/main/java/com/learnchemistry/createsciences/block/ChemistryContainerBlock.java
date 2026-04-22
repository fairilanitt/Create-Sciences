package com.learnchemistry.createsciences.block;

import com.learnchemistry.createsciences.block.entity.ChemistryContainerBlockEntity;
import com.learnchemistry.createsciences.chemistry.container.ContainerVolume;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ChemistryContainerBlock extends Block implements EntityBlock {
    protected ChemistryContainerBlock(Properties properties) {
        super(properties);
    }

    public abstract ContainerVolume containerVolume(BlockState state);

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChemistryContainerBlockEntity(pos, state);
    }
}
