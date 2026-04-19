package xyz.pixelatedw.mineminenomi.morphs.yomi;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;

public class YomiMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(0.6F, 1.8F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(0.6F, 1.5F);
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/yomi_skeleton.png");

   @OnlyIn(Dist.CLIENT)
   public <T extends LivingEntity, M extends EntityModel<T>> void addLayers(EntityRendererProvider.Context ctx, LivingEntityRenderer<T, M> renderer) {
      if (renderer.m_7200_() instanceof HumanoidModel) {
         this.addArmorLayer(ctx, renderer);
      }

   }

   @OnlyIn(Dist.CLIENT)
   private <T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> void addArmorLayer(EntityRendererProvider.Context ctx, LivingEntityRenderer<T, M> renderer) {
      HumanoidArmorModel<T> inner = new HumanoidArmorModel(ctx.m_174023_(ModelLayers.f_171167_));
      HumanoidArmorModel<T> outer = new HumanoidArmorModel(ctx.m_174023_(ModelLayers.f_171168_));
      renderer.m_115326_(new HumanoidArmorLayer(renderer, inner, outer, ctx.m_266367_()));
   }

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderr, PoseStack matrixStack, float partialTickTime) {
      matrixStack.m_85841_(0.98F, 0.98F, 0.98F);
   }

   public Component getDisplayName() {
      return ModI18n.MORPH_YOMI_NAME;
   }

   public ResourceLocation getTexture(LivingEntity entity) {
      return TEXTURE;
   }

   public float getEyeHeight(LivingEntity entity) {
      return 1.74F;
   }

   public float getShadowSize() {
      return 0.5F;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
