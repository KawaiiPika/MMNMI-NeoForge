package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import java.util.function.Predicate;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IDisableAbilitiesEffect;
import xyz.pixelatedw.mineminenomi.data.entity.animation.AnimationCapability;
import xyz.pixelatedw.mineminenomi.data.entity.animation.IAnimationData;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SToggleAnimationPacket;

public class UnconsciousEffect extends BaseEffect implements IColorOverlayEffect, IDisableAbilitiesEffect, IBindBodyEffect, IBindHandsEffect {
   private static final Color VIEW_OVERLAY_COLOR = new Color(0.0F, 0.0F, 0.0F, 1.0F);

   public UnconsciousEffect() {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#000000").getRGB());
      this.m_19472_(Attributes.f_22279_, "0e091520-be78-40aa-9e74-22aa34f506cf", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ForgeMod.SWIM_SPEED.get(), "71ada06a-e999-4408-9d43-6f205379b52a", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "7cadbf47-441b-4cd8-b93f-4e0c1147c7c8", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22283_, "21006ee0-bf00-4ef7-90b0-d6ba8c003a4f", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ModAttributes.MINING_SPEED.get(), "7adfb66b-5442-4b9e-8a42-5b1e25c39226", (double)-1.0F, Operation.MULTIPLY_TOTAL);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return duration % 20 == 0;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      IAnimationData animationData = (IAnimationData)AnimationCapability.get(entity).orElse((Object)null);
      if (animationData != null) {
         boolean isAnimationPlaying = animationData.isAnimationPlaying(ModAnimations.UNCONSCIOUS);
         if (!isAnimationPlaying) {
            this.startAnimation(entity, entity.m_21124_(this));
         }
      }

   }

   public void startEffect(LivingEntity entity, MobEffectInstance instance) {
   }

   private void startAnimation(LivingEntity entity, MobEffectInstance instance) {
      if (!entity.m_9236_().f_46443_) {
         ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.playAnimation(entity, ModAnimations.UNCONSCIOUS, instance.m_19557_(), true), entity);
      }
   }

   public void stopEffect(LivingEntity entity) {
      ModNetwork.sendToAllTrackingAndSelf(SToggleAnimationPacket.stopAnimation(entity, ModAnimations.UNCONSCIOUS), entity);
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public Color getViewOverlayColor(int duration, int amplifier) {
      return VIEW_OVERLAY_COLOR;
   }

   public @Nullable Color getBodyOverlayColor(int duration, int amplifier) {
      return null;
   }

   public boolean isBlockingRotation() {
      return true;
   }

   public boolean isBlockingSwings() {
      return true;
   }

   public Predicate<IAbility> getDisabledAbilities() {
      return (abl) -> true;
   }
}
