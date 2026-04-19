package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import java.util.Objects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.effects.GuardingEffect;
import xyz.pixelatedw.mineminenomi.effects.InEventEffect;
import xyz.pixelatedw.mineminenomi.effects.UnconsciousEffect;

public class DeadzoneRevengeCheck extends BaseRevengeCheck implements ITickRevengeCheck {
   private final int revengeGain;
   private final float deadzone = 2.0F;
   private Vec3 pivot;

   public DeadzoneRevengeCheck(int revengeGain) {
      this.revengeGain = revengeGain;
   }

   public boolean check(LivingEntity entity) {
      if (entity instanceof Mob mob) {
         if (mob.m_5448_() != null && entity.m_21214_() != null && mob.m_5448_().equals(entity.m_21214_()) && entity.m_21215_() + 50 > entity.f_19797_) {
            return false;
         }
      }

      for(MobEffectInstance inst : entity.m_21220_()) {
         if (inst.m_19544_() instanceof InEventEffect || inst.m_19544_() instanceof UnconsciousEffect || inst.m_19544_() instanceof GuardingEffect) {
            return false;
         }
      }

      if (this.pivot == null) {
         this.pivot = entity.m_20182_();
      }

      double var10000 = this.pivot.m_82554_(entity.m_20182_());
      Objects.requireNonNull(this);
      Objects.requireNonNull(this);
      if (var10000 < (double)(2.0F * 2.0F)) {
         this.pivot = entity.m_20182_();
         return true;
      } else {
         this.pivot = entity.m_20182_();
         return false;
      }
   }

   public void resetMarkers() {
      super.resetMarkers();
      this.pivot = null;
   }

   public int revengeMeterGain() {
      return this.revengeGain;
   }
}
