package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;

public class ReducedFallEffect extends MobEffect {
   public ReducedFallEffect() {
      super(MobEffectCategory.BENEFICIAL, 0);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      Vec3 startVec = entity.m_20182_();
      boolean blockUnder = entity.m_9236_().m_45547_(new ClipContext(startVec, startVec.m_82520_((double)0.0F, (double)-3.0F, (double)0.0F), Block.COLLIDER, Fluid.ANY, entity)).m_6662_().equals(Type.BLOCK);
      if (entity.m_20184_().f_82480_ < -1.0E-5 && !blockUnder) {
         AbilityHelper.setDeltaMovement(entity, entity.m_20184_().m_82542_((double)1.0F, 0.1, (double)1.0F));
      }

   }
}
