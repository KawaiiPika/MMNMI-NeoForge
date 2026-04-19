package xyz.pixelatedw.mineminenomi.morphs.bara;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.bara.BaraSplitAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class BaraSplitMorphInfo extends MorphInfo {
   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack poseStack, float partialTickTime) {
      for(ModelPart part : RendererHelper.getLegPartsFrom(renderer.m_7200_(), HumanoidArm.RIGHT)) {
         part.f_104207_ = false;
      }

      for(ModelPart part : RendererHelper.getLegPartsFrom(renderer.m_7200_(), HumanoidArm.LEFT)) {
         part.f_104207_ = false;
      }

   }

   public Component getDisplayName() {
      return ((AbilityCore)BaraSplitAbility.INSTANCE.get()).getLocalizedName();
   }

   public boolean isPartial() {
      return true;
   }
}
