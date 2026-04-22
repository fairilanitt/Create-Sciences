package com.learnchemistry.createsciences.registry;

import com.learnchemistry.createsciences.CreateSciences;
import com.learnchemistry.createsciences.item.CuboidParticleWandItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CreateSciencesItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateSciences.MOD_ID);

    public static final DeferredItem<CuboidParticleWandItem> CUBOID_PARTICLE_WAND = ITEMS.registerItem(
            "cuboid_particle_wand",
            CuboidParticleWandItem::new,
            new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<BlockItem> GAS_BURNER = ITEMS.register(
            "gas_burner",
            () -> new BlockItem(CreateSciencesBlocks.GAS_BURNER.get(), new Item.Properties())
    );

    public static final DeferredItem<BlockItem> BEAKER = ITEMS.register(
            "beaker",
            () -> new BlockItem(CreateSciencesBlocks.BEAKER.get(), new Item.Properties())
    );

    public static final DeferredItem<BlockItem> GLASS_TUBE = ITEMS.register(
            "glass_tube",
            () -> new BlockItem(CreateSciencesBlocks.GLASS_TUBE.get(), new Item.Properties())
    );

    private CreateSciencesItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
