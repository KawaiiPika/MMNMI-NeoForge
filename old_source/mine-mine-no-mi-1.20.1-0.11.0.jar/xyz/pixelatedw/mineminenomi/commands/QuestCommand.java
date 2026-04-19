package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xyz.pixelatedw.mineminenomi.api.commands.QuestArgument;
import xyz.pixelatedw.mineminenomi.api.quests.Quest;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncQuestDataPacket;

public class QuestCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("quest").requires(Requires.hasPermission(ModPermissions.QUEST_COMMAND));
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)builder.then(Commands.m_82127_("finish").then(((RequiredArgumentBuilder)Commands.m_82129_("quest", QuestArgument.quest()).then(Commands.m_82129_("target", EntityArgument.m_91466_()).executes((context) -> finishQuest(context, QuestArgument.getQuest(context, "quest"), EntityArgument.m_91474_(context, "target"))))).executes((context) -> finishQuest(context, QuestArgument.getQuest(context, "quest"), ((CommandSourceStack)context.getSource()).m_81375_()))))).then(Commands.m_82127_("give").then(((RequiredArgumentBuilder)Commands.m_82129_("quest", QuestArgument.quest()).then(Commands.m_82129_("target", EntityArgument.m_91466_()).executes((context) -> giveQuest(context, QuestArgument.getQuest(context, "quest"), EntityArgument.m_91474_(context, "target"))))).executes((context) -> giveQuest(context, QuestArgument.getQuest(context, "quest"), ((CommandSourceStack)context.getSource()).m_81375_()))))).then(Commands.m_82127_("unfinish").then(((RequiredArgumentBuilder)Commands.m_82129_("quest", QuestArgument.quest()).then(Commands.m_82129_("target", EntityArgument.m_91466_()).executes((context) -> unfinishQuest(context, QuestArgument.getQuest(context, "quest"), EntityArgument.m_91474_(context, "target"))))).executes((context) -> unfinishQuest(context, QuestArgument.getQuest(context, "quest"), ((CommandSourceStack)context.getSource()).m_81375_()))))).then(Commands.m_82127_("remove").then(((RequiredArgumentBuilder)Commands.m_82129_("quest", QuestArgument.quest()).then(Commands.m_82129_("target", EntityArgument.m_91466_()).executes((context) -> removeQuest(context, QuestArgument.getQuest(context, "quest"), EntityArgument.m_91474_(context, "target"))))).executes((context) -> removeQuest(context, QuestArgument.getQuest(context, "quest"), ((CommandSourceStack)context.getSource()).m_81375_()))));
      return builder;
   }

   private static int unfinishQuest(CommandContext<CommandSourceStack> context, QuestId<? extends Quest> quest, ServerPlayer player) {
      IQuestData questData = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      IAbilityData abilityData = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
      if (questData != null && abilityData != null) {
         if (questData.hasFinishedQuest(quest)) {
            questData.removeFinishedQuest(quest);
            ProgressionHandler.checkForStyleUnlocks(player);
            ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
         } else {
            player.m_5661_(Component.m_237113_("You haven't finished this quest!"), false);
         }

         return 1;
      } else {
         return -1;
      }
   }

   private static int finishQuest(CommandContext<CommandSourceStack> context, QuestId<? extends Quest> questId, ServerPlayer player) {
      IQuestData questData = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questData == null) {
         return -1;
      } else {
         if (questData.hasInProgressQuest(questId)) {
            Quest quest = questData.getInProgressQuest(questId);
            if (quest.tryComplete(player)) {
               questData.addFinishedQuest(quest.getCore());
               questData.removeInProgressQuest(quest.getCore());
            }
         } else if (!questData.hasInProgressQuest(questId)) {
            player.m_5661_(Component.m_237113_("You don't have this quest!"), false);
         }

         return 1;
      }
   }

   private static int giveQuest(CommandContext<CommandSourceStack> context, QuestId<? extends Quest> quest, ServerPlayer player) {
      IQuestData questData = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questData == null) {
         return -1;
      } else {
         if (!questData.hasInProgressQuest(quest)) {
            questData.addInProgressQuest(quest);
            ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
         } else {
            player.m_5661_(Component.m_237113_("You aleady have this quest!"), false);
         }

         return 1;
      }
   }

   private static int removeQuest(CommandContext<CommandSourceStack> context, QuestId<? extends Quest> quest, ServerPlayer player) {
      IQuestData questData = (IQuestData)QuestCapability.get(player).orElse((Object)null);
      if (questData == null) {
         return -1;
      } else {
         if (questData.hasInProgressQuest(quest)) {
            questData.removeInProgressQuest(quest);
            questData.removeFinishedQuest(quest);
            ModNetwork.sendTo(new SSyncQuestDataPacket(player), player);
         } else {
            player.m_5661_(Component.m_237113_("You don't have this quest!"), false);
         }

         return 1;
      }
   }
}
