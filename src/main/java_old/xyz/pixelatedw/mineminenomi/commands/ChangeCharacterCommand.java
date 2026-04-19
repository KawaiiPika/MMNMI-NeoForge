package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.WyRegistry;
import xyz.pixelatedw.mineminenomi.api.commands.FactionArgument;
import xyz.pixelatedw.mineminenomi.api.commands.FightingStyleArgument;
import xyz.pixelatedw.mineminenomi.api.commands.RaceArgument;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModAdvancements;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class ChangeCharacterCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("change_character").requires(Requires.hasPermission(ModPermissions.CHANGE_CHARACTER_COMMAND));
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)builder.then(Commands.m_82127_("faction").then(Commands.m_82129_("target", EntityArgument.m_91466_()).then(Commands.m_82129_("value", FactionArgument.faction()).executes((context) -> changeFaction(context, EntityArgument.m_91474_(context, "target"), FactionArgument.getFaction(context, "value"))))))).then(Commands.m_82127_("race").then(Commands.m_82129_("target", EntityArgument.m_91466_()).then(Commands.m_82129_("value", RaceArgument.race()).executes((context) -> changeRace(context, EntityArgument.m_91474_(context, "target"), RaceArgument.getRace(context, "value"))))))).then(Commands.m_82127_("style").then(Commands.m_82129_("target", EntityArgument.m_91466_()).then(Commands.m_82129_("value", FightingStyleArgument.fightingStyle()).executes((context) -> changeStyle(context, EntityArgument.m_91474_(context, "target"), FightingStyleArgument.getFightingStyle(context, "value"))))));
      return builder;
   }

   private static int changeFaction(CommandContext<CommandSourceStack> context, ServerPlayer target, Faction faction) throws CommandSyntaxException {
      IEntityStats entityStatsProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
      if (entityStatsProps == null) {
         return -1;
      } else {
         entityStatsProps.setFaction(faction);
         ModAdvancements.SELECT_FACTION.trigger(target, faction.getRegistryName());
         ModNetwork.sendTo(new SSyncEntityStatsPacket(target), target);
         ((CommandSourceStack)context.getSource()).m_288197_(() -> {
            String var10000 = String.valueOf(ChatFormatting.GREEN);
            return Component.m_237113_(var10000 + "Changed " + target.m_5446_().getString() + "'s faction to " + String.valueOf(faction.getRegistryName()));
         }, true);
         return 1;
      }
   }

   private static int changeRace(CommandContext<CommandSourceStack> context, ServerPlayer target, Race race) throws CommandSyntaxException {
      IEntityStats entityStatsProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
      if (entityStatsProps == null) {
         return -1;
      } else {
         if (race.isSubRace()) {
            Optional<Race> mainRace = ((IForgeRegistry)WyRegistry.RACES.get()).getValues().stream().filter((raceEntry) -> {
               for(RegistryObject<Race> subRace : raceEntry.getSubRaces()) {
                  if (((Race)subRace.get()).equals(race)) {
                     return true;
                  }
               }

               return false;
            }).findFirst();
            if (mainRace.isPresent()) {
               entityStatsProps.setRace((Race)mainRace.get());
               entityStatsProps.setSubRace(race);
            }
         } else {
            entityStatsProps.setRace(race);
            entityStatsProps.setSubRace((Race)null);
            if (race.hasSubRaces()) {
               int randomSub = target.m_217043_().m_188503_(race.getSubRaces().size());
               entityStatsProps.setSubRace((Race)((RegistryObject)race.getSubRaces().get(randomSub)).get());
            }
         }

         ModAdvancements.SELECT_RACE.trigger(target, race.getRegistryName());
         ModNetwork.sendTo(new SSyncEntityStatsPacket(target), target);
         ProgressionHandler.checkAllForNewUnlocks(target, true);
         ((CommandSourceStack)context.getSource()).m_288197_(() -> {
            String var10000 = String.valueOf(ChatFormatting.GREEN);
            return Component.m_237113_(var10000 + "Changed " + target.m_5446_().getString() + "'s race to " + String.valueOf(race.getRegistryName()));
         }, true);
         return 1;
      }
   }

   private static int changeStyle(CommandContext<CommandSourceStack> context, ServerPlayer target, FightingStyle style) throws CommandSyntaxException {
      IEntityStats entityStatsProps = (IEntityStats)EntityStatsCapability.get(target).orElse((Object)null);
      if (entityStatsProps == null) {
         return -1;
      } else {
         entityStatsProps.setFightingStyle(style);
         ModAdvancements.SELECT_STYLE.trigger(target, style.getRegistryName());
         ModNetwork.sendTo(new SSyncEntityStatsPacket(target), target);
         ((CommandSourceStack)context.getSource()).m_288197_(() -> {
            String var10000 = String.valueOf(ChatFormatting.GREEN);
            return Component.m_237113_(var10000 + "Changed " + target.m_5446_().getString() + "'s fighting style to " + String.valueOf(style.getRegistryName()));
         }, true);
         return 1;
      }
   }
}
