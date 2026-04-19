package xyz.pixelatedw.mineminenomi.quests.objectives;

import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModItems;

public class SharedKillChecks {
   public static final KillEntityObjective.ICheckKill HAS_SWORD = (player, target, source) -> {
      ItemStack heldItem = player.m_21205_();
      return ItemsHelper.isSword(heldItem);
   };
   public static final KillEntityObjective.ICheckKill HAS_BOW = (player, target, source) -> {
      ItemStack heldItem = player.m_21205_();
      return ItemsHelper.isBowOrGun(heldItem);
   };
   public static final KillEntityObjective.ICheckKill HAS_EMPTY_HAND = (player, target, source) -> {
      ItemStack heldItem = player.m_21205_();
      return heldItem.m_41619_();
   };
   public static final KillEntityObjective.ICheckKill IS_KICKING = (player, target, source) -> {
      ItemStack heldItem = player.m_21205_();
      return heldItem.m_41619_() && (Boolean)EntityStatsCapability.get(player).map((props) -> props.isBlackLeg()).orElse(false);
   };
   public static final KillEntityObjective.ICheckKill HAS_CANNON_BALL = (player, target, source) -> {
      ItemStack heldItem = player.m_21205_();
      return heldItem.m_41720_() == ModItems.CANNON_BALL.get();
   };
   public static final KillEntityObjective.ICheckKill HAS_BRALWER_HAND_CHECK;
   public static final KillEntityObjective.ICheckKill AIRBORNE_ENEMY_CHECK;
   /** @deprecated */
   @Deprecated
   public static final KillEntityObjective.ICheckKill PLAYER_RUNNING_CHECK;
   public static final KillEntityObjective.ICheckKill CRITICAL_KILL_CHECK;
   public static final KillEntityObjective.ICheckKill ON_FIRE_ENEMY_CHECK;
   public static final KillEntityObjective.ICheckKill ON_FIRE_PLAYER_CHECK;

   static {
      HAS_BRALWER_HAND_CHECK = HAS_EMPTY_HAND.or(HAS_CANNON_BALL);
      AIRBORNE_ENEMY_CHECK = (player, target, source) -> !target.m_20096_() && !target.m_20069_();
      PLAYER_RUNNING_CHECK = (player, target, source) -> player.m_20142_();
      CRITICAL_KILL_CHECK = (player, target, source) -> {
         boolean criticalFlag = player.f_19789_ > 0.0F && !player.m_20096_() && !player.m_6147_() && !player.m_20069_() && !player.m_20159_();
         return criticalFlag;
      };
      ON_FIRE_ENEMY_CHECK = (player, target, source) -> target.m_20094_() > 0;
      ON_FIRE_PLAYER_CHECK = (player, target, source) -> player.m_20094_() > 0;
   }
}
