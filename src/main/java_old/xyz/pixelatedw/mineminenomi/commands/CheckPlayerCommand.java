package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.api.helpers.BountyHelper;
import xyz.pixelatedw.mineminenomi.data.entity.combat.CombatCapability;
import xyz.pixelatedw.mineminenomi.data.entity.combat.ICombatData;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.IDevilFruit;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModPermissions;

public class CheckPlayerCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> create() {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("check_player").requires(Requires.hasPermission(ModPermissions.CHECK_PLAYER_COMMAND));
      ((LiteralArgumentBuilder)builder.executes((context) -> checkPlayer(context, ((CommandSourceStack)context.getSource()).m_81375_(), true))).then(Commands.m_82129_("target", EntityArgument.m_91466_()).then(Commands.m_82129_("show_attributes", BoolArgumentType.bool()).executes((context) -> checkPlayer(context, EntityArgument.m_91474_(context, "target"), BoolArgumentType.getBool(context, "show_attributes")))));
      return builder;
   }

   private static int checkPlayer(CommandContext<CommandSourceStack> context, ServerPlayer player, boolean showAttributes) {
      try {
         IEntityStats statsData = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         ICombatData combatData = (ICombatData)CombatCapability.get(player).orElse((Object)null);
         IDevilFruit fruitData = (IDevilFruit)DevilFruitCapability.get(player).orElse((Object)null);
         IHakiData hakiData = (IHakiData)HakiCapability.get(player).orElse((Object)null);
         if (statsData == null || combatData == null || fruitData == null || hakiData == null) {
            return -1;
         }

         FactionsWorldData worldData = FactionsWorldData.get();
         Crew crew = worldData.getCrewWithMember(player.m_20148_());
         StringBuilder builder = new StringBuilder();
         builder.append("===============================================\n");
         String factionName = (String)statsData.getFaction().map((f) -> f.getLabel().getString()).orElse("N/A");
         String raceName = (String)statsData.getRace().map((f) -> f.getLabel().getString()).orElse("N/A");
         String styleName = (String)statsData.getFightingStyle().map((f) -> f.getLabel().getString()).orElse("N/A");
         builder.append("Name: " + player.m_5446_().getString() + "\n");
         builder.append("Faction: " + factionName + "\n");
         if (statsData.isMarine() || statsData.isRevolutionary()) {
            builder.append("Loyalty: " + statsData.getLoyalty() + "\n");
            String rankLabel = (String)statsData.getRank().map((rank) -> ((IFactionRank)rank).getLocalizedName().getString()).orElse("N/A");
            builder.append("Rank: " + rankLabel + "\n");
         }

         builder.append("Race: " + raceName + "\n");
         if (statsData.isCyborg()) {
            builder.append("Max Cola: " + statsData.getMaxCola() + "\n");
            builder.append("Ultra Cola: " + statsData.getUltraCola() + "\n");
         }

         builder.append("Style: " + styleName + "\n");
         builder.append("Doriki: " + statsData.getDoriki() + "\n");
         builder.append("Belly: " + statsData.getBelly() + "\n");
         builder.append("Extol: " + statsData.getExtol() + "\n");
         if (BountyHelper.canGainBounty(player)) {
            builder.append("Bounty: " + statsData.getBounty() + "\n");
            long var10001 = worldData.getBounty(player.m_20148_());
            builder.append("Issued Bounty: " + var10001 + "\n");
         }

         if (statsData.isPirate()) {
            String var36 = crew != null ? crew.getName() : "None";
            builder.append("Crew: " + var36 + "\n");
         }

         ItemStack fruitStack = new ItemStack(fruitData.getDevilFruitItem());
         String var37 = !fruitStack.m_41619_() ? fruitStack.m_41786_().getString() : "None";
         builder.append("Devil Fruit: " + var37 + "\n");
         double var38 = statsData.getDamageMultiplier();
         builder.append("Damage Multiplier: " + var38 + "\n");
         AttributeInstance toughnessAttribute = player.m_21051_((Attribute)ModAttributes.TOUGHNESS.get());
         AttributeInstance armorAttribute = player.m_21051_(Attributes.f_22284_);
         double toughness = toughnessAttribute.m_22135_() / ((RangedAttribute)toughnessAttribute.m_22099_()).m_147362_();
         double armor = armorAttribute.m_22135_() / ((RangedAttribute)armorAttribute.m_22099_()).m_147362_();
         toughness *= 0.6;
         armor *= 0.4;
         double totalDef = toughness + armor;
         boolean isCapped = totalDef > (double)0.95F;
         builder.append(String.format("Protection: %s%% (T:%s%% A:%s%%) %s \n", String.format("%.2f", totalDef * (double)100.0F), String.format("%.2f", toughness * (double)100.0F), String.format("%.2f", armor * (double)100.0F), isCapped ? "(overflowing past limit)" : ""));

         for(TrainingPointType pointType : TrainingPointType.values()) {
            String var39 = pointType.name();
            builder.append(var39 + " Training Points: " + statsData.getTrainingPoints(pointType) + "\n");
         }

         if (WyDebug.isDebug()) {
            builder.append("In Combat ?: " + WyHelper.isInCombat(player) + "\n");
            builder.append("Combat Cache Timer: " + combatData.getLastAttackTime() + "\n");
         }

         if (showAttributes) {
            builder.append("-----\n");
            builder.append("§2Attribute | Current Value / Base Value§r\n");

            for(Attribute attr : ForgeRegistries.ATTRIBUTES.getValues()) {
               AttributeInstance modInst = player.m_21204_().m_22146_(attr);
               if (modInst != null && modInst.m_22135_() != modInst.m_22115_()) {
                  String var40 = modInst.m_22099_().m_22087_();
                  builder.append("- §9" + var40 + " | " + modInst.m_22135_() + "/" + modInst.m_22115_() + "§r\n");

                  for(AttributeModifier mod : modInst.m_22122_()) {
                     var40 = mod.m_22214_();
                     builder.append("  " + var40 + " | " + mod.m_22218_() + "\n");
                  }
               }
            }
         }

         builder.append("===============================================");
         ((CommandSourceStack)context.getSource()).m_288197_(() -> Component.m_237113_(builder.toString()), true);
      } catch (Exception e) {
         e.printStackTrace();
      }

      return 1;
   }
}
