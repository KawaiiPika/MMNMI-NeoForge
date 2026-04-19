package xyz.pixelatedw.mineminenomi.morphs.horu;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.horu.GanmenSeichoHormoneAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class GanmenSeichoMorphInfo extends MorphInfo {
   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack matrixStack, float partialTickTime) {
      EntityModel var6 = renderer.m_7200_();
      if (var6 instanceof HumanoidModel model) {
         model.f_102808_.f_233553_ = 4.0F;
         model.f_102808_.f_233554_ = 4.0F;
         model.f_102808_.f_233555_ = 4.0F;
         model.f_102809_.f_233553_ = 4.0F;
         model.f_102809_.f_233554_ = 4.0F;
         model.f_102809_.f_233555_ = 4.0F;
      }

   }

   public ResourceLocation getTexture(LivingEntity entity) {
      if (entity instanceof AbstractClientPlayer player) {
         return player.m_108560_();
      } else {
         return null;
      }
   }

   public Component getDisplayName() {
      return ((AbilityCore)GanmenSeichoHormoneAbility.INSTANCE.get()).getLocalizedName();
   }

   public boolean isPartial() {
      return true;
   }
}
