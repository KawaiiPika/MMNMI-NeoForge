package xyz.pixelatedw.mineminenomi.morphs.mini;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.mini.MiniMiniAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.renderers.layers.abilities.PaperFloatLayer;

public class MiniMorphInfo extends MorphInfo {
   private static final EntityDimensions STANDING_SIZE = EntityDimensions.m_20395_(0.2F, 0.5F);
   private static final EntityDimensions CROUCHING_SIZE = EntityDimensions.m_20395_(0.2F, 0.39F);

   @OnlyIn(Dist.CLIENT)
   public <T extends LivingEntity, M extends EntityModel<T>> void addLayers(EntityRendererProvider.Context ctx, LivingEntityRenderer<T, M> renderer) {
      renderer.m_115326_(new PaperFloatLayer(ctx, renderer));
   }

   public ResourceLocation getTexture(LivingEntity entity) {
      if (entity instanceof AbstractClientPlayer player) {
         return player.m_108560_();
      } else {
         return null;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderr, PoseStack matrixStack, float partialTickTime) {
      matrixStack.m_85841_(0.25F, 0.25F, 0.25F);
   }

   @OnlyIn(Dist.CLIENT)
   public void preFirstPersonRenderCallback(LivingEntity entity, PoseStack matrixStack) {
      matrixStack.m_252880_(-0.0F, 0.35F, 0.2F);
      matrixStack.m_85841_(0.6F, 0.6F, 0.6F);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean shouldRenderLayer(RenderLayer<?, ?> layer, LivingEntity entity, PoseStack matrixStack, float partialTickTime) {
      if (!(layer instanceof ItemInHandLayer)) {
         return true;
      } else {
         boolean hasPaper = entity.m_21205_().m_41720_() == Items.f_42516_ || entity.m_21206_().m_41720_() == Items.f_42516_;
         boolean inAir = !entity.m_20096_() && entity.m_20184_().f_82480_ < (double)0.0F;
         if (hasPaper && inAir) {
            boolean hasAbility = (Boolean)AbilityCapability.get(entity).map((props) -> (MiniMiniAbility)props.getEquippedAbility((AbilityCore)MiniMiniAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
            return !hasAbility;
         } else {
            return true;
         }
      }
   }

   public Component getDisplayName() {
      return ((AbilityCore)MiniMiniAbility.INSTANCE.get()).getLocalizedName();
   }

   public float getEyeHeight(LivingEntity entity) {
      return 0.42F;
   }

   public float getShadowSize() {
      return 0.2F;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraZoom(LivingEntity entity) {
      return (double)2.5F;
   }

   @OnlyIn(Dist.CLIENT)
   public double getCameraHeight(LivingEntity entity) {
      boolean isFirstPerson = Minecraft.m_91087_().f_91066_.m_92176_() == CameraType.FIRST_PERSON;
      boolean shouldSit = entity.m_20159_() && entity.m_20202_() != null && entity.m_20202_().shouldRiderSit();
      return isFirstPerson && shouldSit ? (double)0.5F : (double)0.0F;
   }

   public Map<Pose, EntityDimensions> getSizes() {
      return ImmutableMap.builder().put(Pose.STANDING, STANDING_SIZE).put(Pose.CROUCHING, CROUCHING_SIZE).build();
   }
}
