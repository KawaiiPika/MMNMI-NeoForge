package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import xyz.pixelatedw.mineminenomi.entities.TornadoEntity;
import xyz.pixelatedw.mineminenomi.models.entities.TornadoModel;

public class TornadoRenderer<T extends TornadoEntity> extends EntityRenderer<T> {
   private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/projectiles/tornado1.png"), ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/projectiles/tornado2.png"), ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/projectiles/tornado3.png")};
   private TornadoModel<TornadoEntity> model;

   protected TornadoRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.f_114477_ = 0.05F;
      this.model = new TornadoModel<TornadoEntity>(ctx.m_174023_(TornadoModel.LAYER_LOCATION));
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      float ageInTicks = (float)entity.f_19797_ + partialTicks;
      float scale = entity.getSize();
      matrixStack.m_85836_();
      matrixStack.m_85836_();
      matrixStack.m_85841_(scale, scale, scale);
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(180.0F));
      matrixStack.m_85837_((double)0.0F, -1.4, (double)0.0F);
      VertexConsumer vertex = buffer.m_6299_(RenderType.m_110473_(this.getTextureLocation(entity)));
      this.model.setupAnim(entity, 0.0F, 0.0F, ageInTicks, 0.0F, 0.0F);
      this.model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.4F);
      matrixStack.m_85836_();
      matrixStack.m_85841_(0.85F, 0.85F, 0.85F);
      matrixStack.m_85837_((double)0.0F, 0.05, (double)0.0F);
      this.model.setupAnim(entity, 0.0F, 0.0F, -ageInTicks / 2.0F, 0.0F, 0.0F);
      this.model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 0.6F);
      matrixStack.m_85849_();
      matrixStack.m_85849_();
      float spread = scale / 3.0F;
      int amount = 8;
      matrixStack.m_252880_(0.0F, 5.0F, 0.0F);
      matrixStack.m_85836_();
      matrixStack.m_252781_(Axis.f_252529_.m_252977_(180.0F));
      float rotAmount = 45.0F;
      matrixStack.m_252880_(spread, -spread, spread);

      for(int i = 0; i < 8; ++i) {
         float rot1 = (float)i * 45.0F;

         for(float j = 0.0F; j < 8.0F; ++j) {
            float rot2 = j * 45.0F;
            matrixStack.m_85836_();
            matrixStack.m_252880_(-spread, -spread, -spread);
            matrixStack.m_252781_(Axis.f_252436_.m_252977_(rot1 + (float)entity.f_19797_ * entity.getSpeed()));
            matrixStack.m_252781_(Axis.f_252393_.m_252977_(rot2 - (float)entity.f_19797_ * entity.getSpeed() / 2.0F));
            matrixStack.m_252880_(spread, spread, spread);
            ItemStack stack = new ItemStack(Blocks.f_49992_);
            Minecraft.m_91087_().m_91291_().m_269128_(stack, ItemDisplayContext.NONE, packedLight, OverlayTexture.f_118083_, matrixStack, buffer, entity.m_9236_(), entity.m_19879_());
            matrixStack.m_85849_();
         }
      }

      matrixStack.m_85849_();
      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(T entity) {
      int i = Math.min(30, entity.f_19797_ % 30);
      return TEXTURES[i / 10];
   }

   public static class Factory implements EntityRendererProvider<TornadoEntity> {
      public EntityRenderer<TornadoEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new TornadoRenderer<TornadoEntity>(ctx);
      }
   }
}
