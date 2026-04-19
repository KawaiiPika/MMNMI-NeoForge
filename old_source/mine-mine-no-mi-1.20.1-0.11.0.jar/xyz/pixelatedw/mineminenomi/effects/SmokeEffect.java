package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.ITransmisibleByTouchEffect;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SmokeEffect extends BaseEffect implements ITransmisibleByTouchEffect {
   public SmokeEffect() {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#929281").getRGB());
      this.m_19472_((Attribute)ModAttributes.REGEN_RATE.get(), "7d355019-7ef9-4beb-bcba-8b2608a73380", (double)-0.25F, Operation.ADDITION);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return duration % 5 == 0;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      if (!entity.m_9236_().f_46443_) {
         int time = entity.m_21124_(this).m_19557_();
         if (time % 10 == 0) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.SMOKE_DEBUFF.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         }

      }
   }

   public boolean isTransmisibleByTouch() {
      return true;
   }

   public boolean isLingering() {
      return true;
   }
}
