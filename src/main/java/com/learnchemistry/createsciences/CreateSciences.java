package com.learnchemistry.createsciences;

import com.learnchemistry.createsciences.registry.CreateSciencesCreativeTabs;
import com.learnchemistry.createsciences.registry.CreateSciencesBlockEntityTypes;
import com.learnchemistry.createsciences.registry.CreateSciencesBlocks;
import com.learnchemistry.createsciences.registry.CreateSciencesItems;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(CreateSciences.MOD_ID)
public class CreateSciences {
    public static final String MOD_ID = "create_sciences";
    public static final String MOD_NAME = "Create: Sciences";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateSciences(IEventBus modEventBus) {
        CreateSciencesBlocks.register(modEventBus);
        CreateSciencesBlockEntityTypes.register(modEventBus);
        CreateSciencesItems.register(modEventBus);
        CreateSciencesCreativeTabs.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("{} initialized.", MOD_NAME);
    }
}
