package xyz.pixelatedw.mineminenomi.morphs.bane;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.bane.SpringHopperAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;

public class SpringLegsMorphInfo extends MorphInfo {
   private static final ResourceLocation SPRING_LEGS_TEXTURE = ResourceLocation.fromNamespaceAndPath("mineminenomi", "textures/models/morphs/spring_legs.png");

   @OnlyIn(Dist.CLIENT)
   public void preRenderCallback(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, PoseStack matrixStack, float partialTickTime) {
      EntityModel var6 = renderer.m_7200_();
      if (var6 instanceof HumanoidModel model) {
         model.f_102813_.f_104207_ = false;
         model.f_102814_.f_104207_ = false;
      }

   }

   public @Nullable ResourceLocation getTexture(LivingEntity entity) {
      return SPRING_LEGS_TEXTURE;
   }

   public Component getDisplayName() {
      return ((AbilityCore)SpringHopperAbility.INSTANCE.get()).getLocalizedName();
   }

   public boolean isPartial() {
      return true;
   }
}
