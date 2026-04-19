package xyz.pixelatedw.mineminenomi.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.FloatArgumentType;
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
import xyz.pixelatedw.mineminenomi.api.enums.HakiType;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;

public class HakiExpCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("hakiexp").requires(Requires.hasPermission(ModPermissions.HAKIEXP_COMMAND));
      int max = ServerConfig.getHakiExpLimit();
      builder.then(Commands.m_82129_("type", EnumArgument.enumArgument(HakiType.class)).then(((RequiredArgumentBuilder)Commands.m_82129_("amount", FloatArgumentType.floatArg((float)(-max), (float)max)).executes((context) -> alterHakiExp(context, (HakiType)context.getArgument("type", HakiType.class), FloatArgumentType.getFloat(context, "amount"), getDefaultCollection(context)))).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((context) -> alterHakiExp(context, (HakiType)context.getArgument("type", HakiType.class), FloatArgumentType.getFloat(context, "amount"), EntityArgument.m_91477_(context, "targets"))))));
      return builder;
   }

   private static Collection<ServerPlayer> getDefaultCollection(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      return Lists.newArrayList(new ServerPlayer[]{((CommandSourceStack)context.getSource()).m_81375_()});
   }

   private static int alterHakiExp(CommandContext<CommandSourceStack> context, HakiType hakiType, float amount, Collection<ServerPlayer> targets) throws CommandSyntaxException {
      for(ServerPlayer player : targets) {
         IHakiData props = (IHakiData)HakiCapability.get(player).orElse((Object)null);
         if (props == null) {
            return -1;
         }

         boolean flag = false;
         if (hakiType == HakiType.BUSOSHOKU) {
            flag = props.alterBusoshokuHakiExp(amount, StatChangeSource.COMMAND);
         } else if (hakiType == HakiType.KENBUNSHOKU) {
            flag = props.alterKenbunshokuHakiExp(amount, StatChangeSource.COMMAND);
         } else if (hakiType == HakiType.HAOSHOKU) {
            flag |= props.alterBusoshokuHakiExp(amount, StatChangeSource.COMMAND);
            flag |= props.alterKenbunshokuHakiExp(amount, StatChangeSource.COMMAND);
         }

         if (flag) {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> {
               String var10000 = String.valueOf(ChatFormatting.GREEN);
               return Component.m_237113_(var10000 + (amount > 0.0F ? "+" : "") + amount + " haki exp for " + player.m_5446_().getString());
            }, true);
         }
      }

      return 1;
   }
}
