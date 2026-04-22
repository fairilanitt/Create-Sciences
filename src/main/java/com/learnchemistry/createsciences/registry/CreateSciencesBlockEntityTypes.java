package com.learnchemistry.createsciences.registry;

import com.learnchemistry.createsciences.CreateSciences;
import com.learnchemistry.createsciences.block.entity.ChemistryContainerBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CreateSciencesBlockEntityTypes {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreateSciences.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ChemistryContainerBlockEntity>> CHEMISTRY_CONTAINER =
            BLOCK_ENTITY_TYPES.register(
                    "chemistry_container",
                    () -> BlockEntityType.Builder.of(
                            ChemistryContainerBlockEntity::new,
                            CreateSciencesBlocks.BEAKER.get(),
                            CreateSciencesBlocks.GLASS_TUBE.get()
                    ).build(null)
            );

    private CreateSciencesBlockEntityTypes() {
    }

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITY_TYPES.register(modEventBus);
    }
}
