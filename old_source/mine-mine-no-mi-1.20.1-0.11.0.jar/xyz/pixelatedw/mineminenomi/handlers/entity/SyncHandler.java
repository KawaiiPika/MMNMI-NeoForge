package xyz.pixelatedw.mineminenomi.handlers.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncChallengeDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncGCDDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncHakiDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.ability.SRecalculateEyeHeightPacket;

public class SyncHandler {
   public static void joinWorldSync(Player player) {
      DevilFruitCapability.get(player).ifPresent((props) -> {
         ItemStack dfStack = new ItemStack(props.getDevilFruitItem());
         if (dfStack.m_41720_() == Blocks.f_50016_.m_5456_()) {
            props.setDevilFruit((ResourceLocation)null);
         }

      });
      ProgressionHandler.checkAllForNewUnlocks(player, false);
      AbilityCapability.get(player).ifPresent((props) -> props.setCombatBarSet(0));
      EntityStatsCapability.get(player).ifPresent((props) -> props.setCombatMode(false));
      dimensionSync(player);
   }

   public static void dimensionSync(Player player) {
      ModNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket(player), player);
      ModNetwork.sendToAllTrackingAndSelf(new SSyncDevilFruitPacket(player), player);
      ModNetwork.sendToAllTrackingAndSelf(new SSyncAbilityDataPacket(player), player);
      ModNetwork.sendToAllTrackingAndSelf(new SSyncEntityStatsPacket(player), player);
      ModNetwork.sendToAllTrackingAndSelf(new SSyncHakiDataPacket(player), player);
      ModNetwork.sendToAllTrackingAndSelf(new SSyncQuestDataPacket(player), player);
      ModNetwork.sendTo(new SSyncChallengeDataPacket(player), player);
      ModNetwork.sendTo(new SSyncGCDDataPacket(player), player);
      MinecraftForge.EVENT_BUS.post(new EntityEvent.Size(player, player.m_20089_(), player.m_6972_(player.m_20089_()), player.m_20206_()));
      ModNetwork.sendToAllTrackingAndSelf(new SRecalculateEyeHeightPacket(player.m_19879_()), player);
   }

   public static void trackingSync(LivingEntity living, Player target) {
      ModNetwork.sendTo(new SSyncEntityStatsPacket(living), target);
      ModNetwork.sendTo(new SSyncDevilFruitPacket(living), target);
      ModNetwork.sendTo(new SSyncAbilityDataPacket(living), target);
      ModNetwork.sendTo(new SSyncHakiDataPacket(living), target);
   }
}
