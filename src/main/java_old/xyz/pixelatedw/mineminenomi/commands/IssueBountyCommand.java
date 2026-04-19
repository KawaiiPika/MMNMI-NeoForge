package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.nodes.PermissionDynamicContext;
import xyz.pixelatedw.mineminenomi.api.events.entity.IssueBountyEvent;
import xyz.pixelatedw.mineminenomi.api.factions.MarineRank;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nCommands;

public class IssueBountyCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = Commands.m_82127_("issuebounty");
      builder.then(Commands.m_82129_("target", EntityArgument.m_91466_()).then(Commands.m_82129_("bounty", LongArgumentType.longArg()).executes((context) -> issueBounty(context, EntityArgument.m_91474_(context, "target"), LongArgumentType.getLong(context, "bounty")))));
      return builder;
   }

   private static int issueBounty(CommandContext<CommandSourceStack> context, ServerPlayer player, long bounty) {
      try {
         ServerPlayer sender = ((CommandSourceStack)context.getSource()).m_81375_();
         IEntityStats propz = (IEntityStats)EntityStatsCapability.get(sender).orElse((Object)null);
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         if (propz == null || props == null) {
            return -1;
         }

         IssueBountyEvent event = new IssueBountyEvent(player, bounty, sender);
         MinecraftForge.EVENT_BUS.post(event);
         boolean hasPermission = (Boolean)PermissionAPI.getPermission(sender, ModPermissions.ISSUE_BOUNTY_COMMAND, new PermissionDynamicContext[0]);
         if (hasPermission || propz.isMarine() && (event.getResult() == Result.DEFAULT && propz.hasRank(MarineRank.CAPTAIN) || event.getResult() == Result.ALLOW)) {
            if (!props.isPirate() && !props.isBandit() && !props.isRevolutionary()) {
               player.m_213846_(ModI18nCommands.ISSUEBOUNTY_TARGET_REQUIREMENTS);
            } else {
               if (bounty <= props.getBounty()) {
                  player.m_213846_(ModI18nCommands.ISSUEBOUNTY_ONLY_UP);
                  return 1;
               }

               long costOfAdding = bounty - props.getBounty();
               if (propz.getBelly() <= costOfAdding) {
                  player.m_213846_(ModI18nCommands.ISSUEBOUNTY_NOT_ENOUGH_BELLY);
                  return 1;
               }

               propz.setBelly(propz.getBelly() - costOfAdding);
               props.setBounty(bounty);
            }
         } else {
            player.m_213846_(ModI18nCommands.ISSUEBOUNTY_REQUIREMENTS);
         }
      } catch (CommandSyntaxException e) {
         e.printStackTrace();
      }

      return 1;
   }
}
