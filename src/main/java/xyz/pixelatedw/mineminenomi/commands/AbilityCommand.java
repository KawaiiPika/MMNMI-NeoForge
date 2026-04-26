package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.commands.AbilityArgument;
import xyz.pixelatedw.mineminenomi.data.entity.PlayerStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilities;

import java.util.Collection;
import java.util.List;

public class AbilityCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(Commands.literal("ability")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("give")
                        .then(Commands.argument("ability", AbilityArgument.ability())
                                .executes(context -> addAbility(context, AbilityArgument.getAbility(context, "ability"), List.of(context.getSource().getPlayerOrException())))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(context -> addAbility(context, AbilityArgument.getAbility(context, "ability"), EntityArgument.getEntities(context, "targets")))
                                )
                        )
                )
                .then(Commands.literal("remove")
                        .then(Commands.argument("ability", AbilityArgument.ability())
                                .executes(context -> removeAbility(context, AbilityArgument.getAbility(context, "ability"), List.of(context.getSource().getPlayerOrException())))
                                .then(Commands.argument("targets", EntityArgument.entities())
                                        .executes(context -> removeAbility(context, AbilityArgument.getAbility(context, "ability"), EntityArgument.getEntities(context, "targets")))
                                )
                        )
                )
                .then(Commands.literal("reset_cooldown")
                        .executes(context -> resetCooldown(context, List.of(context.getSource().getPlayerOrException())))
                        .then(Commands.argument("targets", EntityArgument.entities())
                                .executes(context -> resetCooldown(context, EntityArgument.getEntities(context, "targets")))
                        )
                )
                .then(Commands.literal("awaken")
                        .executes(context -> awakeFruit(context, List.of(context.getSource().getPlayerOrException())))
                        .then(Commands.argument("targets", EntityArgument.entities())
                                .executes(context -> awakeFruit(context, EntityArgument.getEntities(context, "targets")))
                        )
                )
        );
    }

    private static int addAbility(CommandContext<CommandSourceStack> context, Ability ability, Collection<? extends Entity> targets) {
        if (ability == null) return -1;
        ResourceLocation id = ability.getAbilityId();

        for (Entity target : targets) {
            if (target instanceof LivingEntity livingTarget) {
                PlayerStats stats = PlayerStats.get(livingTarget);
                if (stats != null) {
                    stats.grantAbility(id);
                    stats.sync(livingTarget);

                    String abilityName = id.getPath();
                    context.getSource().sendSuccess(() -> Component.literal("Gave ability " + abilityName + " to " + livingTarget.getName().getString()).withStyle(ChatFormatting.GREEN), true);
                }
            }
        }
        return 1;
    }

    private static int removeAbility(CommandContext<CommandSourceStack> context, Ability ability, Collection<? extends Entity> targets) {
        if (ability == null) return -1;
        ResourceLocation id = ability.getAbilityId();
        String idStr = id.toString();

        for (Entity target : targets) {
            if (target instanceof LivingEntity livingTarget) {
                PlayerStats stats = PlayerStats.get(livingTarget);
                if (stats != null) {
                    List<String> list = new java.util.ArrayList<>(stats.getCombat().equippedAbilities());
                    List<String> activeList = new java.util.ArrayList<>(stats.getCombat().activeAbilities());
                    activeList.remove(idStr);
                    int index = list.indexOf(idStr);
                    if (index != -1) {
                        stats.setEquippedAbility(index, null);
                        stats.setAbilityActive(idStr, false);

                        // Also fully revoke the ability in a proper setup. Since Ability capability
                        // was absorbed into CombatStats, we don't have a separate "unlockedAbilities" list yet,
                        // so removing from equipped and active represents removal.

                        stats.sync(livingTarget);

                        String abilityName = id.getPath();
                        context.getSource().sendSuccess(() -> Component.literal("Removed ability " + abilityName + " from " + livingTarget.getName().getString()).withStyle(ChatFormatting.GREEN), true);
                    }
                }
            }
        }
        return 1;
    }

    private static int resetCooldown(CommandContext<CommandSourceStack> context, Collection<? extends Entity> targets) {
        for (Entity target : targets) {
            if (target instanceof LivingEntity livingTarget) {
                PlayerStats stats = PlayerStats.get(livingTarget);
                if (stats != null) {
                    java.util.Map<String, Long> cooldowns = new java.util.HashMap<>(stats.getCombat().abilityCooldowns());
                    cooldowns.clear();

                    stats.setCombat(new PlayerStats.CombatStats(
                            stats.getCombat().isLogia(), stats.getCombat().hasYamiPower(), stats.getCombat().hasYomiPower(),
                            stats.getCombat().hasAwakenedFruit(), stats.getCombat().busoshokuHakiExp(), stats.getCombat().kenbunshokuHakiExp(),
                            0, stats.getCombat().equippedAbilities(), stats.getCombat().activeAbilities(),
                            stats.getCombat().currentCombatBarSet(), stats.getCombat().selectedAbilitySlot(), stats.getCombat().isInCombatMode(),
                            stats.getCombat().busoshokuActive(), stats.getCombat().kenbunshokuActive(), cooldowns, stats.getCombat().abilityMaxCooldowns()
                    ));
                    stats.sync(livingTarget);

                    context.getSource().sendSuccess(() -> Component.literal("Reset cooldowns for " + livingTarget.getName().getString()).withStyle(ChatFormatting.GREEN), true);
                }
            }
        }
        return 1;
    }

    private static int awakeFruit(CommandContext<CommandSourceStack> context, Collection<? extends Entity> targets) {
        for (Entity target : targets) {
            if (target instanceof LivingEntity livingTarget) {
                PlayerStats stats = PlayerStats.get(livingTarget);
                if (stats != null) {
                    stats.setCombat(new PlayerStats.CombatStats(
                            stats.getCombat().isLogia(), stats.getCombat().hasYamiPower(), stats.getCombat().hasYomiPower(),
                            !stats.getCombat().hasAwakenedFruit(), stats.getCombat().busoshokuHakiExp(), stats.getCombat().kenbunshokuHakiExp(),
                            stats.getCombat().hakiOveruse(), stats.getCombat().equippedAbilities(), stats.getCombat().activeAbilities(),
                            stats.getCombat().currentCombatBarSet(), stats.getCombat().selectedAbilitySlot(), stats.getCombat().isInCombatMode(),
                            stats.getCombat().busoshokuActive(), stats.getCombat().kenbunshokuActive(), stats.getCombat().abilityCooldowns(), stats.getCombat().abilityMaxCooldowns()
                    ));
                    stats.sync(livingTarget);

                    boolean awakened = stats.getCombat().hasAwakenedFruit();
                    context.getSource().sendSuccess(() -> Component.literal((awakened ? "Awakened" : "Un-awakened") + " devil fruit for " + livingTarget.getName().getString()).withStyle(ChatFormatting.GREEN), true);
                }
            }
        }
        return 1;
    }
}
