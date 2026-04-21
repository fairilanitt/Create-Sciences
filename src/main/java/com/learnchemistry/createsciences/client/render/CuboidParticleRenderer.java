package com.learnchemistry.createsciences.client.render;

import com.learnchemistry.createsciences.cuboid.CuboidParticleColor;
import com.learnchemistry.createsciences.cuboid.CuboidParticleSnapshot;
import com.learnchemistry.createsciences.cuboid.CuboidVector;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;

public final class CuboidParticleRenderer {
    public void render(PoseStack poseStack, Camera camera, List<CuboidParticleSnapshot> particles) {
        if (particles.isEmpty()) {
            return;
        }

        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.debugFilledBox());
        Vec3 cameraPosition = camera.getPosition();

        poseStack.pushPose();
        poseStack.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);
        for (CuboidParticleSnapshot particle : particles) {
            renderParticle(poseStack, consumer, particle);
        }
        poseStack.popPose();

        bufferSource.endBatch(RenderType.debugFilledBox());
    }

    private void renderParticle(PoseStack poseStack, VertexConsumer consumer, CuboidParticleSnapshot particle) {
        CuboidVector position = particle.position();
        CuboidParticleColor color = particle.color();
        double half = particle.visualSize() * 0.5;

        LevelRenderer.addChainedFilledBoxVertices(
                poseStack,
                consumer,
                position.x() - half,
                position.y() - half,
                position.z() - half,
                position.x() + half,
                position.y() + half,
                position.z() + half,
                color.red(),
                color.green(),
                color.blue(),
                color.alpha()
        );
    }
}
