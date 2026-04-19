package xyz.pixelatedw.mineminenomi.morphs.jiki;

import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.pixelatedw.mineminenomi.abilities.jiki.DamnedPunkAbility;
import xyz.pixelatedw.mineminenomi.abilities.jiki.PunkGibsonAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class PunkGibsonMorphInfo extends MorphInfo {
   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack matrixStack, float partialTickTime) {
      for(ModelPart part : RendererHelper.getArmPartsFrom(renderer.m_7200_(), HumanoidArm.RIGHT)) {
         part.f_104207_ = false;
      }

   }

   public boolean canRender(LivingEntity entity) {
      boolean hasDamnedPunkActive = (Boolean)AbilityCapability.get(entity).map((props) -> (DamnedPunkAbility)props.getEquippedAbility((AbilityCore)DamnedPunkAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
      return !hasDamnedPunkActive;
   }

   public Component getDisplayName() {
      return ((AbilityCore)PunkGibsonAbility.INSTANCE.get()).getLocalizedName();
   }

   @Nullable
   public ResourceLocation getTexture(LivingEntity entity) {
      return ModResources.PUNK_TEXTURE;
   }

   public boolean isPartial() {
      return true;
   }
}
