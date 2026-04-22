package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xyz.pixelatedw.mineminenomi.api.enums.TrainingPointType;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import java.util.Collection;

public class PointsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("points")
                .requires(source -> source.hasPermission(2));

        for (TrainingPointType type : TrainingPointType.values()) {
            builder.then(Commands.literal(type.name().toLowerCase())
                    .then(Commands.argument("targets", EntityArgument.players())
                            .then(Commands.literal("add")
                                    .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                            .executes(ctx -> alterPoints(ctx, type, IntegerArgumentType.getInteger(ctx, "amount")))))
                            .then(Commands.literal("set")
                                    .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                            .executes(ctx -> setPoints(ctx, type, IntegerArgumentType.getInteger(ctx, "amount")))))
                            .then(Commands.literal("remove")
                                    .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                            .executes(ctx -> alterPoints(ctx, type, -IntegerArgumentType.getInteger(ctx, "amount")))))));
        }

        dispatcher.register(builder);
    }

    private static int alterPoints(CommandContext<CommandSourceStack> context, TrainingPointType type, int amount) {
        try {
            Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "targets");
            for (ServerPlayer player : targets) {
                PlayerStats stats = PlayerStats.get(player);
                stats.alterTrainingPoints(type, amount);
                stats.sync(player);
            }
            context.getSource().sendSuccess(() -> Component.literal("Altered " + type.name() + " points for " + targets.size() + " players"), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    private static int setPoints(CommandContext<CommandSourceStack> context, TrainingPointType type, int amount) {
        try {
            Collection<ServerPlayer> targets = EntityArgument.getPlayers(context, "targets");
            for (ServerPlayer player : targets) {
                PlayerStats stats = PlayerStats.get(player);
                stats.setTrainingPoints(type, amount);
                stats.sync(player);
            }
            context.getSource().sendSuccess(() -> Component.literal("Set " + type.name() + " points to " + amount + " for " + targets.size() + " players"), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
