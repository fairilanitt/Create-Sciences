package com.learnchemistry.createsciences.client;

import com.learnchemistry.createsciences.CreateSciences;
import com.learnchemistry.createsciences.client.gui.CuboidLiquidSettingsScreen;
import com.learnchemistry.createsciences.client.render.CuboidParticleRenderer;
import com.learnchemistry.createsciences.cuboid.CuboidLiquidSettings;
import com.learnchemistry.createsciences.cuboid.CuboidParticleToolHooks;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = CreateSciences.MOD_ID, dist = Dist.CLIENT)
public final class CreateSciencesClient {
    private static final CuboidLiquidSettings LIQUID_SETTINGS = new CuboidLiquidSettings();
    private static final ClientCuboidParticleSystem PARTICLE_SYSTEM = new ClientCuboidParticleSystem(LIQUID_SETTINGS);
    private static final CuboidParticleRenderer PARTICLE_RENDERER = new CuboidParticleRenderer();

    public CreateSciencesClient() {
        CuboidParticleToolHooks.setTestingLiquidSpawner(PARTICLE_SYSTEM::spawnTestingLiquid);
        CuboidParticleToolHooks.setSettingsScreenOpener(CreateSciencesClient::openCuboidLiquidSettings);
        NeoForge.EVENT_BUS.addListener(CreateSciencesClient::onClientTick);
        NeoForge.EVENT_BUS.addListener(CreateSciencesClient::onRenderLevel);
    }

    private static void openCuboidLiquidSettings() {
        Minecraft.getInstance().setScreen(new CuboidLiquidSettingsScreen(LIQUID_SETTINGS));
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            PARTICLE_SYSTEM.clear();
            return;
        }

        PARTICLE_SYSTEM.tick(minecraft.level);
    }

    private static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            return;
        }

        PARTICLE_RENDERER.render(event.getPoseStack(), event.getCamera(), PARTICLE_SYSTEM.snapshots());
    }
}
