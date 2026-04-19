package xyz.pixelatedw.mineminenomi.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import xyz.pixelatedw.mineminenomi.entities.projectiles.GomuPistolEntity;

public class GomuPistolRenderer extends EntityRenderer<GomuPistolEntity> {
    
    public GomuPistolRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(GomuPistolEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Entity owner = entity.getOwner();
        if (owner == null) return;

        poseStack.pushPose();
        
        Vec3 start = owner.getEyePosition(partialTicks).subtract(0, 0.4, 0);
        Vec3 end = entity.getPosition(partialTicks);
        Vec3 diff = end.subtract(start);
        
        float length = (float) diff.length();
        float yaw = (float) (Math.atan2(diff.x, diff.z) * (180 / Math.PI));
        float pitch = (float) (Math.atan2(diff.y, Math.sqrt(diff.x * diff.x + diff.z * diff.z)) * (180 / Math.PI));

        poseStack.translate(start.x - entity.getX(), start.y - entity.getY(), start.z - entity.getZ());
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(-pitch));

        VertexConsumer builder = buffer.getBuffer(RenderType.entitySolid(getTextureLocation(entity)));
        Matrix4f matrix = poseStack.last().pose();
        
        float size = 0.15F;
        
        // Drawing a simple 4-sided stretched arm
        // Top
        addVertex(matrix, builder, -size, size, 0, 0, 0, packedLight);
        addVertex(matrix, builder, -size, size, length, 0, 1, packedLight);
        addVertex(matrix, builder, size, size, length, 1, 1, packedLight);
        addVertex(matrix, builder, size, size, 0, 1, 0, packedLight);

        // Bottom
        addVertex(matrix, builder, -size, -size, 0, 0, 0, packedLight);
        addVertex(matrix, builder, size, -size, 0, 1, 0, packedLight);
        addVertex(matrix, builder, size, -size, length, 1, 1, packedLight);
        addVertex(matrix, builder, -size, -size, length, 0, 1, packedLight);

        // Left
        addVertex(matrix, builder, -size, -size, 0, 0, 0, packedLight);
        addVertex(matrix, builder, -size, -size, length, 0, 1, packedLight);
        addVertex(matrix, builder, -size, size, length, 1, 1, packedLight);
        addVertex(matrix, builder, -size, size, 0, 1, 0, packedLight);

        // Right
        addVertex(matrix, builder, size, -size, 0, 0, 0, packedLight);
        addVertex(matrix, builder, size, size, 0, 1, 0, packedLight);
        addVertex(matrix, builder, size, size, length, 1, 1, packedLight);
        addVertex(matrix, builder, size, -size, length, 0, 1, packedLight);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private void addVertex(Matrix4f matrix, VertexConsumer builder, float x, float y, float z, float u, float v, int light) {
        builder.addVertex(matrix, x, y, z)
               .setColor(255, 255, 255, 255)
               .setUv(u, v)
               .setOverlay(OverlayTexture.NO_OVERLAY)
               .setLight(light)
               .setNormal(0, 1, 0);
    }

    @Override
    public ResourceLocation getTextureLocation(GomuPistolEntity entity) {
        return ResourceLocation.withDefaultNamespace("textures/entity/steve.png");
    }
}
