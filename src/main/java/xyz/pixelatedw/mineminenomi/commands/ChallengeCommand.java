package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import xyz.pixelatedw.mineminenomi.api.commands.ChallengeArgument;

import java.util.Collection;
import java.util.List;

// Stubbed as Challenge Capability/Data has not been ported to attachments yet.
public class ChallengeCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal("challenge")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("start")
                        .then(Commands.argument("challenge", ChallengeArgument.challenge())
                                .executes(context -> startChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), List.of(context.getSource().getPlayerOrException())))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(context -> startChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), EntityArgument.getEntities(context, "targets")))
                                )
                        )
                )
                .then(Commands.literal("finish")
                        .then(Commands.argument("challenge", ChallengeArgument.challenge())
                                .executes(context -> finishChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), List.of(context.getSource().getPlayerOrException())))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(context -> finishChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), EntityArgument.getEntities(context, "targets")))
                                )
                        )
                )
                .then(Commands.literal("give")
                        .then(Commands.argument("challenge", ChallengeArgument.challenge())
                                .executes(context -> giveChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), List.of(context.getSource().getPlayerOrException())))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(context -> giveChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), EntityArgument.getEntities(context, "targets")))
                                )
                        )
                )
                .then(Commands.literal("ALL")
                        .then(Commands.argument("targets", EntityArgument.entities())
                                .executes(context -> giveAllChallenges(context, EntityArgument.getEntities(context, "targets")))
                        )
                )
                .then(Commands.literal("unfinish")
                        .then(Commands.argument("challenge", ChallengeArgument.challenge())
                                .executes(context -> unfinishChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), List.of(context.getSource().getPlayerOrException())))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(context -> unfinishChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), EntityArgument.getEntities(context, "targets")))
                                )
                        )
                )
                .then(Commands.literal("remove")
                        .then(Commands.argument("challenge", ChallengeArgument.challenge())
                                .executes(context -> removeChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), List.of(context.getSource().getPlayerOrException())))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(context -> removeChallenge(context, ChallengeArgument.getChallenge(context, "challenge"), EntityArgument.getEntities(context, "targets")))
                                )
                        )
                )
        );
    }

    private static int startChallenge(CommandContext<CommandSourceStack> context, Object core, Collection<? extends Entity> targets) {
        context.getSource().sendFailure(Component.literal("Challenge data not yet ported."));
        return 0;
    }

    private static int unfinishChallenge(CommandContext<CommandSourceStack> context, Object core, Collection<? extends Entity> targets) {
        context.getSource().sendFailure(Component.literal("Challenge data not yet ported."));
        return 0;
    }

    private static int finishChallenge(CommandContext<CommandSourceStack> context, Object core, Collection<? extends Entity> targets) {
        context.getSource().sendFailure(Component.literal("Challenge data not yet ported."));
        return 0;
    }

    private static int giveAllChallenges(CommandContext<CommandSourceStack> context, Collection<? extends Entity> targets) {
        context.getSource().sendFailure(Component.literal("Challenge data not yet ported."));
        return 0;
    }

    private static int giveChallenge(CommandContext<CommandSourceStack> context, Object core, Collection<? extends Entity> targets) {
        context.getSource().sendFailure(Component.literal("Challenge data not yet ported."));
        return 0;
    }

    private static int removeChallenge(CommandContext<CommandSourceStack> context, Object core, Collection<? extends Entity> targets) {
        context.getSource().sendFailure(Component.literal("Challenge data not yet ported."));
        return 0;
    }
}
