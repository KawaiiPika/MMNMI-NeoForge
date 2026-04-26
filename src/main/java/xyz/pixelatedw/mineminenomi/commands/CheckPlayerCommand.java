package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.api.factions.IFactionRank;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

public class CheckPlayerCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("check_player").requires(source -> source.hasPermission(2));

        builder.executes((context) -> checkPlayer(context, context.getSource().getPlayerOrException(), true))
                .then(Commands.argument("target", EntityArgument.player())
                        .executes((context) -> checkPlayer(context, EntityArgument.getPlayer(context, "target"), true))
                        .then(Commands.argument("show_attributes", BoolArgumentType.bool())
                                .executes((context) -> checkPlayer(context, EntityArgument.getPlayer(context, "target"), BoolArgumentType.getBool(context, "show_attributes")))));

        dispatcher.register(builder);
    }

    private static int checkPlayer(CommandContext<CommandSourceStack> context, ServerPlayer player, boolean showAttributes) {
        try {
            PlayerStats statsData = player.getData(ModDataAttachments.PLAYER_STATS);
            if (statsData == null) {
                return -1;
            }

            FactionsWorldData worldData = FactionsWorldData.get();
            Crew crew = worldData != null ? worldData.getCrewWithMember(player.getUUID()) : null;

            StringBuilder builder = new StringBuilder();
            builder.append("===============================================\n");

            String factionName = statsData.getFaction().map(f -> f.toString()).orElse("N/A"); // TODO: Use proper localized name if Faction registry is ported
            String raceName = statsData.getRace().map(r -> r.toString()).orElse("N/A"); // TODO: localized name
            String styleName = statsData.getFightingStyle().map(s -> s.toString()).orElse("N/A"); // TODO: localized name

            builder.append("Name: ").append(player.getDisplayName().getString()).append("\n");
            builder.append("Faction: ").append(factionName).append("\n");

            // TODO: if (statsData.isMarine() || statsData.isRevolutionary())
            builder.append("Loyalty: ").append(statsData.getLoyalty()).append("\n");
            // String rankLabel = statsData.getRank().map(rank -> ((IFactionRank)rank).getLocalizedName().getString()).orElse("N/A");
            // builder.append("Rank: ").append(rankLabel).append("\n");

            builder.append("Race: ").append(raceName).append("\n");
            // TODO: isCyborg maxCola ultraCola

            builder.append("Style: ").append(styleName).append("\n");
            builder.append("Doriki: ").append(statsData.getDoriki()).append("\n");
            builder.append("Belly: ").append(statsData.getBelly()).append("\n");
            builder.append("Extol: ").append(statsData.getExtol()).append("\n");

            // TODO: canGainBounty
            builder.append("Bounty: ").append(statsData.getBounty()).append("\n");
            if (worldData != null) {
                long issuedBounty = worldData.getBounty(player.getUUID());
                builder.append("Issued Bounty: ").append(issuedBounty).append("\n");
            }

            // if (statsData.isPirate())
            String crewName = crew != null ? crew.getName() : "None";
            builder.append("Crew: ").append(crewName).append("\n");

            String fruitName = statsData.hasDevilFruit() ? statsData.getDevilFruit().get().toString() : "None";
            builder.append("Devil Fruit: ").append(fruitName).append("\n");

            // TODO: damageMultiplier

            AttributeInstance toughnessAttribute = player.getAttribute(ModAttributes.TOUGHNESS);
            AttributeInstance armorAttribute = player.getAttribute(Attributes.ARMOR);

            if (toughnessAttribute != null && armorAttribute != null) {
                double toughness = toughnessAttribute.getValue() / 100.0; // TODO: handle ranged attributes properly
                double armor = armorAttribute.getValue() / 100.0;
                toughness *= 0.6;
                armor *= 0.4;
                double totalDef = toughness + armor;
                boolean isCapped = totalDef > 0.95F;
                builder.append(String.format("Protection: %s%% (T:%s%% A:%s%%) %s \n",
                    String.format("%.2f", totalDef * 100.0F),
                    String.format("%.2f", toughness * 100.0F),
                    String.format("%.2f", armor * 100.0F),
                    isCapped ? "(overflowing past limit)" : ""));
            }

            for (TrainingPointType pointType : TrainingPointType.values()) {
                builder.append(pointType.name()).append(" Training Points: ").append(statsData.getTrainingPoints(pointType)).append("\n");
            }

            // builder.append("In Combat ?: ").append(WyHelper.isInCombat(player)).append("\n");
            // builder.append("Combat Cache Timer: ").append(combatData.getLastAttackTime()).append("\n");

            if (showAttributes) {
                builder.append("-----\n");
                builder.append("§2Attribute | Current Value / Base Value§r\n");

                for (Attribute attr : BuiltInRegistries.ATTRIBUTE) {
                    AttributeInstance modInst = player.getAttributes().getInstance(BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attr));
                    if (modInst != null && modInst.getValue() != modInst.getBaseValue()) {
                        String attrName = attr.getDescriptionId();
                        builder.append("- §9").append(attrName).append(" | ").append(modInst.getValue()).append("/").append(modInst.getBaseValue()).append("§r\n");

                        for (AttributeModifier mod : modInst.getModifiers()) {
                            builder.append("  ").append(mod.id().toString()).append(" | ").append(mod.amount()).append("\n");
                        }
                    }
                }
            }

            builder.append("===============================================");
            context.getSource().sendSuccess(() -> Component.literal(builder.toString()), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
