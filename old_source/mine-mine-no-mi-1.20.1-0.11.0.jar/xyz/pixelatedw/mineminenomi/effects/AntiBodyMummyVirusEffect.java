package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class AntiBodyMummyVirusEffect extends BaseEffect {
   public AntiBodyMummyVirusEffect() {
      super(MobEffectCategory.BENEFICIAL, WyHelper.hexToRGB("#7d7a2a").getRGB());
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         int time = entity.m_21124_(this).m_19557_();
         if (time % 10 == 0) {
            entity.m_21195_((MobEffect)ModEffects.MUMMY_VIRUS.get());
         }

      }
   }

   public boolean isLingering() {
      return true;
   }
}
