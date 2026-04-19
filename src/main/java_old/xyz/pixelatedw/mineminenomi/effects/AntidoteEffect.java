package xyz.pixelatedw.mineminenomi.effects;

import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class AntidoteEffect extends BaseEffect {
   public AntidoteEffect() {
      super(MobEffectCategory.BENEFICIAL, WyHelper.hexToRGB("#36be4e").getRGB());
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      if (!entity.m_9236_().f_46443_) {
         int time = entity.m_21124_(this).m_19557_();
         if (time % 10 == 0) {
            Collection<MobEffectInstance> effects = entity.m_21220_();
            Collection<MobEffect> toRemove = new ArrayList();

            for(MobEffectInstance inst : effects) {
               if (this.isImmuneTo(inst.m_19544_())) {
                  toRemove.add(inst.m_19544_());
               }
            }

            for(MobEffect eff : toRemove) {
               entity.m_21195_(eff);
            }
         }

      }
   }

   private boolean isImmuneTo(MobEffect effect) {
      return effect == MobEffects.f_19610_ || effect == MobEffects.f_19604_ || effect == MobEffects.f_19614_ || effect == MobEffects.f_19612_ || effect == MobEffects.f_19613_ || effect == ModEffects.HUNGER.get();
   }

   public boolean isLingering() {
      return true;
   }
}
