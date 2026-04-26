package xyz.pixelatedw.mineminenomi.commands;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.Collection;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import xyz.pixelatedw.mineminenomi.api.enums.StatChangeSource;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModDataAttachments;

public class LoyaltyCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("loyalty").requires(source -> source.hasPermission(2));
        int min = -100;
        int max = 100;

        builder.then(Commands.argument("amount", IntegerArgumentType.integer(min, max))
                .executes((context) -> alterLoyalty(context, IntegerArgumentType.getInteger(context, "amount"), getDefaultCollection(context)))
                .then(Commands.argument("targets", EntityArgument.players())
                        .executes((context) -> alterLoyalty(context, IntegerArgumentType.getInteger(context, "amount"), EntityArgument.getPlayers(context, "targets")))));

        dispatcher.register(builder);
    }

    private static Collection<ServerPlayer> getDefaultCollection(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return Lists.newArrayList(context.getSource().getPlayerOrException());
    }

    private static int alterLoyalty(CommandContext<CommandSourceStack> context, int amount, Collection<ServerPlayer> targets) throws CommandSyntaxException {
        for (ServerPlayer player : targets) {
            PlayerStats props = player.getData(ModDataAttachments.PLAYER_STATS);
            boolean hasChanged = props.alterLoyalty((double)amount, StatChangeSource.COMMAND);
            if (hasChanged) {
                player.setData(ModDataAttachments.PLAYER_STATS, props);
                props.sync(player);
                context.getSource().sendSuccess(() -> Component.literal(String.valueOf(ChatFormatting.GREEN) + (amount > 0 ? "+" : "") + amount + " loyalty for " + player.getDisplayName().getString()), true);
            }
        }
        return 1;
    }
}
