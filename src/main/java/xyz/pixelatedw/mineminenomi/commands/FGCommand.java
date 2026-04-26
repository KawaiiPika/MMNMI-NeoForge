package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class FGCommand {
    public static boolean NO_COOLDOWN = false;
    public static boolean SHOW_TPS = false;
    public static boolean IGNORE_STRUCTURE_SIZE = false;
    public static int ANIM_SPEED = 8;
    public static boolean SHOW_DEBUG_ALL = false;
    public static boolean SHOW_DEBUG_IN_CHAT = false;
    public static boolean SHOW_DEBUG_DAMAGE = false;
    public static boolean SHOW_DEBUG_REWARDS = false;
    public static boolean SHOW_DEBUG_STATS = false;
    public static boolean SHOW_DEBUG_FRIENDLY = false;
    public static boolean SHOW_NPC_ABILITY_LOG = false;
    public static boolean SHOW_PACKET_LOG = false;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext) {
        LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("fg").requires((source) -> source.hasPermission(2));

        builder.then(Commands.literal("test").executes((ctx) -> runTest(ctx, ctx.getSource().getPlayerOrException())));

        builder.then(Commands.literal("reset")
                .then(Commands.literal("nodes").executes((ctx) -> {
                    // TODO: Port ability nodes
                    return 1;
                }))
                .then(Commands.literal("abilities").executes((ctx) -> {
                    // TODO: Port unlocked abilities removal
                    return 1;
                }))
                .then(Commands.literal("selection").executes((ctx) -> {
                    ServerPlayer player = ctx.getSource().getPlayerOrException();
                    //stats.setFaction(null);
                    //stats.setRace(null);
                    //stats.setFightingStyle(null);
                    return 1;
                }))
                .then(Commands.literal("ultra_cola").executes((ctx) -> {
                    ServerPlayer player = ctx.getSource().getPlayerOrException();
                    // TODO: PlayerStats ultra_cola
                    return 1;
                }))
                .then(Commands.literal("events").executes((ctx) -> {
                    // TODO: Port EventsWorldData
                    return 1;
                }))
        );

        builder.then(Commands.literal("show")
                .then(Commands.literal("debug_in_chat").executes((ctx) -> {
                    SHOW_DEBUG_IN_CHAT = !SHOW_DEBUG_IN_CHAT;
                    return 1;
                }))
                .then(Commands.literal("damage").executes((ctx) -> {
                    SHOW_DEBUG_DAMAGE = !SHOW_DEBUG_DAMAGE;
                    return 1;
                }))
                .then(Commands.literal("rewards").executes((ctx) -> {
                    SHOW_DEBUG_REWARDS = !SHOW_DEBUG_REWARDS;
                    return 1;
                }))
                .then(Commands.literal("spawn_stats").executes((ctx) -> {
                    SHOW_DEBUG_STATS = !SHOW_DEBUG_STATS;
                    return 1;
                }))
                .then(Commands.literal("friendly").executes((ctx) -> {
                    SHOW_DEBUG_FRIENDLY = !SHOW_DEBUG_FRIENDLY;
                    return 1;
                }))
                .then(Commands.literal("npc_ability_log").executes((ctx) -> {
                    SHOW_NPC_ABILITY_LOG = !SHOW_NPC_ABILITY_LOG;
                    return 1;
                }))
                .then(Commands.literal("packet_log").executes((ctx) -> {
                    SHOW_PACKET_LOG = !SHOW_PACKET_LOG;
                    return 1;
                }))
                .then(Commands.literal("all").executes((ctx) -> {
                    SHOW_DEBUG_ALL = !SHOW_DEBUG_ALL;
                    SHOW_DEBUG_IN_CHAT = SHOW_DEBUG_ALL;
                    SHOW_DEBUG_DAMAGE = SHOW_DEBUG_ALL;
                    SHOW_DEBUG_REWARDS = SHOW_DEBUG_ALL;
                    SHOW_DEBUG_STATS = SHOW_DEBUG_ALL;
                    SHOW_DEBUG_FRIENDLY = SHOW_DEBUG_ALL;
                    SHOW_NPC_ABILITY_LOG = SHOW_DEBUG_ALL;
                    SHOW_PACKET_LOG = SHOW_DEBUG_ALL;
                    return 1;
                }))
        );

        builder.then(Commands.literal("get_target_soulbounds").then(Commands.argument("target", EntityArgument.entity()).executes((context) -> {
            Entity target = EntityArgument.getEntity(context, "target");
            if (target instanceof LivingEntity living) {
                // TODO: Port get target soulbounds
            }
            return 1;
        })));

        builder.then(Commands.literal("tp_to_challenge").executes((context) -> {
            // TODO: Port Challenge Dimension logic
            return 1;
        }));

        builder.then(Commands.literal("simulate_challenges").executes((context) -> {
            // TODO: Port Challenge Dimension simulation
            return 1;
        }));

        builder.then(Commands.literal("no_cooldowns").executes((context) -> {
            NO_COOLDOWN = !NO_COOLDOWN;
            context.getSource().getPlayerOrException().displayClientMessage(Component.literal((NO_COOLDOWN ? "Enabled" : "Disabled") + " No Cooldown Mode"), false);
            return 1;
        }));

        builder.then(Commands.literal("ignore_structure_size").executes((context) -> {
            IGNORE_STRUCTURE_SIZE = !IGNORE_STRUCTURE_SIZE;
            context.getSource().getPlayerOrException().displayClientMessage(Component.literal((IGNORE_STRUCTURE_SIZE ? "Enabled" : "Disabled") + " Ignore Structure Size"), false);
            return 1;
        }));

        builder.then(Commands.literal("get_poster").executes((ctx) -> {
            ServerPlayer player = ctx.getSource().getPlayerOrException();
            // TODO: port get wanted poster
            return 1;
        }));

        builder.then(Commands.literal("clue")
                .then(Commands.argument("rolls", IntegerArgumentType.integer(1))
                        .executes((context) -> giveRandomClue(context, context.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(context, "rolls"), null))
                        .then(Commands.argument("fruit", ResourceLocationArgument.id())
                                .executes((context) -> giveRandomClue(context, context.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(context, "rolls"), ResourceLocationArgument.getId(context, "fruit")))))
        );

        builder.then(Commands.literal("spawner")
                .then(Commands.argument("spawn", ResourceArgument.resource(commandBuildContext, Registries.ENTITY_TYPE))
                        .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                        .then(Commands.argument("limit", IntegerArgumentType.integer())
                                .executes((ctx) -> {
                                    ServerLevel level = ctx.getSource().getLevel();
                                    Vec3 vecPos = ctx.getSource().getPosition();
                                    BlockPos pos = BlockPos.containing(vecPos.x, vecPos.y, vecPos.z);
                                    // TODO: port custom spawner logic
                                    return 1;
                                }))));

        dispatcher.register(builder);
    }

    private static int giveRandomClue(CommandContext<CommandSourceStack> context, ServerPlayer player, int rolls, @Nullable ResourceLocation fruitTemplate) {
        // TODO: Port give random clue
        return 1;
    }

    private static int runTest(CommandContext<CommandSourceStack> context, ServerPlayer player) {
        return 1;
    }
}
