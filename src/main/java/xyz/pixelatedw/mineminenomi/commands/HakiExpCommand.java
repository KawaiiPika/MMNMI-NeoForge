package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
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

public class HakiExpCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal("hakiexp")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("busoshoku")
                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0, 100))
                                .executes(context -> setHakiExp(context, "buso", FloatArgumentType.getFloat(context, "amount"), List.of(context.getSource().getPlayerOrException())))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(context -> setHakiExp(context, "buso", FloatArgumentType.getFloat(context, "amount"), EntityArgument.getEntities(context, "targets")))
                                )
                        )
                )
                .then(Commands.literal("kenbunshoku")
                        .then(Commands.argument("amount", FloatArgumentType.floatArg(0, 100))
                                .executes(context -> setHakiExp(context, "kenbun", FloatArgumentType.getFloat(context, "amount"), List.of(context.getSource().getPlayerOrException())))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(context -> setHakiExp(context, "kenbun", FloatArgumentType.getFloat(context, "amount"), EntityArgument.getEntities(context, "targets")))
                                )
                        )
                )
        );
    }

    private static int setHakiExp(CommandContext<CommandSourceStack> context, String type, float amount, Collection<? extends Entity> targets) {
        for (Entity target : targets) {
            if (target instanceof LivingEntity livingTarget) {
                PlayerStats stats = PlayerStats.get(livingTarget);
                if (stats != null) {
                    if (type.equals("buso")) {
                        stats.setBusoshokuHakiExp(amount);
                    } else if (type.equals("kenbun")) {
                        stats.setKenbunshokuHakiExp(amount);
                    }
                    stats.sync(livingTarget);

                    context.getSource().sendSuccess(() -> Component.literal("Set " + type + " haki exp to " + amount + " for " + livingTarget.getName().getString()).withStyle(ChatFormatting.GREEN), true);
                }
            }
        }
        return 1;
    }
}
