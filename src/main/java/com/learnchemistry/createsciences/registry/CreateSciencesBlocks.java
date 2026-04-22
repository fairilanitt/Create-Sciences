package com.learnchemistry.createsciences.registry;

import com.learnchemistry.createsciences.CreateSciences;
import com.learnchemistry.createsciences.block.GasBurnerBlock;
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

    private CreateSciencesBlocks() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
