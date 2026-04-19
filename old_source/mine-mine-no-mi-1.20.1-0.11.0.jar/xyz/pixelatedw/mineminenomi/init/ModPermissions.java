package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
import net.minecraftforge.server.permission.nodes.PermissionDynamicContext;
import net.minecraftforge.server.permission.nodes.PermissionDynamicContextKey;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;

public class ModPermissions {
   public static final PermissionNode<Boolean> REMOVE_DF_COMMAND = createBoolNode("command.removedf", 2);
   public static final PermissionNode<Boolean> REMOVE_DF_COMMAND_SELF = createBoolNode("command.removedf.self", 2);
   public static final PermissionNode<Boolean> ABILITY_PROTECTION_COMMAND = createBoolNode("command.ability_protection", 3);
   public static final PermissionNode<Boolean> CHECK_FRUITS_COMMAND_LIST = createBoolNode("command.check_fruits.list", 2);
   public static final PermissionNode<Boolean> CHECK_FRUITS_COMMAND_HISTORY = createBoolNode("command.check_fruits.history", 2);
   public static final PermissionNode<Boolean> CHECK_PLAYER_COMMAND = createBoolNode("command.check_player", 2);
   public static final PermissionNode<Boolean> ISSUE_BOUNTY_COMMAND = createBoolNode("command.issue_bounty", 2);
   public static final PermissionNode<Boolean> ABILITY_COMMAND = createBoolNode("command.ability", 2);
   public static final PermissionNode<Boolean> ABILITY_COMMAND_RESET_COOLDOWN = createBoolNode("command.ability.reset_cooldown", 2);
   public static final PermissionNode<Boolean> BELLY_COMMAND = createBoolNode("command.belly", 2);
   public static final PermissionNode<Boolean> EXTOL_COMMAND = createBoolNode("command.extol", 2);
   public static final PermissionNode<Boolean> BOUNTY_COMMAND = createBoolNode("command.bounty", 2);
   public static final PermissionNode<Boolean> DORIKI_COMMAND = createBoolNode("command.doriki", 2);
   public static final PermissionNode<Boolean> HAKIEXP_COMMAND = createBoolNode("command.hakiexp", 2);
   public static final PermissionNode<Boolean> LOYALTY_COMMAND = createBoolNode("command.loyalty", 2);
   public static final PermissionNode<Boolean> QUEST_COMMAND = createBoolNode("command.quest", 2);
   public static final PermissionNode<Boolean> CHALLENGE_COMMAND = createBoolNode("command.challenge", 2);
   public static final PermissionNode<Boolean> CHANGE_CHARACTER_COMMAND = createBoolNode("command.change_character", 2);
   public static final PermissionNode<Boolean> EVENTS_COMMAND = createBoolNode("command.events", 2);
   public static final PermissionNode<Boolean> POINTS_COMMAND = createBoolNode("command.points", 2);
   public static final PermissionNode<Boolean> BUSTER_CALL_ITEM = createBoolNode("item.buster_call", 2);

   public static void registerPermissions(PermissionGatherEvent.Nodes event) {
      event.addNodes(new PermissionNode[]{REMOVE_DF_COMMAND});
      event.addNodes(new PermissionNode[]{REMOVE_DF_COMMAND_SELF});
      event.addNodes(new PermissionNode[]{ABILITY_PROTECTION_COMMAND});
      event.addNodes(new PermissionNode[]{CHECK_FRUITS_COMMAND_LIST});
      event.addNodes(new PermissionNode[]{CHECK_FRUITS_COMMAND_HISTORY});
      event.addNodes(new PermissionNode[]{CHECK_PLAYER_COMMAND});
      event.addNodes(new PermissionNode[]{ISSUE_BOUNTY_COMMAND});
      event.addNodes(new PermissionNode[]{ABILITY_COMMAND});
      event.addNodes(new PermissionNode[]{ABILITY_COMMAND_RESET_COOLDOWN});
      event.addNodes(new PermissionNode[]{BELLY_COMMAND});
      event.addNodes(new PermissionNode[]{EXTOL_COMMAND});
      event.addNodes(new PermissionNode[]{BOUNTY_COMMAND});
      event.addNodes(new PermissionNode[]{DORIKI_COMMAND});
      event.addNodes(new PermissionNode[]{HAKIEXP_COMMAND});
      event.addNodes(new PermissionNode[]{LOYALTY_COMMAND});
      event.addNodes(new PermissionNode[]{QUEST_COMMAND});
      event.addNodes(new PermissionNode[]{CHALLENGE_COMMAND});
      event.addNodes(new PermissionNode[]{CHANGE_CHARACTER_COMMAND});
      event.addNodes(new PermissionNode[]{EVENTS_COMMAND});
      event.addNodes(new PermissionNode[]{POINTS_COMMAND});
      event.addNodes(new PermissionNode[]{BUSTER_CALL_ITEM});
   }

   public static boolean hasPermission(Player player, PermissionNode<Boolean> permission) {
      if (player instanceof ServerPlayer serverPlayer) {
         return !ServerConfig.hasPermissionsEnabled() ? false : (Boolean)PermissionAPI.getPermission(serverPlayer, permission, new PermissionDynamicContext[0]);
      } else {
         return false;
      }
   }

   private static PermissionNode<Boolean> createBoolNode(String id, int defaultLevel) {
      return new PermissionNode("mineminenomi", id, PermissionTypes.BOOLEAN, (player, uuid, contexts) -> player != null && player.m_20310_(defaultLevel), new PermissionDynamicContextKey[0]);
   }
}
