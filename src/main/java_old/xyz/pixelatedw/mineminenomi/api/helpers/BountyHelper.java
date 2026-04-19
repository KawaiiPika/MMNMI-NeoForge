package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;

public class BountyHelper {
   public static final String WANTED_POSTER_TAG = "WPData";

   /** @deprecated */
   @Deprecated
   public static boolean canGainBounty(LivingEntity entity) {
      return EntityStatsCapability.canReceiveBounty(entity);
   }

   public static boolean issueBountyForPlayer(Player player) {
      Level level = player.m_9236_();
      if (level != null && !level.f_46443_) {
         FactionsWorldData worldData = FactionsWorldData.get();
         Optional<IEntityStats> entityStatsData = EntityStatsCapability.get(player);
         long bounty = (Long)entityStatsData.map((props) -> props.getBounty()).orElse(0L);
         boolean canGainBounty = EntityStatsCapability.canReceiveBounty(player);
         if (canGainBounty && bounty > 1000L) {
            worldData.issueBounty(player.m_20148_(), bounty);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static void issueBountyForAllPlayers(Level world) {
      if (world instanceof ServerLevel) {
         for(Player player : world.m_6907_()) {
            issueBountyForPlayer(player);
         }

      }
   }
}
