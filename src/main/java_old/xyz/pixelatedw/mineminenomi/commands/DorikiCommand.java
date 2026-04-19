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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class DorikiCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("doriki").requires(Requires.hasPermission(ModPermissions.DORIKI_COMMAND));
      int min = -ServerConfig.getDorikiLimit();
      int max = ServerConfig.getDorikiLimit();
      builder.then(((RequiredArgumentBuilder)Commands.m_82129_("amount", IntegerArgumentType.integer(min, max)).executes((context) -> alterDoriki(context, IntegerArgumentType.getInteger(context, "amount"), getDefaultCollection(context)))).then(Commands.m_82129_("targets", EntityArgument.m_91460_()).executes((context) -> alterDoriki(context, IntegerArgumentType.getInteger(context, "amount"), EntityArgument.m_91477_(context, "targets")))));
      return builder;
   }

   private static Collection<ServerPlayer> getDefaultCollection(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      return Lists.newArrayList(new ServerPlayer[]{((CommandSourceStack)context.getSource()).m_81375_()});
   }

   private static int alterDoriki(CommandContext<CommandSourceStack> context, int amount, Collection<? extends Entity> targets) throws CommandSyntaxException {
      for(Entity target : targets) {
         if (target instanceof LivingEntity livingTarget) {
            boolean hasChanged = (Boolean)EntityStatsCapability.get(livingTarget).map((props) -> props.alterDoriki((double)amount, StatChangeSource.COMMAND)).orElse(false);
            if (hasChanged) {
               if (livingTarget instanceof ServerPlayer) {
                  ServerPlayer player = (ServerPlayer)livingTarget;
                  ModNetwork.sendTo(new SSyncEntityStatsPacket(player), player);
               }

               ((CommandSourceStack)context.getSource()).m_288197_(() -> {
                  String var10000 = String.valueOf(ChatFormatting.GREEN);
                  return Component.m_237113_(var10000 + (amount > 0 ? "+" : "") + amount + " doriki for " + livingTarget.m_5446_().getString());
               }, true);
            }
         }
      }

      return 1;
   }
}
