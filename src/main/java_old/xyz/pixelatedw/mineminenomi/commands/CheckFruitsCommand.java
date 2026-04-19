package xyz.pixelatedw.mineminenomi.commands;

import com.google.common.base.Strings;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.api.OneFruitEntry;
import xyz.pixelatedw.mineminenomi.api.commands.FruitArgument;
import xyz.pixelatedw.mineminenomi.config.GeneralConfig;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.OneFruitWorldData;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nCommands;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class CheckFruitsCommand {
   private static final Comparator<OneFruitEntry> COMPARE_ENTRIES_ALPHABETICALLY = (o1, o2) -> {
      String fruitName1 = (String)o1.getItemFromKey().map((item) -> item.m_41466_().getString()).orElse(o1.getKey().toString());
      String fruitName2 = (String)o2.getItemFromKey().map((item) -> item.m_41466_().getString()).orElse(o2.getKey().toString());
      return fruitName1.compareToIgnoreCase(fruitName2);
   };
   private static final Comparator<Item> COMPARE_FRUITS_ALPHABETICALLY = (i1, i2) -> {
      String fruitName1 = i1.m_41466_().getString();
      String fruitName2 = i2.m_41466_().getString();
      return fruitName1.compareToIgnoreCase(fruitName2);
   };

   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = Commands.m_82127_("check_fruits");
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)builder.requires((source) -> source.m_6761_((Boolean)GeneralConfig.PUBLIC_CHECK_FRUITS.get() ? 0 : 2))).executes((context) -> checkFruitsInWorld(context))).then(((LiteralArgumentBuilder)Commands.m_82127_("fruit").requires((source) -> source.m_6761_((Boolean)GeneralConfig.PUBLIC_CHECK_FRUITS.get() ? 0 : 2))).then(Commands.m_82129_("fruit", FruitArgument.fruit()).executes((context) -> checkFruitInWorld(context, FruitArgument.getFruit(context, "fruit")))))).then(((LiteralArgumentBuilder)Commands.m_82127_("list").requires((source) -> source.m_6761_((Boolean)GeneralConfig.PUBLIC_CHECK_FRUITS.get() ? 0 : 2))).executes((context) -> checkFruitsInWorld(context)))).then(((LiteralArgumentBuilder)Commands.m_82127_("history").requires((source) -> source.m_6761_(2))).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.m_82129_("fruit", FruitArgument.fruit()).then(Commands.m_82127_("export").executes((context) -> exportFruitHistory(context, FruitArgument.getFruit(context, "fruit"))))).then(Commands.m_82129_("page", IntegerArgumentType.integer()).executes((context) -> fruitHistory(context, FruitArgument.getFruit(context, "fruit"), IntegerArgumentType.getInteger(context, "page"))))).executes((context) -> fruitHistory(context, FruitArgument.getFruit(context, "fruit"), -1))));
      return builder;
   }

   private static int checkFruitInWorld(CommandContext<CommandSourceStack> context, AkumaNoMiItem fruit) {
      if (!ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
         ((CommandSourceStack)context.getSource()).m_81352_(ModI18nCommands.CHECK_FRUIT_OFPW_ONLY);
         return 0;
      } else {
         Level world = ((CommandSourceStack)context.getSource()).m_81372_();
         OneFruitWorldData worldData = OneFruitWorldData.get();
         OneFruitEntry entry = worldData.getOneFruitEntry(fruit.getRegistryKey());
         if (entry == null) {
            ((CommandSourceStack)context.getSource()).m_81352_(Component.m_237113_("No entry found for " + fruit.m_41466_().getString()));
            return 0;
         } else {
            Item fruitItem = (Item)ForgeRegistries.ITEMS.getValue(entry.getKey());
            if (fruitItem == null) {
               return 0;
            } else {
               String fruitName = fruitItem.m_41466_().getString();
               String playerName = getOwnerName(entry, world);
               String status = getStatusColor(entry);
               MutableComponent message = Component.m_237113_("===============================================\n");
               message.m_130946_("§l§6Fruit Name | Player Name | Status \n");
               message.m_130946_(fruitName + " | " + playerName + " | " + status);
               message.m_7220_(getClickableFruitLocation(entry));
               message.m_130946_("\n");
               message.m_130946_("===============================================");
               ((CommandSourceStack)context.getSource()).m_288197_(() -> message, false);
               return 0;
            }
         }
      }
   }

   private static int exportFruitHistory(CommandContext<CommandSourceStack> context, AkumaNoMiItem fruit) {
      if (!ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
         ((CommandSourceStack)context.getSource()).m_81352_(ModI18nCommands.CHECK_FRUIT_OFPW_ONLY);
         return 0;
      } else {
         Level world = ((CommandSourceStack)context.getSource()).m_81372_();
         OneFruitWorldData worldData = OneFruitWorldData.get();
         Optional<OneFruitEntry> oneFruit = worldData.getOneFruitEntries().stream().filter((entry) -> entry.getKey().toString().equals(fruit.getRegistryKey().toString())).findFirst();
         if (oneFruit.isPresent()) {
            StringBuilder builder = new StringBuilder();
            List<OneFruitEntry.HistoryEntry> history = ((OneFruitEntry)oneFruit.get()).getHistory();

            for(int i = 0; i < history.size(); ++i) {
               OneFruitEntry.HistoryEntry current = (OneFruitEntry.HistoryEntry)history.get(i);
               OneFruitEntry.HistoryEntry previous = (OneFruitEntry.HistoryEntry)history.get(Mth.m_14045_(i - 1, 0, history.size()));
               String status = "";
               String owner = getOwnerName(current, world);
               if (current == previous) {
                  status = current.getStatus().name();
               } else {
                  String var10000 = previous.getStatus().name();
                  status = var10000 + " → " + current.getStatus().name();
               }

               builder.append(current.getDate().toString() + ":\n");
               builder.append(status + " by " + owner + (!Strings.isNullOrEmpty(current.getStatusMessage()) ? " due to: " + current.getStatusMessage() : "") + "\n\n");
            }

            try {
               String path = System.getProperty("user.dir");
               File exportFolder = new File(path, "onefruit-exports");
               if (!exportFolder.exists()) {
                  exportFolder.mkdir();
               }

               File exportFile = new File(exportFolder, String.valueOf(fruit) + ".txt");
               if (exportFile.exists()) {
                  exportFile.delete();
               }

               exportFile.createNewFile();
               PrintWriter out = new PrintWriter(new FileWriter(exportFile, true));

               try {
                  out.append(builder.toString());
               } catch (Throwable var14) {
                  try {
                     out.close();
                  } catch (Throwable var13) {
                     var14.addSuppressed(var13);
                  }

                  throw var14;
               }

               out.close();
               ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("Exported file as " + String.valueOf(fruit) + ".txt"), false);
            } catch (Exception e) {
               e.printStackTrace();
               ((CommandSourceStack)context.getSource()).m_288197_(() -> ModI18nCommands.CHECK_FRUIT_ERROR_EXPORTING, false);
            }
         }

         return 1;
      }
   }

   private static int fruitHistory(CommandContext<CommandSourceStack> context, AkumaNoMiItem fruit, int page) {
      if (!ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
         ((CommandSourceStack)context.getSource()).m_81352_(ModI18nCommands.CHECK_FRUIT_OFPW_ONLY);
         return 0;
      } else {
         Level world = ((CommandSourceStack)context.getSource()).m_81372_();
         OneFruitWorldData worldData = OneFruitWorldData.get();
         Optional<OneFruitEntry> oneFruit = worldData.getOneFruitEntries().stream().filter((entry) -> entry.getKey().toString().equals(fruit.getRegistryKey().toString())).findFirst();
         MutableComponent message = Component.m_237113_("");
         if (oneFruit.isPresent()) {
            int elements = 100;
            List<OneFruitEntry.HistoryEntry> history = ((OneFruitEntry)oneFruit.get()).getHistory();
            if (page >= 0) {
               history = history.stream().skip((long)(elements * page)).limit((long)elements).toList();
            }

            message.m_7220_(Component.m_237113_("===============================================\n"));
            String var10001 = String.valueOf(((OneFruitEntry)oneFruit.get()).getKey());
            message.m_7220_(Component.m_237113_("§l§6" + var10001 + "'s History - Page " + (page + 1) + ":§r \n"));

            for(int i = 0; i < history.size(); ++i) {
               OneFruitEntry.HistoryEntry current = (OneFruitEntry.HistoryEntry)history.get(i);
               OneFruitEntry.HistoryEntry previous = (OneFruitEntry.HistoryEntry)history.get(Mth.m_14045_(i - 1, 0, history.size()));
               String status = "";
               String owner = getOwnerName(current, world);
               if (current == previous) {
                  status = getStatusColor(current);
               } else {
                  String var10000 = getStatusColor(previous);
                  status = var10000 + " → " + getStatusColor(current);
               }

               message.m_7220_(Component.m_237113_(current.getDate().toString() + ":\n"));
               message.m_7220_(Component.m_237113_(status + " by " + owner + (!Strings.isNullOrEmpty(current.getStatusMessage()) ? " due to: " + current.getStatusMessage() : "")));
               message.m_7220_(getClickableFruitLocation(current));
               message.m_130946_("\n");
            }

            message.m_7220_(Component.m_237113_("==============================================="));
            ((CommandSourceStack)context.getSource()).m_288197_(() -> message, false);
         } else {
            ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_("No history found for " + fruit.getRegistryKey().toString()), false);
         }

         return 1;
      }
   }

   private static int checkFruitsInWorld(CommandContext<CommandSourceStack> context) {
      if (!ServerConfig.hasOneFruitPerWorldSimpleLogic()) {
         ((CommandSourceStack)context.getSource()).m_81352_(ModI18nCommands.CHECK_FRUIT_OFPW_ONLY);
         return 0;
      } else {
         Level world = ((CommandSourceStack)context.getSource()).m_81372_();
         OneFruitWorldData worldData = OneFruitWorldData.get();
         MutableComponent message = Component.m_237113_("");
         List<ResourceLocation> foundKeys = worldData.getOneFruitEntries().stream().map(OneFruitEntry::getKey).toList();
         List<OneFruitEntry> existingFruits = worldData.getOneFruitEntries().stream().sorted(COMPARE_ENTRIES_ALPHABETICALLY).toList();
         message.m_130946_("===============================================\n");
         message.m_130946_("§l§6Fruit Name | Player Name | Status §r\n");

         for(OneFruitEntry entry : existingFruits) {
            String fruitName = (String)entry.getItemFromKey().map((item) -> item.m_41466_().getString()).orElse(entry.getKey().toString());
            String playerName = getOwnerName(entry, world);
            String status = getStatusColor(entry);
            message.m_130946_(fruitName + " | " + playerName + " | " + status);
            message.m_7220_(getClickableFruitLocation(entry));
            message.m_130946_("\n");
         }

         message.m_130946_("\n");

         for(AkumaNoMiItem fruit : ModValues.DEVIL_FRUITS.stream().sorted(COMPARE_FRUITS_ALPHABETICALLY).toList()) {
            ResourceLocation key = fruit.getRegistryKey();
            if (!foundKeys.contains(key)) {
               message.m_130946_(fruit.m_41466_().getString() + " | §5NEVER_FOUND§r\n");
            }
         }

         message.m_130946_("===============================================");
         ((CommandSourceStack)context.getSource()).m_288197_(() -> message, false);
         return 1;
      }
   }

   private static String getOwnerName(OneFruitEntry entry, Level world) {
      String playerName = "System";
      if (entry.getOwner().isPresent()) {
         if (world.m_46003_((UUID)entry.getOwner().get()) != null) {
            playerName = world.m_46003_((UUID)entry.getOwner().get()).m_5446_().getString();
         } else if (UsernameCache.getLastKnownUsername((UUID)entry.getOwner().get()) != null) {
            playerName = UsernameCache.getLastKnownUsername((UUID)entry.getOwner().get());
         } else {
            playerName = "Unknown Player Name";
         }
      }

      return playerName;
   }

   private static String getOwnerName(OneFruitEntry.HistoryEntry entry, Level world) {
      String playerName = "System";
      if (entry.getOwner().isPresent()) {
         if (world.m_46003_((UUID)entry.getOwner().get()) != null) {
            playerName = world.m_46003_((UUID)entry.getOwner().get()).m_5446_().getString();
         } else if (UsernameCache.getLastKnownUsername((UUID)entry.getOwner().get()) != null) {
            playerName = UsernameCache.getLastKnownUsername((UUID)entry.getOwner().get());
         } else {
            playerName = "Unknown Player Name";
         }
      }

      return playerName;
   }

   private static String getStatusColor(OneFruitEntry entry) {
      return getStatusColor(entry.getStatus());
   }

   private static String getStatusColor(OneFruitEntry.HistoryEntry entry) {
      return getStatusColor(entry.getStatus());
   }

   private static String getStatusColor(OneFruitEntry.Status status) {
      String coloredStatus = status.name();
      if (status == OneFruitEntry.Status.LOST) {
         coloredStatus = "§c" + status.name() + "§r";
      } else if (status == OneFruitEntry.Status.DROPPED) {
         coloredStatus = "§d" + status.name() + "§r";
      } else if (status == OneFruitEntry.Status.IN_USE || status == OneFruitEntry.Status.INVENTORY) {
         coloredStatus = "§a" + status.name() + "§r";
      }

      return coloredStatus;
   }

   private static Component getClickableFruitLocation(OneFruitEntry entry) {
      return buildTeleportComponent(entry.getDimensionLocation(), entry.getBlockPos());
   }

   private static Component getClickableFruitLocation(OneFruitEntry.HistoryEntry entry) {
      return buildTeleportComponent(entry.getDimensionLocation(), entry.getBlockPos());
   }

   private static Component buildTeleportComponent(Optional<ResourceLocation> dimension, Optional<BlockPos> position) {
      if (dimension.isPresent() && position.isPresent()) {
         ResourceLocation dim = (ResourceLocation)dimension.get();
         BlockPos pos = (BlockPos)position.get();
         MutableComponent var10000 = Component.m_237113_(" | [Teleport to fruit]");
         Style var10001 = Style.f_131099_.m_131140_(ChatFormatting.GREEN);
         ClickEvent.Action var10004 = Action.RUN_COMMAND;
         String var10005 = dim.toString();
         var10001 = var10001.m_131142_(new ClickEvent(var10004, "/execute in " + var10005 + " run tp " + pos.m_123341_() + " " + pos.m_123342_() + " " + pos.m_123343_()));
         HoverEvent.Action var5 = net.minecraft.network.chat.HoverEvent.Action.f_130831_;
         var10005 = dim.toString();
         return var10000.m_6270_(var10001.m_131144_(new HoverEvent(var5, Component.m_237113_("Teleport to " + var10005 + " at " + pos.m_123344_()))));
      } else {
         return Component.m_237113_("");
      }
   }
}
