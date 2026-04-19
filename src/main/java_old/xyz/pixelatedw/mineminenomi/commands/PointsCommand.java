package xyz.pixelatedw.mineminenomi.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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
import net.minecraftforge.server.command.EnumArgument;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class PointsCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("points").requires(Requires.hasPermission(ModPermissions.POINTS_COMMAND));
      int max = ServerConfig.getTrainingPointLimit();
      builder.then(Commands.m_82129_("type", EnumArgument.enumArgument(TrainingPointType.class)).then(((RequiredArgumentBuilder)Commands.m_82129_("amount", IntegerArgumentType.integer(-max, max)).executes((context) -> alterTrainingPoints(context, (TrainingPointType)context.getArgument("type", TrainingPointType.class), IntegerArgumentType.getInteger(context, "amount"), getDefaultCollection(context)))).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((context) -> alterTrainingPoints(context, (TrainingPointType)context.getArgument("type", TrainingPointType.class), IntegerArgumentType.getInteger(context, "amount"), EntityArgument.m_91477_(context, "targets"))))));
      return builder;
   }

   private static Collection<ServerPlayer> getDefaultCollection(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      return Lists.newArrayList(new ServerPlayer[]{((CommandSourceStack)context.getSource()).m_81375_()});
   }

   private static int alterTrainingPoints(CommandContext<CommandSourceStack> context, TrainingPointType type, int amount, Collection<ServerPlayer> targets) throws CommandSyntaxException {
      for(ServerPlayer player : targets) {
         IEntityStats entityProps = (IEntityStats)EntityStatsCapability.getLazy(player).orElse((Object)null);
         if (entityProps != null) {
            entityProps.alterTrainingPoints(type, amount);
            ModNetwork.sendTo(new SSyncEntityStatsPacket(player), player);
            ((CommandSourceStack)context.getSource()).m_288197_(() -> {
               String var10000 = String.valueOf(ChatFormatting.GREEN);
               return Component.m_237113_(var10000 + (amount > 0 ? "+" : "") + amount + " training points for " + player.m_5446_().getString() + " in " + type.name());
            }, true);
         }
      }

      return 1;
   }
}
