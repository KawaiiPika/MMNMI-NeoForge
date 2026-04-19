package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import javax.annotation.Nullable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITransmisibleByProximityEffect;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class IceOniEffect extends BaseEffect implements IColorOverlayEffect, ITransmisibleByProximityEffect {
   private static final Color OVERLAY_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.5F);

   public IceOniEffect() {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#69f2f4").getRGB());
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      if (!entity.m_9236_().f_46443_) {
         int time = entity.m_21124_(this).m_19557_();
         if (time % 10 == 0) {
            if (!entity.m_21023_(MobEffects.f_19600_)) {
               entity.m_7292_(new MobEffectInstance(MobEffects.f_19600_, entity.m_21124_(this).m_19557_(), 0));
            }

            if (!entity.m_21023_((MobEffect)ModEffects.ICE_ONI_FROSTBITE.get())) {
               entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.ICE_ONI_FROSTBITE.get(), entity.m_21124_(this).m_19557_(), 0));
            }
         }

         if (time < 20) {
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.ICE_ONI_ANTIBODY.get(), 840, 0));
         }

      }
   }

   @Nullable
   public Color getBodyOverlayColor(int duration, int amplifier) {
      return null;
   }

   public Color getViewOverlayColor(int duration, int amplifier) {
      return OVERLAY_COLOR;
   }

   public boolean isTransmisibleByProximity() {
      return true;
   }

   public float poximityTransmisionDistance() {
      return 2.0F;
   }

   public boolean isLingering() {
      return true;
   }
}
