package xyz.pixelatedw.mineminenomi.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityUnlock;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.commands.AbilityArgument;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityGroups;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.handlers.ability.ProgressionHandler;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncAbilityDataPacket;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncDevilFruitPacket;

public class AbilityCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("ability").requires(Requires.hasEitherPermission(ModPermissions.ABILITY_COMMAND_RESET_COOLDOWN, ModPermissions.ABILITY_COMMAND));
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)builder.then(((LiteralArgumentBuilder)Commands.m_82127_("give").requires(Requires.hasPermission(ModPermissions.ABILITY_COMMAND))).then(((RequiredArgumentBuilder)Commands.m_82129_("ability", AbilityArgument.ability()).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((context) -> addAbility(context, AbilityArgument.getAbility(context, "ability"), EntityArgument.m_91477_(context, "targets"))))).executes((context) -> addAbility(context, AbilityArgument.getAbility(context, "ability"), getDefaultCollection(context)))))).then(((LiteralArgumentBuilder)Commands.m_82127_("remove").requires(Requires.hasPermission(ModPermissions.ABILITY_COMMAND))).then(((RequiredArgumentBuilder)Commands.m_82129_("ability", new AbilityArgument()).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((context) -> removeAbility(context, AbilityArgument.getAbility(context, "ability"), EntityArgument.m_91477_(context, "targets"))))).executes((context) -> removeAbility(context, AbilityArgument.getAbility(context, "ability"), getDefaultCollection(context)))))).then(((LiteralArgumentBuilder)Commands.m_82127_("unlock_group").requires(Requires.hasPermission(ModPermissions.ABILITY_COMMAND))).then(((RequiredArgumentBuilder)Commands.m_82129_("group", ResourceLocationArgument.m_106984_()).suggests(Suggests.allAbilityGroups()).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((context) -> abilityGroup(context, ResourceLocationArgument.m_107011_(context, "group"), 1, EntityArgument.m_91477_(context, "targets"))))).executes((context) -> abilityGroup(context, ResourceLocationArgument.m_107011_(context, "group"), 1, getDefaultCollection(context)))))).then(((LiteralArgumentBuilder)Commands.m_82127_("lock_group").requires(Requires.hasPermission(ModPermissions.ABILITY_COMMAND))).then(((RequiredArgumentBuilder)Commands.m_82129_("group", ResourceLocationArgument.m_106984_()).suggests(Suggests.allAbilityGroups()).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((context) -> abilityGroup(context, ResourceLocationArgument.m_107011_(context, "group"), -1, EntityArgument.m_91477_(context, "targets"))))).executes((context) -> abilityGroup(context, ResourceLocationArgument.m_107011_(context, "group"), -1, getDefaultCollection(context)))))).then(((LiteralArgumentBuilder)Commands.m_82127_("reset_cooldown").then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((context) -> resetCooldown(context, EntityArgument.m_91477_(context, "targets"))))).executes((context) -> resetCooldown(context, getDefaultCollection(context))));
      if (ServerConfig.hasAwakeningsEnabled()) {
         builder.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("awaken").requires(Requires.hasPermission(ModPermissions.ABILITY_COMMAND))).then(Commands.m_82129_("targets", EntityArgument.m_91470_()).executes((context) -> awakeFruit(context, EntityArgument.m_91477_(context, "targets"))))).executes((context) -> awakeFruit(context, getDefaultCollection(context))));
      }

      return builder;
   }

   private static int awakeFruit(CommandContext<CommandSourceStack> context, Collection<ServerPlayer> players) {
      for(ServerPlayer player : players) {
         DevilFruitCapability.get(player).ifPresent((props) -> {
            props.setAwakenedFruit(!props.hasAwakenedFruit());
            ProgressionHandler.checkForPossibleFruitAbilities(player);
            ModNetwork.sendTo(new SSyncDevilFruitPacket(player), player);
         });
      }

      return 1;
   }

   private static int abilityGroup(CommandContext<CommandSourceStack> context, ResourceLocation id, int op, Collection<ServerPlayer> players) {
      for(ServerPlayer player : players) {
         AbilityCapability.get(player).ifPresent((props) -> {
            for(RegistryObject<? extends AbilityCore<?>> reg : AbilityGroups.getAbilities(id)) {
               AbilityCore<?> core = (AbilityCore)reg.get();
               if (!AbilityHelper.verifyIfAbilityIsBanned(core)) {
                  if (op == 1) {
                     props.addUnlockedAbility(core, AbilityUnlock.COMMAND);
                  } else {
                     props.removeUnlockedAbility(core);
                  }
               }
            }

            ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
         });
      }

      return 1;
   }

   private static Collection<ServerPlayer> getDefaultCollection(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      return Lists.newArrayList(new ServerPlayer[]{((CommandSourceStack)context.getSource()).m_81375_()});
   }

   private static int resetCooldown(CommandContext<CommandSourceStack> context, Collection<ServerPlayer> players) {
      for(ServerPlayer player : players) {
         AbilityCapability.get(player).ifPresent((props) -> {
            for(IAbility ability : props.getEquippedAbilities()) {
               if (ability != null) {
                  ability.getComponent((AbilityComponentKey)ModAbilityComponents.COOLDOWN.get()).ifPresent((c) -> c.stopCooldown(player));
                  ability.getComponent((AbilityComponentKey)ModAbilityComponents.STACK.get()).ifPresent((stackComp) -> stackComp.revertStacksToDefault(player, ability));
               }
            }

         });
         HakiCapability.get(player).ifPresent((props) -> props.setHakiOveruse(0));
      }

      return 1;
   }

   private static int addAbility(CommandContext<CommandSourceStack> context, AbilityCore<?> core, Collection<ServerPlayer> targets) {
      if (AbilityHelper.verifyIfAbilityIsBanned(core)) {
         return -1;
      } else {
         for(ServerPlayer player : targets) {
            AbilityCapability.get(player).ifPresent((props) -> {
               props.addUnlockedAbility(core, AbilityUnlock.COMMAND);
               if (WyDebug.isDebug()) {
                  String var10000 = String.valueOf(ChatFormatting.GREEN);
                  Component message = Component.m_237113_(var10000 + String.valueOf(ChatFormatting.ITALIC) + "[DEBUG] " + core.getLocalizedName().getString() + " unlocked for " + player.m_7755_().getString());
                  player.m_5661_(message, false);
               }

               ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
            });
         }

         return 1;
      }
   }

   private static int removeAbility(CommandContext<CommandSourceStack> context, AbilityCore<?> core, Collection<ServerPlayer> targets) {
      for(ServerPlayer player : targets) {
         AbilityCapability.get(player).ifPresent((props) -> {
            props.removeUnlockedAbility(core);
            if (WyDebug.isDebug()) {
               String var10000 = String.valueOf(ChatFormatting.GREEN);
               Component message = Component.m_237113_(var10000 + String.valueOf(ChatFormatting.ITALIC) + "[DEBUG] " + core.getLocalizedName().getString() + " removed for " + player.m_7755_().getString());
               player.m_5661_(message, false);
            }

            ModNetwork.sendTo(new SSyncAbilityDataPacket(player), player);
         });
      }

      return 1;
   }
}
