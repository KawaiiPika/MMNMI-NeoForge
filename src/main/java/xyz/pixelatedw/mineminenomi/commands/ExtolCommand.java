package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import java.util.Collection;
import java.util.List;

public class ExtolCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal("extol")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("amount", LongArgumentType.longArg(-1000000000L, 1000000000L))
                        .executes(context -> alterExtol(context, LongArgumentType.getLong(context, "amount"), List.of(context.getSource().getPlayerOrException())))
                        .then(Commands.argument("targets", EntityArgument.entities())
                                .executes(context -> alterExtol(context, LongArgumentType.getLong(context, "amount"), EntityArgument.getEntities(context, "targets")))
                        )
                )
        );
    }

    private static int alterExtol(CommandContext<CommandSourceStack> context, long amount, Collection<? extends Entity> targets) {
        for (Entity target : targets) {
            if (target instanceof LivingEntity livingTarget) {
                PlayerStats stats = PlayerStats.get(livingTarget);
                if (stats != null) {
                    stats.alterExtol(amount);
                    stats.sync(livingTarget);

                    context.getSource().sendSuccess(() -> Component.literal((amount > 0 ? "+" : "") + amount + " extol for " + livingTarget.getName().getString()).withStyle(ChatFormatting.GREEN), true);
                }
            }
        }
        return 1;
    }
}
