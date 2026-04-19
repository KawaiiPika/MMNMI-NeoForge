package xyz.pixelatedw.mineminenomi.renderers.entities.vehicles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.entities.vehicles.StrikerEntity;
import xyz.pixelatedw.mineminenomi.models.entities.vehicles.StrikerModel;

public class StrikerRenderer extends EntityRenderer<StrikerEntity> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/striker.png");
   private final StrikerModel model;

   protected StrikerRenderer(EntityRendererProvider.Context ctx, StrikerModel model) {
      super(ctx);
      this.model = model;
   }

   public void render(StrikerEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      matrixStack.m_85836_();
      matrixStack.m_85837_((double)0.0F, (double)2.0F, (double)0.0F);
      matrixStack.m_85841_(1.4F, 1.4F, 1.4F);
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(180.0F - entityYaw));
      float hurtTime = (float)entity.getHurtTime() - partialTicks;
      if (hurtTime > 0.0F) {
         matrixStack.m_252781_(Axis.f_252403_.m_252977_(Mth.m_14031_(hurtTime) * hurtTime));
         matrixStack.m_252781_(Axis.f_252495_.m_252977_(Mth.m_14031_(hurtTime) * hurtTime));
      }

      matrixStack.m_85841_(-1.0F, -1.0F, 1.0F);
      this.model.setupAnim(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer ivertexbuilder = buffer.m_6299_(this.model.m_103119_(this.getTextureLocation(entity)));
      this.model.m_7695_(matrixStack, ivertexbuilder, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStack.m_85836_();
      matrixStack.m_85837_(-0.7, (double)-1.0F, 0.61);
      matrixStack.m_85841_(1.5F, 1.5F, 1.5F);
      Player lastRider = entity.getLastRider();
      if (lastRider != null) {
         EntityStatsCapability.get(lastRider).ifPresent((props) -> {
            if (props.getFaction().isPresent()) {
               RendererHelper.drawPlayerFactionIcon((Faction)props.getFaction().get(), entity.getCrew(), lastRider, matrixStack, buffer, packedLight);
            }

         });
      }

      matrixStack.m_85849_();
      matrixStack.m_85849_();
      super.m_7392_(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
   }

   public ResourceLocation getTextureLocation(StrikerEntity entity) {
      return TEXTURE;
   }

   public static class Factory implements EntityRendererProvider<StrikerEntity> {
      public EntityRenderer<StrikerEntity> m_174009_(EntityRendererProvider.Context ctx) {
         StrikerModel model = new StrikerModel(ctx);
         return new StrikerRenderer(ctx, model);
      }
   }
}
