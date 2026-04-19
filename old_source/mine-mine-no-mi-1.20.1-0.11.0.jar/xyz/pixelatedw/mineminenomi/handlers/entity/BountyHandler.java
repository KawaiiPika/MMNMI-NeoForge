package xyz.pixelatedw.mineminenomi.handlers.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.helpers.BountyHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.WantedPosterPackageEntity;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class BountyHandler {
   private static HashMap<Player, double[]> cachedPositions = new HashMap();

   public static boolean redeemBounty(ServerLevel level, Player player, LivingEntity target) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
      IEntityStats targetProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
      FactionsWorldData worldData = FactionsWorldData.get();
      if (props != null && targetProps != null && props.isBountyHunter()) {
         StatChangeSource source = StatChangeSource.KILL_NPC;
         if (target instanceof Player) {
            source = StatChangeSource.KILL_PLAYER;
         }

         int updates = 0;

         for(int i = 0; i < player.m_150109_().m_6643_(); ++i) {
            ItemStack itemStack = player.m_150109_().m_8020_(i);
            if (itemStack.m_41782_()) {
               CompoundTag tag = itemStack.m_41783_().m_128469_("WPData");
               if (!tag.m_128456_()) {
                  UUID uuid = tag.m_128342_("UUID");
                  if (uuid != null) {
                     boolean isTarget = uuid.equals(target.m_20148_());
                     long bounty = worldData.getBounty(target.m_20148_());
                     boolean hasBounty = bounty > 0L;
                     if (isTarget && hasBounty) {
                        worldData.issueBounty(uuid, 0L);
                        targetProps.setBounty(0L);
                        ItemsHelper.removeItemStackFromInventory(player, itemStack);
                        if (props.alterBelly(bounty, source)) {
                           ModNetwork.sendTo(new SSyncEntityStatsPacket(player), player);
                           ++updates;
                        }
                     }
                  }
               }
            }
         }

         if (updates > 0) {
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static void dropWantedPosters(ServerLevel world) {
      int listSize = Mth.m_14045_(world.m_6907_().size() / 2, 1, 10);
      Player[] cachedPlayers = new Player[listSize];

      for(int i = 0; i < cachedPlayers.length; ++i) {
         Player player = world.m_8890_();
         if (player != null) {
            boolean alreadyCached = Arrays.stream(cachedPlayers).anyMatch((target) -> target == player);
            if (!alreadyCached) {
               double currentPosX = player.m_20185_();
               double currentPosZ = player.m_20189_();
               boolean drop = false;
               if (!cachedPositions.containsKey(player)) {
                  cachedPositions.put(player, new double[]{currentPosX, currentPosZ});
                  drop = true;
               } else {
                  double[] positions = (double[])cachedPositions.get(player);
                  double cachedPosX = positions[0];
                  double cachedPosZ = positions[1];
                  boolean flagPosX = Math.abs(currentPosX - cachedPosX) > (double)100.0F;
                  boolean flagPosZ = Math.abs(currentPosZ - cachedPosZ) > (double)100.0F;
                  if (flagPosX || flagPosZ) {
                     cachedPositions.remove(player);
                     cachedPositions.put(player, new double[]{currentPosX, currentPosZ});
                     drop = true;
                  }
               }

               if (drop) {
                  WantedPosterPackageEntity pkg = new WantedPosterPackageEntity(world);
                  pkg.m_7678_(player.m_20185_() + WyHelper.randomWithRange(-10, 10), player.m_20186_() + (double)30.0F, player.m_20189_() + WyHelper.randomWithRange(-10, 10), 0.0F, 0.0F);
                  world.m_7967_(pkg);
               }
            }
         }
      }

   }

   public static void updateEverybodysBounty(ServerLevel world) {
      for(Player player : world.m_6907_()) {
         BountyHelper.issueBountyForPlayer(player);
      }

   }

   public static void clearDropCache() {
      cachedPositions.clear();
   }
}
