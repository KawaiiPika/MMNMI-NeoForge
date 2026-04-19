package xyz.pixelatedw.mineminenomi.client.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import xyz.pixelatedw.mineminenomi.entities.SphereEntity;

public class SphereRenderer extends EntityRenderer<SphereEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/skyboxes/default.png"); // We'll just use a blank or dummy texture, or no texture.

    public SphereRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(SphereEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        float radius = entity.getRadius();
        if (radius <= 0.1f) return;

        poseStack.pushPose();
        
        // Translucent blue color
        int r = 0;
        int g = 255;
        int b = 255;
        int a = 60; // Semi-transparent

        // Using entityTranslucent to allow alpha blending. We don't really need a texture if we rely on vertex colors, 
        // but entityTranslucent requires one. We'll provide a dummy one or use white.
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));
        Matrix4f pose = poseStack.last().pose();

        int detailLevel = 16;
        float startU = 0.0F;
        float endU = (float)Math.PI * 2F;
        float startV = 0.0F;
        float endV = (float)Math.PI;
        float stepU = (endU - startU) / detailLevel;
        float stepV = (endV - startV) / detailLevel;

        for(int i = 0; i < detailLevel; ++i) {
            for(int j = 0; j < detailLevel; ++j) {
                float u = i * stepU + startU;
                float v = j * stepV + startV;
                float un = (i + 1) * stepU + startU;
                float vn = (j + 1) * stepV + startV;

                Vector3f p0 = parametricSphere(u, v, radius);
                Vector3f p1 = parametricSphere(u, vn, radius);
                Vector3f p2 = parametricSphere(un, v, radius);
                Vector3f p3 = parametricSphere(un, vn, radius);

                vertexPosColor(vertexConsumer, pose, p0.x(), p0.y(), p0.z(), r, g, b, a);
                vertexPosColor(vertexConsumer, pose, p2.x(), p2.y(), p2.z(), r, g, b, a);
                vertexPosColor(vertexConsumer, pose, p1.x(), p1.y(), p1.z(), r, g, b, a);

                vertexPosColor(vertexConsumer, pose, p3.x(), p3.y(), p3.z(), r, g, b, a);
                vertexPosColor(vertexConsumer, pose, p1.x(), p1.y(), p1.z(), r, g, b, a);
                vertexPosColor(vertexConsumer, pose, p2.x(), p2.y(), p2.z(), r, g, b, a);
            }
        }

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private Vector3f parametricSphere(float u, float v, float r) {
        return new Vector3f(Mth.cos(u) * Mth.sin(v) * r, Mth.cos(v) * r, Mth.sin(u) * Mth.sin(v) * r);
    }

    private void vertexPosColor(VertexConsumer buffer, Matrix4f projection, float x, float y, float z, int r, int g, int b, int a) {
        buffer.addVertex(projection, x, y, z).setColor(r, g, b, a).setUv(0, 0).setOverlay(net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY).setLight(15728880).setNormal(0, 1, 0);
    }

    @Override
    public ResourceLocation getTextureLocation(SphereEntity entity) {
        // Fallback to missingno or just use a default white texture if needed.
        return ResourceLocation.withDefaultNamespace("textures/misc/white.png"); // Vanilla white texture
    }
}
