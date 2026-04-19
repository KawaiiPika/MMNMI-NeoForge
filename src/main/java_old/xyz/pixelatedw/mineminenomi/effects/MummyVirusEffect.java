package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import javax.annotation.Nullable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITransmisibleByProximityEffect;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class MummyVirusEffect extends BaseEffect implements IColorOverlayEffect, ITransmisibleByProximityEffect {
   private static final Color OVERLAY_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.5F);

   public MummyVirusEffect() {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#beb936").getRGB());
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      if (!entity.m_9236_().f_46443_) {
         int time = entity.m_21124_(this).m_19557_();
         if (time % 10 == 0) {
            if (!entity.m_21023_((MobEffect)ModEffects.HUNGER.get())) {
               entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.HUNGER.get(), 20, 0));
            }

            if (!entity.m_21023_((MobEffect)ModEffects.BLEEDING.get())) {
               entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.BLEEDING.get(), 20, 0));
            }
         }

         if (time < 20) {
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MUMMY_VIRUS_ANTIBODY.get(), 840, 0));
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
