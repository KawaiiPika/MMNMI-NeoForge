package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.registries.IForgeRegistry;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.challenges.Challenge;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeCore;
import xyz.pixelatedw.mineminenomi.api.commands.ChallengeArgument;
import xyz.pixelatedw.mineminenomi.data.entity.challenge.ChallengeCapability;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nChallenges;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncChallengeDataPacket;

public class ChallengeCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("challenge").requires(Requires.hasPermission(ModPermissions.CHALLENGE_COMMAND));
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)builder.then(Commands.m_82127_("start").then(((RequiredArgumentBuilder)Commands.m_82129_("challenge", ChallengeArgument.challenge()).executes((context) -> startChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), ((CommandSourceStack)context.getSource()).m_81375_()))).then(Commands.m_82129_("target", EntityArgument.m_91466_()).executes((context) -> startChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), EntityArgument.m_91474_(context, "target"))))))).then(Commands.m_82127_("finish").then(((RequiredArgumentBuilder)Commands.m_82129_("challenge", ChallengeArgument.challenge()).executes((context) -> finishChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), ((CommandSourceStack)context.getSource()).m_81375_()))).then(Commands.m_82129_("target", EntityArgument.m_91466_()).executes((context) -> finishChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), EntityArgument.m_91474_(context, "target"))))))).then(((LiteralArgumentBuilder)Commands.m_82127_("give").then(((RequiredArgumentBuilder)Commands.m_82129_("challenge", ChallengeArgument.challenge()).executes((context) -> giveChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), ((CommandSourceStack)context.getSource()).m_81375_()))).then(Commands.m_82129_("target", EntityArgument.m_91466_()).executes((context) -> giveChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), EntityArgument.m_91474_(context, "target")))))).then(Commands.m_82127_("ALL").then(Commands.m_82129_("target", EntityArgument.m_91470_()).executes((context) -> giveAllChallenges(context, EntityArgument.m_91474_(context, "target"))))))).then(Commands.m_82127_("unfinish").then(((RequiredArgumentBuilder)Commands.m_82129_("challenge", ChallengeArgument.challenge()).executes((context) -> unfinishChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), ((CommandSourceStack)context.getSource()).m_81375_()))).then(Commands.m_82129_("target", EntityArgument.m_91466_()).executes((context) -> unfinishChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), EntityArgument.m_91474_(context, "target"))))))).then(Commands.m_82127_("remove").then(((RequiredArgumentBuilder)Commands.m_82129_("challenge", ChallengeArgument.challenge()).executes((context) -> removeChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), ((CommandSourceStack)context.getSource()).m_81375_()))).then(Commands.m_82129_("target", EntityArgument.m_91466_()).executes((context) -> removeChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), EntityArgument.m_91474_(context, "target"))))));
      return builder;
   }

   private static int startChallenge(CommandContext<CommandSourceStack> context, ChallengeCore<?> core, ServerPlayer player) {
      ChallengeCapability.get(player).ifPresent((props) -> {
         Challenge challenge = props.getChallenge(core);
         if (challenge != null) {
            ChallengesWorldData.get().startChallenge(player, (List)null, core, false);
         } else {
            player.m_213846_(ModI18nChallenges.MESSAGE_NOT_UNLOCKED);
         }

      });
      return 1;
   }

   private static int unfinishChallenge(CommandContext<CommandSourceStack> context, ChallengeCore<?> core, ServerPlayer player) {
      ChallengeCapability.get(player).ifPresent((props) -> {
         Challenge challenge = props.getChallenge(core);
         if (challenge != null) {
            challenge.setComplete(player, false);
            challenge.resetBestTime();
            ModNetwork.sendTo(new SSyncChallengeDataPacket(player, props), player);
         } else {
            player.m_213846_(ModI18nChallenges.MESSAGE_NOT_FINISHED);
         }

      });
      return 1;
   }

   private static int finishChallenge(CommandContext<CommandSourceStack> context, ChallengeCore<?> core, ServerPlayer player) {
      ChallengeCapability.get(player).ifPresent((props) -> {
         Challenge challenge = props.getChallenge(core);
         if (challenge != null) {
            challenge.setComplete(player, true);
            ModNetwork.sendTo(new SSyncChallengeDataPacket(player, props), player);
         } else {
            player.m_213846_(ModI18nChallenges.MESSAGE_NOT_UNLOCKED);
         }

      });
      return 1;
   }

   private static int giveAllChallenges(CommandContext<CommandSourceStack> context, ServerPlayer player) {
      ChallengeCapability.get(player).ifPresent((props) -> {
         for(ChallengeCore<?> core : ((IForgeRegistry)WyRegistry.CHALLENGES.get()).getValues()) {
            if (!props.hasChallenge(core)) {
               props.addChallenge(core);
               ModNetwork.sendTo(new SSyncChallengeDataPacket(player, props), player);
            }
         }

      });
      return 1;
   }

   private static int giveChallenge(CommandContext<CommandSourceStack> context, ChallengeCore<?> core, ServerPlayer player) {
      ChallengeCapability.get(player).ifPresent((props) -> {
         if (!props.hasChallenge(core)) {
            props.addChallenge(core);
            ModNetwork.sendTo(new SSyncChallengeDataPacket(player, props), player);
         } else {
            player.m_213846_(ModI18nChallenges.MESSAGE_ALREADY_UNLOCKED);
         }

      });
      return 1;
   }

   private static int removeChallenge(CommandContext<CommandSourceStack> context, ChallengeCore<?> core, ServerPlayer player) {
      ChallengeCapability.get(player).ifPresent((props) -> {
         if (props.hasChallenge(core)) {
            props.removeChallenge(core);
            ModNetwork.sendTo(new SSyncChallengeDataPacket(player, props), player);
         } else {
            player.m_213846_(ModI18nChallenges.MESSAGE_NOT_UNLOCKED);
         }

      });
      return 1;
   }
}
