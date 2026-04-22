package xyz.pixelatedw.mineminenomi.api.helpers;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.init.ModRegistries;

public class BountyHelper {
   public static final String WANTED_POSTER_TAG = "WPData";

   public static boolean canGainBounty(LivingEntity entity) {
      PlayerStats props = entity.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS);
      if (props != null && props.getBasic().identity().faction().isPresent()) {
         net.minecraft.resources.ResourceLocation loc = props.getBasic().identity().faction().get();
         Faction faction = ModRegistries.FACTIONS_REGISTRY.getEntries().stream()
                 .filter(e -> e.getId().equals(loc))
                 .map(net.neoforged.neoforge.registries.DeferredHolder::get)
                 .findFirst().orElse(null);
         if (faction != null) {
             return faction.canReceiveBounty(entity);
         }
      }
      return false;
   }

   public static boolean issueBountyForPlayer(Player player) {
      Level level = player.level();
      if (level != null && !level.isClientSide()) {
         FactionsWorldData worldData = FactionsWorldData.get();
         if (worldData == null) return false;

         PlayerStats entityStatsData = player.getData(xyz.pixelatedw.mineminenomi.init.ModDataAttachments.PLAYER_STATS);
         long bounty = entityStatsData != null ? entityStatsData.getBasic().bounty() : 0L;
         boolean canGainBounty = canGainBounty(player);

         if (canGainBounty && bounty > 1000L) {
            worldData.issueBounty(player.getUUID(), bounty);
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
         for(Player player : world.players()) {
            issueBountyForPlayer(player);
         }
      }
   }
}
