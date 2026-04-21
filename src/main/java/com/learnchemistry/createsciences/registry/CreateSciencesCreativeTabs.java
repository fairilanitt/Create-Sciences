package com.learnchemistry.createsciences.registry;

import com.learnchemistry.createsciences.CreateSciences;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CreateSciencesCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateSciences.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = CREATIVE_TABS.register(
            "main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.create_sciences"))
                    .icon(() -> CreateSciencesItems.CUBOID_PARTICLE_WAND.get().getDefaultInstance())
                    .displayItems((parameters, output) -> output.accept(CreateSciencesItems.CUBOID_PARTICLE_WAND.get()))
                    .build()
    );

    private CreateSciencesCreativeTabs() {
    }

    public static void register(IEventBus modEventBus) {
        CREATIVE_TABS.register(modEventBus);
    }
}
