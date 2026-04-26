package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.commands.QuestArgument;
import xyz.pixelatedw.mineminenomi.api.quests.QuestId;

import java.util.Collection;
import java.util.List;

// Stubbed as Quest Capability/Data has not been ported to attachments yet.
public class QuestCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal("quest")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("finish")
                        .then(Commands.argument("quest", QuestArgument.quest())
                                .executes(context -> finishQuest(context, QuestArgument.getQuest(context, "quest"), List.of(context.getSource().getPlayerOrException())))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(context -> finishQuest(context, QuestArgument.getQuest(context, "quest"), EntityArgument.getEntities(context, "targets")))
                                )
                        )
                )
                .then(Commands.literal("give")
                        .then(Commands.argument("quest", QuestArgument.quest())
                                .executes(context -> giveQuest(context, QuestArgument.getQuest(context, "quest"), List.of(context.getSource().getPlayerOrException())))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(context -> giveQuest(context, QuestArgument.getQuest(context, "quest"), EntityArgument.getEntities(context, "targets")))
                                )
                        )
                )
                .then(Commands.literal("unfinish")
                        .then(Commands.argument("quest", QuestArgument.quest())
                                .executes(context -> unfinishQuest(context, QuestArgument.getQuest(context, "quest"), List.of(context.getSource().getPlayerOrException())))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(context -> unfinishQuest(context, QuestArgument.getQuest(context, "quest"), EntityArgument.getEntities(context, "targets")))
                                )
                        )
                )
                .then(Commands.literal("remove")
                        .then(Commands.argument("quest", QuestArgument.quest())
                                .executes(context -> removeQuest(context, QuestArgument.getQuest(context, "quest"), List.of(context.getSource().getPlayerOrException())))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(context -> removeQuest(context, QuestArgument.getQuest(context, "quest"), EntityArgument.getEntities(context, "targets")))
                                )
                        )
                )
        );
    }

    private static int unfinishQuest(CommandContext<CommandSourceStack> context, QuestId<?> quest, Collection<? extends Entity> targets) {
        // Stub
        context.getSource().sendFailure(Component.literal("Quest data not yet ported."));
        return 0;
    }

    private static int finishQuest(CommandContext<CommandSourceStack> context, QuestId<?> questId, Collection<? extends Entity> targets) {
        // Stub
        context.getSource().sendFailure(Component.literal("Quest data not yet ported."));
        return 0;
    }

    private static int giveQuest(CommandContext<CommandSourceStack> context, QuestId<?> quest, Collection<? extends Entity> targets) {
        // Stub
        context.getSource().sendFailure(Component.literal("Quest data not yet ported."));
        return 0;
    }

    private static int removeQuest(CommandContext<CommandSourceStack> context, QuestId<?> quest, Collection<? extends Entity> targets) {
        // Stub
        context.getSource().sendFailure(Component.literal("Quest data not yet ported."));
        return 0;
    }
}
