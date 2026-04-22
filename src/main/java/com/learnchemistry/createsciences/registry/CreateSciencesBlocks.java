package com.learnchemistry.createsciences.registry;

import com.learnchemistry.createsciences.CreateSciences;
import com.learnchemistry.createsciences.block.BeakerBlock;
import com.learnchemistry.createsciences.block.GasBurnerBlock;
import com.learnchemistry.createsciences.block.GlassTubeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CreateSciencesBlocks {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CreateSciences.MOD_ID);

    public static final DeferredBlock<GasBurnerBlock> GAS_BURNER = BLOCKS.registerBlock(
            "gas_burner",
            GasBurnerBlock::new,
            BlockBehaviour.Properties.of().strength(2.0F, 6.0F)
    );

    public static final DeferredBlock<BeakerBlock> BEAKER = BLOCKS.registerBlock(
            "beaker",
            BeakerBlock::new,
            BlockBehaviour.Properties.of().strength(0.3F).noOcclusion()
    );

    public static final DeferredBlock<GlassTubeBlock> GLASS_TUBE = BLOCKS.registerBlock(
            "glass_tube",
            GlassTubeBlock::new,
            BlockBehaviour.Properties.of().strength(0.3F).noOcclusion()
    );

    private CreateSciencesBlocks() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
