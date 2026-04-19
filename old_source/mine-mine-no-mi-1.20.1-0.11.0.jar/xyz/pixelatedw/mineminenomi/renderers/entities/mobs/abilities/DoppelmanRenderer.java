package xyz.pixelatedw.mineminenomi.renderers.entities.mobs.abilities;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.entities.mobs.abilities.DoppelmanEntity;
import xyz.pixelatedw.mineminenomi.renderers.entities.mobs.CloneHumanoidRenderer;

public class DoppelmanRenderer extends CloneHumanoidRenderer<DoppelmanEntity, PlayerModel<DoppelmanEntity>> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/doppelman.png");

   public DoppelmanRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
   }

   public void scale(DoppelmanEntity entity, PoseStack poseStack, float partialTickTime) {
      float scale = Math.max(1.0F, 1.0F + (float)entity.getShadows() / 7.0F);
      poseStack.m_85841_(scale, scale, scale);
   }

   public ResourceLocation getTextureLocation(DoppelmanEntity entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<DoppelmanEntity> {
      public EntityRenderer<DoppelmanEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new DoppelmanRenderer(ctx);
      }
   }
}
