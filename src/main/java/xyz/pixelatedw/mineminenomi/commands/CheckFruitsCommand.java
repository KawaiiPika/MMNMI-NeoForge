package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import xyz.pixelatedw.mineminenomi.api.commands.FruitArgument;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;

public class CheckFruitsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal("check_fruits")
                .requires(source -> source.hasPermission(2))
                .executes(CheckFruitsCommand::checkFruitsInWorld)
                .then(Commands.literal("history")
                        .then(Commands.argument("fruit", FruitArgument.fruit())
                                .executes(context -> fruitHistory(context, FruitArgument.getFruit(context, "fruit"), 0))
                                .then(Commands.argument("page", IntegerArgumentType.integer(0))
                                        .executes(context -> fruitHistory(context, FruitArgument.getFruit(context, "fruit"), IntegerArgumentType.getInteger(context, "page")))
                                )
                        )
                )
                .then(Commands.literal("export")
                        .then(Commands.argument("fruit", FruitArgument.fruit())
                                .executes(context -> exportFruitHistory(context, FruitArgument.getFruit(context, "fruit")))
                        )
                )
        );
    }

    private static int exportFruitHistory(CommandContext<CommandSourceStack> context, AkumaNoMiItem fruit) {
        // Stub: OneFruitWorldData and associated config logic has not been fully ported
        context.getSource().sendFailure(Component.literal("OneFruit system not fully ported."));
        return 0;
    }

    private static int fruitHistory(CommandContext<CommandSourceStack> context, AkumaNoMiItem fruit, int page) {
        // Stub: OneFruitWorldData and associated config logic has not been fully ported
        context.getSource().sendFailure(Component.literal("OneFruit system not fully ported."));
        return 0;
    }

    private static int checkFruitsInWorld(CommandContext<CommandSourceStack> context) {
        // Stub: OneFruitWorldData and associated config logic has not been fully ported
        context.getSource().sendFailure(Component.literal("OneFruit system not fully ported."));
        return 0;
    }
}
