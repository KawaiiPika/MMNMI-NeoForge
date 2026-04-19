package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import xyz.pixelatedw.mineminenomi.api.commands.CurrencyTypeArgument;
import xyz.pixelatedw.mineminenomi.api.enums.Currency;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModItems;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nCommands;
import xyz.pixelatedw.mineminenomi.packets.server.SSyncEntityStatsPacket;

public class PouchCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("pouch").requires((source) -> source.m_6761_(0));
      builder.then(Commands.m_82127_("ALL").then(Commands.m_82129_("currency", CurrencyTypeArgument.currencyType()).executes((ctx) -> createPouch(ctx, (Currency)ctx.getArgument("currency", Currency.class), 999999999L))));
      builder.then(Commands.m_82129_("amount", LongArgumentType.longArg(1L, 999999999L)).then(Commands.m_82129_("currency", CurrencyTypeArgument.currencyType()).executes((ctx) -> createPouch(ctx, (Currency)ctx.getArgument("currency", Currency.class), LongArgumentType.getLong(ctx, "amount")))));
      builder.then(Commands.m_82127_("take").then(Commands.m_82129_("amount", LongArgumentType.longArg(1L, 999999999L)).executes((ctx) -> takeFromPouch(ctx, ((CommandSourceStack)ctx.getSource()).m_81375_(), LongArgumentType.getLong(ctx, "amount")))));
      return builder;
   }

   private static int takeFromPouch(CommandContext<CommandSourceStack> context, ServerPlayer player, long amount) {
      if (amount <= 0L) {
         return 1;
      } else {
         ItemStack stack = player.m_21205_();
         if (stack.m_41619_()) {
            stack = player.m_21206_();
            if (stack.m_41619_()) {
               return 1;
            }
         }

         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         if (props == null) {
            return -1;
         } else {
            if (stack.m_41720_() == ModItems.BELLY_POUCH.get()) {
               long belly = stack.m_41784_().m_128454_("belly");
               if (belly < amount) {
                  amount = belly;
               }

               props.alterBelly(amount, StatChangeSource.COMMAND);
               long remains = belly - amount;
               if (remains > 0L) {
                  stack.m_41784_().m_128356_("belly", remains);
               } else {
                  stack.m_41774_(1);
               }

               ModNetwork.sendTo(new SSyncEntityStatsPacket(player, props), player);
            } else if (stack.m_41720_() == ModItems.EXTOL_POUCH.get()) {
               long extol = stack.m_41784_().m_128454_("extol");
               if (extol < amount) {
                  amount = extol;
               }

               props.alterExtol(amount, StatChangeSource.COMMAND);
               long remains = extol - amount;
               if (remains > 0L) {
                  stack.m_41784_().m_128356_("extol", remains);
               } else {
                  stack.m_41774_(1);
               }

               ModNetwork.sendTo(new SSyncEntityStatsPacket(player, props), player);
            }

            return 1;
         }
      }
   }

   private static int createPouch(CommandContext<CommandSourceStack> context, Currency currency, long amount) {
      if (amount <= 0L) {
         return 1;
      } else {
         try {
            ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();
            switch (currency) {
               case EXTOL:
                  createExtolPouch(player, amount);
                  break;
               case BELLY:
               default:
                  createBellyPouch(player, amount);
            }
         } catch (CommandSyntaxException e) {
            e.printStackTrace();
         }

         return 1;
      }
   }

   private static void createBellyPouch(ServerPlayer player, long amount) {
      if (ItemsHelper.hasInventoryFull(player)) {
         player.m_213846_(ModI18nCommands.POUCH_INVENTORY_FULL);
      } else {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         if (props != null) {
            if (props.getBelly() > 0L) {
               if (props.getBelly() - amount >= 0L) {
                  props.alterBelly(-amount, StatChangeSource.COMMAND);
               } else {
                  amount = props.getBelly();
                  props.alterBelly(-amount, StatChangeSource.COMMAND);
               }

               ItemStack pouch = new ItemStack((ItemLike)ModItems.BELLY_POUCH.get());
               pouch.m_41784_().m_128356_("belly", amount);
               player.m_150109_().m_36054_(pouch);
               ModNetwork.sendTo(new SSyncEntityStatsPacket(player), player);
            }
         }
      }
   }

   private static void createExtolPouch(ServerPlayer player, long amount) {
      if (ItemsHelper.hasInventoryFull(player)) {
         player.m_213846_(ModI18nCommands.POUCH_INVENTORY_FULL);
      } else {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         if (props != null) {
            if (props.getExtol() > 0L) {
               if (props.getExtol() - amount >= 0L) {
                  props.alterExtol(-amount, StatChangeSource.COMMAND);
               } else {
                  amount = props.getExtol();
                  props.alterExtol(-amount, StatChangeSource.COMMAND);
               }

               ItemStack pouch = new ItemStack((ItemLike)ModItems.EXTOL_POUCH.get());
               pouch.m_41784_().m_128356_("extol", amount);
               player.m_150109_().m_36054_(pouch);
               ModNetwork.sendTo(new SSyncEntityStatsPacket(player), player);
            }
         }
      }
   }
}
