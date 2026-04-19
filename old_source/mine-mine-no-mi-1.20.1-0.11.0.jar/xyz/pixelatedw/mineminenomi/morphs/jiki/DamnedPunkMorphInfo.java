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
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.helpers.RendererHelper;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class DamnedPunkMorphInfo extends MorphInfo {
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/damned_punk.png");

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack matrixStack, float partialTickTime) {
      for(ModelPart part : RendererHelper.getArmPartsFrom(renderer.m_7200_(), HumanoidArm.RIGHT)) {
         part.f_104207_ = false;
      }

   }

   public Component getDisplayName() {
      return ((AbilityCore)DamnedPunkAbility.INSTANCE.get()).getLocalizedName();
   }

   @Nullable
   public ResourceLocation getTexture(LivingEntity entity) {
      return TEXTURE;
   }

   public boolean isPartial() {
      return true;
   }
}
