package xyz.pixelatedw.mineminenomi.commands;

import com.google.common.base.Strings;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.util.DBlockPos;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.handlers.entity.PatreonHandler;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;
import xyz.pixelatedw.mineminenomi.packets.server.SViewProtectionPacket;

public class AbilityProtectionCommand {
   private static final int MAX_SIZE = 59999999;
   private static final String RESET = "§r";

   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("abilityprotection").requires(Requires.hasPermission(ModPermissions.ABILITY_PROTECTION_COMMAND));
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)builder.then(Commands.m_82127_("new").then(Commands.m_82129_("size", IntegerArgumentType.integer(1, 59999999)).then(Commands.m_82129_("label", StringArgumentType.string()).executes((context) -> createProtection(context, IntegerArgumentType.getInteger(context, "size"), StringArgumentType.getString(context, "label"))))))).then(Commands.m_82127_("resize").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("size", IntegerArgumentType.integer(1, 59999999)).executes((context) -> resizeProtection(context, StringArgumentType.getString(context, "label"), IntegerArgumentType.getInteger(context, "size"))))))).then(Commands.m_82127_("rename").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("new_label", StringArgumentType.string()).executes((context) -> renameProtection(context, StringArgumentType.getString(context, "label"), StringArgumentType.getString(context, "new_label"))))))).then(Commands.m_82127_("info").then(Commands.m_82129_("label", StringArgumentType.string()).executes((context) -> printAreaInfo(context, StringArgumentType.getString(context, "label")))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("props").then(Commands.m_82127_("block_destruction").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("state", BoolArgumentType.bool()).executes((context) -> changeBlockDestructionProp(context, StringArgumentType.getString(context, "label"), BoolArgumentType.getBool(context, "state"))))))).then(Commands.m_82127_("entity_damage").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("state", BoolArgumentType.bool()).executes((context) -> changeEntityDamageProp(context, StringArgumentType.getString(context, "label"), BoolArgumentType.getBool(context, "state"))))))).then(Commands.m_82127_("player_damage").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("state", BoolArgumentType.bool()).executes((context) -> changePlayerDamageProp(context, StringArgumentType.getString(context, "label"), BoolArgumentType.getBool(context, "state"))))))).then(Commands.m_82127_("block_restoration").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("state", BoolArgumentType.bool()).executes((context) -> changeBlockRestorationProp(context, StringArgumentType.getString(context, "label"), BoolArgumentType.getBool(context, "state"))))))).then(Commands.m_82127_("abilities_use").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("state", BoolArgumentType.bool()).executes((context) -> changeAbilitiesUsageProp(context, StringArgumentType.getString(context, "label"), BoolArgumentType.getBool(context, "state"))))))).then(Commands.m_82127_("stat_loss").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("state", BoolArgumentType.bool()).executes((context) -> changeStatLossProp(context, StringArgumentType.getString(context, "label"), BoolArgumentType.getBool(context, "state"))))))).then(Commands.m_82127_("death").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("state", BoolArgumentType.bool()).executes((context) -> changeDeathProp(context, StringArgumentType.getString(context, "label"), BoolArgumentType.getBool(context, "state"))))))).then(Commands.m_82127_("mob_spawns").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("state", BoolArgumentType.bool()).executes((context) -> changeMobSpawnsProp(context, StringArgumentType.getString(context, "label"), BoolArgumentType.getBool(context, "state"))))))).then(Commands.m_82127_("unconscious_time").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("time", IntegerArgumentType.integer(0, 1200)).executes((context) -> changeUnconsciousTimeProp(context, StringArgumentType.getString(context, "label"), IntegerArgumentType.getInteger(context, "time"))))))).then(Commands.m_82127_("restoration_interval").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("interval", IntegerArgumentType.integer(0, 1200)).executes((context) -> changeRestorationIntervalProp(context, StringArgumentType.getString(context, "label"), IntegerArgumentType.getInteger(context, "interval"))))))).then(Commands.m_82127_("restoration_amount").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("amount", IntegerArgumentType.integer(1, 500)).executes((context) -> changeRestorationAmountProp(context, StringArgumentType.getString(context, "label"), IntegerArgumentType.getInteger(context, "amount"))))))).then(Commands.m_82127_("restoration_distance").then(Commands.m_82129_("label", StringArgumentType.string()).then(Commands.m_82129_("amount", IntegerArgumentType.integer(0, 1000)).executes((context) -> changeRestorationDistanceProp(context, StringArgumentType.getString(context, "label"), IntegerArgumentType.getInteger(context, "amount")))))))).then(Commands.m_82127_("view").then(Commands.m_82129_("state", BoolArgumentType.bool()).executes((context) -> viewProtection(context, BoolArgumentType.getBool(context, "state")))))).then(Commands.m_82127_("list").executes((context) -> listProtections(context)))).then(Commands.m_82127_("remove").then(Commands.m_82129_("label", StringArgumentType.string()).executes((context) -> deleteProtection(context, StringArgumentType.getString(context, "label")))));
      return builder;
   }

   private static int printAreaInfo(CommandContext<CommandSourceStack> ctx, String label) throws CommandSyntaxException {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      MutableComponent comp = Component.m_237113_("");
      comp.m_130946_("Label: " + area.getLabel() + "\n");
      comp.m_130946_("Size: " + area.getSize() + "\n");
      comp.m_130946_("Can Destroy Blocks: ");
      addInfoOption(comp, area.canDestroyBlocks());
      comp.m_130946_("Can Restore Blocks: ");
      addInfoOption(comp, area.canRestoreBlocks());
      comp.m_130946_("Restoration Amount: " + area.getRestoreAmount() + "\n");
      comp.m_130946_("Restoration Interval (ticks): " + area.getRestoreInterval() + "\n");
      comp.m_130946_("Can Hurt Entities: ");
      addInfoOption(comp, area.canHurtEntities());
      comp.m_130946_("Can Hurt Players: ");
      addInfoOption(comp, area.canHurtPlayers());
      comp.m_130946_("Can Use Abilities: ");
      addInfoOption(comp, area.canAbilitiesBeUsed());
      comp.m_130946_("Can Lose Stats: ");
      addInfoOption(comp, area.canLoseStats());
      comp.m_130946_("Can Die: ");
      addInfoOption(comp, area.canDie());
      comp.m_130946_("Can Mobs Spawn: ");
      addInfoOption(comp, area.canMobsSpawn());
      comp.m_130946_("Unconscious Time (ticks): " + area.getUnconsciousTime() + "\n");
      if (WyDebug.isDebug() || !PatreonHandler.isReleaseBuild()) {
         comp.m_130946_("Restoration Queue: " + area.getRestorationMap().size());
      }

      ((CommandSourceStack)ctx.getSource()).m_288197_(() -> comp, true);
      return 1;
   }

   private static MutableComponent addInfoOption(MutableComponent comp, boolean option) {
      Style button1Style = Style.f_131099_.m_131140_(option ? ChatFormatting.GREEN : ChatFormatting.RED);
      Style button2Style = Style.f_131099_.m_131140_(!option ? ChatFormatting.GREEN : ChatFormatting.RED);
      comp.m_7220_(Component.m_237113_("[true]").m_130948_(button1Style));
      comp.m_130946_(String.format("%s/", "§r"));
      comp.m_7220_(Component.m_237113_("[false]").m_130948_(button2Style));
      comp.m_130946_(String.format("%s\n", "§r"));
      return comp;
   }

   private static int changeRestorationAmountProp(CommandContext<CommandSourceStack> ctx, String label, int amount) {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      area.setRestoreAmount(amount);
      worldData.m_77762_();
      return 1;
   }

   private static int changeRestorationDistanceProp(CommandContext<CommandSourceStack> ctx, String label, int amount) {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      area.setRestoreDistance(amount);
      worldData.m_77762_();
      return 1;
   }

   private static int changeRestorationIntervalProp(CommandContext<CommandSourceStack> ctx, String label, int interval) {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      area.setRestoreInterval(interval);
      worldData.m_77762_();
      return 1;
   }

   private static int changeAbilitiesUsageProp(CommandContext<CommandSourceStack> ctx, String label, boolean state) {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      area.setAbilitiesUsage(state);
      worldData.m_77762_();
      return 1;
   }

   private static int changeBlockDestructionProp(CommandContext<CommandSourceStack> ctx, String label, boolean state) {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      area.setBlockDestruction(state);
      worldData.m_77762_();
      return 1;
   }

   private static int changeEntityDamageProp(CommandContext<CommandSourceStack> ctx, String label, boolean state) {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      area.setEntityDamage(state);
      worldData.m_77762_();
      return 1;
   }

   private static int changePlayerDamageProp(CommandContext<CommandSourceStack> ctx, String label, boolean state) {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      area.setPlayerDamage(state);
      worldData.m_77762_();
      return 1;
   }

   private static int changeBlockRestorationProp(CommandContext<CommandSourceStack> ctx, String label, boolean state) {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      area.setBlockRestoration(state);
      worldData.m_77762_();
      return 1;
   }

   private static int changeStatLossProp(CommandContext<CommandSourceStack> ctx, String label, boolean state) {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      area.setStatLoss(state);
      worldData.m_77762_();
      return 1;
   }

   private static int changeDeathProp(CommandContext<CommandSourceStack> ctx, String label, boolean state) {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      area.setDeath(state);
      worldData.m_77762_();
      return 1;
   }

   private static int changeMobSpawnsProp(CommandContext<CommandSourceStack> ctx, String label, boolean state) {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      area.setMobSpawns(state);
      worldData.m_77762_();
      return 1;
   }

   private static int changeUnconsciousTimeProp(CommandContext<CommandSourceStack> ctx, String label, int time) {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      area.setUnconsciousTime(time);
      worldData.m_77762_();
      return 1;
   }

   private static int resizeProtection(CommandContext<CommandSourceStack> ctx, String label, int size) throws CommandSyntaxException {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      worldData.resizeRestrictedArea(label, size);
      return 1;
   }

   private static int renameProtection(CommandContext<CommandSourceStack> ctx, String label, String newLabel) throws CommandSyntaxException {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      ProtectedArea area = worldData.getProtectedArea(label);
      worldData.removeRestrictedArea(label);
      area.setLabel(newLabel);
      worldData.addRestrictedArea(area);
      return 1;
   }

   private static int deleteProtection(CommandContext<CommandSourceStack> ctx, String label) throws CommandSyntaxException {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      worldData.removeRestrictedArea(label);
      return 1;
   }

   private static int listProtections(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      MutableComponent list = Component.m_237113_("Protection Sites: \n\n").m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GREEN));
      int i = 1;

      for(ProtectedArea area : worldData.getAllRestrictions().values()) {
         int midX = area.getCenter().m_123341_();
         int midY = area.getCenter().m_123342_();
         int midZ = area.getCenter().m_123343_();
         String label = "";
         if (!Strings.isNullOrEmpty(area.getLabel())) {
            label = "(" + area.getLabel() + ")";
         }

         Style style = Style.f_131099_.m_131142_(new ClickEvent(Action.RUN_COMMAND, "/tp @p " + midX + " " + midY + " " + midZ));
         list.m_7220_(Component.m_237113_(i + ". " + label + " " + midX + ", " + midY + ", " + midZ + "\n").m_6270_(style.m_131140_(i % 2 == 0 ? ChatFormatting.GREEN : ChatFormatting.DARK_GREEN)));
         ++i;
      }

      ((CommandSourceStack)ctx.getSource()).m_288197_(() -> list, true);
      return 1;
   }

   private static int viewProtection(CommandContext<CommandSourceStack> ctx, boolean state) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)ctx.getSource()).m_81375_();
      ModNetwork.sendTo(new SViewProtectionPacket(((CommandSourceStack)ctx.getSource()).m_81372_(), state), player);
      return 1;
   }

   private static int createProtection(CommandContext<CommandSourceStack> ctx, int size, String label) throws CommandSyntaxException {
      ServerLevel world = ((CommandSourceStack)ctx.getSource()).m_81372_();
      ProtectedAreasData worldData = ProtectedAreasData.get(((CommandSourceStack)ctx.getSource()).m_81372_());
      if (worldData.getProtectedArea(label) != null) {
         ((CommandSourceStack)ctx.getSource()).m_81352_(Component.m_237113_("Protection with this name already exists!"));
         return -1;
      } else {
         Vec3 vec = ((CommandSourceStack)ctx.getSource()).m_81371_();
         DBlockPos pos = new DBlockPos((int)vec.f_82479_, (int)vec.f_82480_, (int)vec.f_82481_, world.m_46472_());
         worldData.addRestrictedArea(pos, size, label);
         StringBuilder builder = new StringBuilder();
         builder.append("Created an ability protection zone named " + label + " at " + pos.m_123341_() + " " + pos.m_123342_() + " " + pos.m_123343_() + " of size " + size);
         ((CommandSourceStack)ctx.getSource()).m_288197_(() -> Component.m_237113_(builder.toString()), true);
         return 1;
      }
   }
}
