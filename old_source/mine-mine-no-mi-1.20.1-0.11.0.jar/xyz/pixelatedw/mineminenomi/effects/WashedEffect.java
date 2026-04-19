package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WashedEffect extends BaseEffect {
   public WashedEffect() {
      super(MobEffectCategory.NEUTRAL, 0);
      this.m_19472_(Attributes.f_22279_, "63f9c447-3f2d-4ac9-bf17-ea84f5352d46", (double)-0.5F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "c6344fb0-aee5-46f3-89ef-1791256de5ad", (double)-0.5F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22283_, "887875d0-c7c2-4d6d-afc7-0381d60bda6e", (double)-4.0F, Operation.ADDITION);
      this.m_19472_(Attributes.f_22281_, "957a1347-334f-4988-825b-73dd9de5b9b0", (double)-6.0F, Operation.ADDITION);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      if (!entity.m_9236_().f_46443_) {
         int time = entity.m_21124_(this).m_19557_();
         if (time % 10 == 0) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.WASHED.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         }

         if (entity.m_20070_()) {
            entity.m_21195_(this);
         }

      }
   }
}
