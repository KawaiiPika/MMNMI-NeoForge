package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
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

public class HaoshokuCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal("haoshoku")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("targets", EntityArgument.entities())
                        .then(Commands.argument("override", BoolArgumentType.bool())
                                .executes(context -> setHaoshokuOverride(context, BoolArgumentType.getBool(context, "override"), EntityArgument.getEntities(context, "targets")))
                        )
                )
        );
    }

    private static int setHaoshokuOverride(CommandContext<CommandSourceStack> context, boolean override, Collection<? extends Entity> targets) {
        int count = 0;
        for (Entity target : targets) {
            if (target instanceof LivingEntity livingTarget) {
                PlayerStats stats = PlayerStats.get(livingTarget);
                if (stats != null) {
                    stats.setHaoshokuBornOverride(override);
                    stats.sync(livingTarget);
                    count++;

                    context.getSource().sendSuccess(() -> Component.literal("Set Haoshoku Haki born override to " + override + " for " + livingTarget.getName().getString()).withStyle(ChatFormatting.GREEN), true);
                }
            }
        }
        return count;
    }
}
