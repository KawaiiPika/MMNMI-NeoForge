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
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;

import java.util.Collection;
import java.util.List;

public class RemoveDFCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal("removedf")
                .requires(source -> source.hasPermission(2))
                .executes(context -> removeDF(context, List.of(context.getSource().getPlayerOrException())))
                .then(Commands.argument("targets", EntityArgument.entities())
                        .executes(context -> removeDF(context, EntityArgument.getEntities(context, "targets")))
                )
        );
    }

    private static int removeDF(CommandContext<CommandSourceStack> context, Collection<? extends Entity> targets) {
        for (Entity target : targets) {
            if (target instanceof LivingEntity livingTarget) {
                PlayerStats stats = PlayerStats.get(livingTarget);
                if (stats != null) {
                    stats.setDevilFruit(null);
                    stats.setLogia(false);
                    // Clear equipped abilities associated with the fruit
                    for (int i = 0; i < 24; i++) {
                        String abilityId = stats.getEquippedAbility(i);
                        if (abilityId != null && !abilityId.isEmpty()) {
                            // If we needed more precise logic, we'd check if the ability was from the DF.
                            // For now, removing the DF clears active/equipped abilities.
                            stats.setEquippedAbility(i, null);
                            stats.setAbilityActive(abilityId, false);
                        }
                    }
                    stats.sync(livingTarget);

                    context.getSource().sendSuccess(() -> Component.literal("Removed Devil Fruit from " + livingTarget.getName().getString()).withStyle(ChatFormatting.GREEN), true);
                }
            }
        }
        return 1;
    }
}
