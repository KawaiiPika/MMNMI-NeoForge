package xyz.pixelatedw.mineminenomi.handlers.world;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.api.helpers.StructuresHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class StructuresHandler {
   public static void tryLowerLoyaltyFromStealing(Player player, AbstractContainerMenu container) {
      if (!player.m_9236_().f_46443_ && !player.m_7500_() && !player.m_5833_() && !player.m_21023_((MobEffect)ModEffects.SILENT.get())) {
         if (container instanceof ChestMenu) {
            IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
            if (props != null) {
               ServerPlayer serverPlayer = (ServerPlayer)player;
               boolean isStealing = false;
               boolean isInsideMarineStructure = StructuresHelper.isInsideMarineStructure(serverPlayer);
               boolean isInsideRevoStructure = StructuresHelper.isInsideRevoStructure(serverPlayer);
               if (isInsideMarineStructure) {
                  isStealing |= props.isMarine() && !props.hasRank(MarineRank.CAPTAIN);
                  isStealing |= props.isBountyHunter();
               } else if (isInsideRevoStructure) {
                  isStealing |= props.isRevolutionary();
               }

               boolean isInside = isInsideMarineStructure || isInsideRevoStructure;
               if (isStealing) {
                  props.alterLoyalty((double)-0.25F, StatChangeSource.NATURAL);
                  ModNetwork.sendTo(new SSyncEntityStatsPacket(player, props), player);
               } else if (isInside) {
                  Predicate<Mob> canNotify = (target) -> {
                     IEntityStats targetProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
                     if (targetProps == null) {
                        return false;
                     } else if (isInsideMarineStructure && targetProps.isMarine()) {
                        return true;
                     } else {
                        return isInsideRevoStructure && targetProps.isRevolutionary();
                     }
                  };
                  double range = player.m_9236_().m_46791_() == Difficulty.HARD ? (double)64.0F : (double)32.0F;
                  List<Mob> list = (List)player.m_9236_().m_45976_(Mob.class, player.m_20191_().m_82400_(range)).stream().filter(canNotify).collect(Collectors.toList());
                  if (list.size() > 0) {
                     list.stream().forEach((entity) -> entity.m_6710_(player));
                  }
               }

            }
         }
      }
   }
}
