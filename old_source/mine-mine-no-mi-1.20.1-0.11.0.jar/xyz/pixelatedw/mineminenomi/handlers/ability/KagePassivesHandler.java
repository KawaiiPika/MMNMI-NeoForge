package xyz.pixelatedw.mineminenomi.handlers.ability;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class KagePassivesHandler {
   public static void tryBurningInSun(LivingEntity entity) {
      boolean hasShadow = (Boolean)EntityStatsCapability.get(entity).map((props) -> props.hasShadow()).orElse(true);
      if (!hasShadow) {
         if (entity.m_9236_().m_46461_()) {
            boolean isHoldingUmbrella = entity.m_21205_().m_41720_().equals(ModWeapons.UMBRELLA.get()) || entity.m_21206_().m_41720_().equals(ModWeapons.UMBRELLA.get());
            if (!isHoldingUmbrella) {
               BlockPos pos = BlockPos.m_274561_(entity.m_20185_(), entity.m_20188_(), entity.m_20189_());
               if (entity.m_9236_().m_45527_(pos)) {
                  entity.m_20254_(2);
                  if (entity.f_19797_ % 10 == 0) {
                     entity.m_6469_(ModDamageSources.getInstance().sunIncineration(), 5.0F);
                  }
               }

            }
         }
      }
   }
}
