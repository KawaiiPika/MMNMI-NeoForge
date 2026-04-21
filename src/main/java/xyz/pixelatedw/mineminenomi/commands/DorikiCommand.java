package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import java.util.Collection;
import java.util.List;

public class DorikiCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal("doriki")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("amount", IntegerArgumentType.integer(-1000000, 1000000))
                        .executes(context -> alterDoriki(context, IntegerArgumentType.getInteger(context, "amount"), List.of(context.getSource().getPlayerOrException())))
                        .then(Commands.argument("targets", EntityArgument.entities())
                                .executes(context -> alterDoriki(context, IntegerArgumentType.getInteger(context, "amount"), EntityArgument.getEntities(context, "targets")))
                        )
                )
        );
    }

    private static int alterDoriki(CommandContext<CommandSourceStack> context, int amount, Collection<? extends Entity> targets) {
        for (Entity target : targets) {
            if (target instanceof LivingEntity livingTarget) {
                PlayerStats stats = PlayerStats.get(livingTarget);
                if (stats != null) {
                    double newDoriki = Math.max(0, stats.getDoriki() + amount);
                    stats.setDoriki(newDoriki);
                    stats.sync(livingTarget);

                    context.getSource().sendSuccess(() -> Component.literal((amount > 0 ? "+" : "") + amount + " doriki for " + livingTarget.getName().getString()).withStyle(ChatFormatting.GREEN), true);
                }
            }
        }
        return 1;
    }
}
