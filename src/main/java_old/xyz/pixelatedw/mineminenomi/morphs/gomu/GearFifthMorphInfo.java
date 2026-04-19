package xyz.pixelatedw.mineminenomi.morphs.gomu;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.gomu.GearFifthAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class GearFifthMorphInfo extends MorphInfo {
   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack matrixStack, float partialTickTime) {
      boolean isRunning = entity.m_20142_() && entity.m_20184_().m_82553_() > (double)0.05F;
      if (isRunning) {
         for(ModelPart part : RendererHelper.getLegPartsFrom(renderer.m_7200_(), HumanoidArm.RIGHT)) {
            part.f_104207_ = false;
         }

         for(ModelPart part : RendererHelper.getLegPartsFrom(renderer.m_7200_(), HumanoidArm.LEFT)) {
            part.f_104207_ = false;
         }
      }

   }

   public @Nullable ResourceLocation getTexture(LivingEntity entity) {
      if (entity instanceof AbstractClientPlayer player) {
         return player.m_108560_();
      } else {
         return null;
      }
   }

   public Component getDisplayName() {
      return ((AbilityCore)GearFifthAbility.INSTANCE.get()).getLocalizedName();
   }

   public boolean isPartial() {
      return true;
   }
}
