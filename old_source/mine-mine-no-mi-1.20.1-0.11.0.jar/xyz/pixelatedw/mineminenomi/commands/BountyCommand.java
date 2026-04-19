package xyz.pixelatedw.mineminenomi.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class BountyCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("bounty").requires(Requires.hasPermission(ModPermissions.BOUNTY_COMMAND));
      builder.then(((RequiredArgumentBuilder)Commands.m_82129_("amount", LongArgumentType.longArg(-100000000000L, 100000000000L)).executes((context) -> alterBounty(context, LongArgumentType.getLong(context, "amount"), getDefaultCollection(context)))).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((context) -> alterBounty(context, LongArgumentType.getLong(context, "amount"), EntityArgument.m_91477_(context, "targets")))));
      return builder;
   }

   private static Collection<ServerPlayer> getDefaultCollection(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      return Lists.newArrayList(new ServerPlayer[]{((CommandSourceStack)context.getSource()).m_81375_()});
   }

   private static int alterBounty(CommandContext<CommandSourceStack> context, long amount, Collection<ServerPlayer> targets) throws CommandSyntaxException {
      for(ServerPlayer player : targets) {
         boolean hasChanged = (Boolean)EntityStatsCapability.get(player).map((props) -> props.alterBounty(amount, StatChangeSource.COMMAND)).orElse(false);
         if (hasChanged) {
            ModNetwork.sendTo(new SSyncEntityStatsPacket(player), player);
            ((CommandSourceStack)context.getSource()).m_288197_(() -> {
               String var10000 = String.valueOf(ChatFormatting.GREEN);
               return Component.m_237113_(var10000 + (amount > 0L ? "+" : "") + amount + " bounty for " + player.m_5446_().getString());
            }, true);
         }
      }

      return 1;
   }
}
