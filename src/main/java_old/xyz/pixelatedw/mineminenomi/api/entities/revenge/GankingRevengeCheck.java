package xyz.pixelatedw.mineminenomi.api.entities.revenge;

import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;

public class GankingRevengeCheck extends BaseRevengeCheck implements ITickRevengeCheck {
   private final int revengeGain;
   private final float gankRadius;

   public GankingRevengeCheck(int revengeGain, float gankRadius) {
      this.revengeGain = revengeGain;
      this.gankRadius = gankRadius;
   }

   public boolean check(LivingEntity entity) {
      List<LivingEntity> list = TargetHelper.<LivingEntity>getEntitiesInArea(entity.m_9236_(), entity, (double)this.gankRadius, (TargetPredicate)null, LivingEntity.class);
      return list.size() > 1;
   }

   public int revengeMeterGain() {
      return this.revengeGain;
   }
}
