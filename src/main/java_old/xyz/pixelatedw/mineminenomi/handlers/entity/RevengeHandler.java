package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IHitRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.IRevengeEntity;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.ITickRevengeCheck;
import xyz.pixelatedw.mineminenomi.api.entities.revenge.RevengeMeter;

public class RevengeHandler {
   public static void checkTicks(LivingEntity entity) {
      if (entity instanceof IRevengeEntity revengeEntity) {
         if (entity.m_9236_().m_46467_() % 60L == 0L) {
            for(RevengeMeter meter : revengeEntity.getRevengeMeters()) {
               for(IRevengeCheck check : meter.getChecks()) {
                  if (check instanceof ITickRevengeCheck) {
                     ITickRevengeCheck tickCheck = (ITickRevengeCheck)check;
                     if (tickCheck.check(entity)) {
                        meter.addRevengeValueFrom(tickCheck);
                     }
                  }
               }
            }

         }
      }
   }

   public static void checkHits(LivingEntity entity, DamageSource source) {
      if (entity instanceof IRevengeEntity revengeEntity) {
         for(RevengeMeter meter : revengeEntity.getRevengeMeters()) {
            for(IRevengeCheck check : meter.getChecks()) {
               if (check instanceof IHitRevengeCheck hitCheck) {
                  if (hitCheck.check(entity, source)) {
                     meter.addRevengeValueFrom(hitCheck);
                  }
               }
            }
         }

      }
   }
}
