package xyz.pixelatedw.mineminenomi.renderers.entities;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.UUID;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.entities.PhysicalBodyEntity;
import xyz.pixelatedw.mineminenomi.init.ModFruits;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.models.entities.PhysicalBodyModel;

public class PhysicalBodyRenderer<T extends PhysicalBodyEntity> extends EntityRenderer<T> {
   private static final ResourceLocation SPOOKY_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/zoanmorph/yomi_skeleton.png");
   private PhysicalBodyModel model;
   private final PhysicalBodyModel slimModel;
   private final PhysicalBodyModel wideModel;

   public PhysicalBodyRenderer(EntityRendererProvider.Context ctx) {
      super(ctx);
      this.f_114477_ = 0.7F;
      this.slimModel = new PhysicalBodyModel(ctx.m_174023_(PhysicalBodyModel.SLIM_LAYER_LOCATION));
      this.wideModel = new PhysicalBodyModel(ctx.m_174023_(PhysicalBodyModel.WIDE_LAYER_LOCATION));
   }

   public void render(PhysicalBodyEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
      boolean isSlim = false;
      Player var9 = entity.getOwner();
      if (var9 instanceof AbstractClientPlayer owner) {
         isSlim = owner.m_108564_().equals("slim");
      }

      this.model = isSlim ? this.slimModel : this.wideModel;
      matrixStack.m_85836_();
      matrixStack.m_85837_((double)0.0F, (double)1.5F, (double)0.0F);
      matrixStack.m_252781_(Axis.f_252403_.m_252977_(180.0F));
      matrixStack.m_252781_(Axis.f_252436_.m_252977_(entity.m_146909_() + 180.0F));
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      this.model.f_102610_ = false;
      ResourceLocation texture = this.getTextureLocation(entity);
      VertexConsumer vertex = buffer.m_6299_(this.model.m_103119_(texture));
      this.model.m_7695_(matrixStack, vertex, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStack.m_85849_();
   }

   public ResourceLocation getTextureLocation(PhysicalBodyEntity entity) {
      Player player = entity.getOwner();
      if (player == null) {
         UUID uuid = entity.getOwnerUUID();
         return uuid != null ? DefaultPlayerSkin.m_118627_(uuid) : DefaultPlayerSkin.m_118626_();
      } else {
         boolean isSkeleton = false;
         IDevilFruit devilFruitProps = (IDevilFruit)DevilFruitCapability.get(entity.getOwner()).orElse((Object)null);
         isSkeleton = devilFruitProps != null && devilFruitProps.hasDevilFruit(ModFruits.YOMI_YOMI_NO_MI) && ((MorphInfo)ModMorphs.YOMI_SKELETON.get()).isActive(entity.getOwner());
         return isSkeleton ? SPOOKY_TEXTURE : ((AbstractClientPlayer)player).m_108560_();
      }
   }

   public static class Factory implements EntityRendererProvider<PhysicalBodyEntity> {
      public EntityRenderer<PhysicalBodyEntity> m_174009_(EntityRendererProvider.Context ctx) {
         return new PhysicalBodyRenderer<PhysicalBodyEntity>(ctx);
      }
   }
}
