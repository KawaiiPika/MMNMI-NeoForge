package xyz.pixelatedw.mineminenomi.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.DFEncyclopediaEntry;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Faction;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.blocks.blockentity.CustomSpawnerBlockEntity;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.data.world.EventsWorldData;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModValues;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18n;
import xyz.pixelatedw.mineminenomi.items.AkumaNoMiItem;
import xyz.pixelatedw.mineminenomi.items.HeartItem;
import xyz.pixelatedw.mineminenomi.items.StrawDollItem;
import xyz.pixelatedw.mineminenomi.items.WantedPosterItem;
import xyz.pixelatedw.mineminenomi.world.ChallengesChunkGenerator;
import xyz.pixelatedw.mineminenomi.world.DynamicDimensionManager;

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

   public static LiteralArgumentBuilder<CommandSourceStack> create(CommandBuildContext commandBuildContext) {
      LiteralArgumentBuilder<CommandSourceStack> builder = (LiteralArgumentBuilder)Commands.m_82127_("fg").requires((source) -> source.m_6761_(0));
      builder.then(Commands.m_82127_("test").executes((ctx) -> runTest(ctx, ((CommandSourceStack)ctx.getSource()).m_81375_())));
      builder.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("reset").then(Commands.m_82127_("nodes").executes((ctx) -> {
         ServerPlayer player = ((CommandSourceStack)ctx.getSource()).m_81375_();
         IAbilityData props = (IAbilityData)AbilityCapability.get(player).orElse((Object)null);
         if (props == null) {
            return -1;
         } else {
            for(AbilityNode node : props.getNodes()) {
               node.lockNode(player);
            }

            return 1;
         }
      }))).then(Commands.m_82127_("abilities").executes((ctx) -> {
         ServerPlayer player = ((CommandSourceStack)ctx.getSource()).m_81375_();
         AbilityCapability.get(player).ifPresent((data) -> {
            Stream var10000 = data.getUnlockedAbilities().stream().map((w) -> w.getAbilityCore());
            Objects.requireNonNull(data);
            var10000.forEach(data::removeUnlockedAbility);
         });
         return 1;
      }))).then(Commands.m_82127_("selection").executes((ctx) -> {
         ServerPlayer player = ((CommandSourceStack)ctx.getSource()).m_81375_();
         EntityStatsCapability.get(player).ifPresent((props) -> {
            props.setFaction((Faction)null);
            props.setRace((Race)null);
            props.setFightingStyle((FightingStyle)null);
         });
         return 1;
      }))).then(Commands.m_82127_("ultra_cola").executes((ctx) -> {
         ServerPlayer player = ((CommandSourceStack)ctx.getSource()).m_81375_();
         EntityStatsCapability.get(player).ifPresent((props) -> props.setUltraCola(0));
         return 1;
      }))).then(Commands.m_82127_("events").executes((ctx) -> {
         EventsWorldData worldData = EventsWorldData.get();
         worldData.wipeAllEvents();
         return 1;
      })));
      builder.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("show").then(Commands.m_82127_("debug_in_chat").executes((ctx) -> {
         SHOW_DEBUG_IN_CHAT = !SHOW_DEBUG_IN_CHAT;
         return 1;
      }))).then(Commands.m_82127_("damage").executes((ctx) -> {
         SHOW_DEBUG_DAMAGE = !SHOW_DEBUG_DAMAGE;
         return 1;
      }))).then(Commands.m_82127_("rewards").executes((ctx) -> {
         SHOW_DEBUG_REWARDS = !SHOW_DEBUG_REWARDS;
         return 1;
      }))).then(Commands.m_82127_("spawn_stats").executes((ctx) -> {
         SHOW_DEBUG_STATS = !SHOW_DEBUG_STATS;
         return 1;
      }))).then(Commands.m_82127_("friendly").executes((ctx) -> {
         SHOW_DEBUG_FRIENDLY = !SHOW_DEBUG_FRIENDLY;
         return 1;
      }))).then(Commands.m_82127_("npc_ability_log").executes((ctx) -> {
         SHOW_NPC_ABILITY_LOG = !SHOW_NPC_ABILITY_LOG;
         return 1;
      }))).then(Commands.m_82127_("packet_log").executes((ctx) -> {
         SHOW_PACKET_LOG = !SHOW_PACKET_LOG;
         return 1;
      }))).then(Commands.m_82127_("all").executes((ctx) -> {
         SHOW_DEBUG_ALL = !SHOW_DEBUG_ALL;
         SHOW_DEBUG_IN_CHAT = SHOW_DEBUG_ALL;
         SHOW_DEBUG_DAMAGE = SHOW_DEBUG_ALL;
         SHOW_DEBUG_REWARDS = SHOW_DEBUG_ALL;
         SHOW_DEBUG_STATS = SHOW_DEBUG_ALL;
         SHOW_DEBUG_FRIENDLY = SHOW_DEBUG_ALL;
         SHOW_NPC_ABILITY_LOG = SHOW_DEBUG_ALL;
         SHOW_PACKET_LOG = SHOW_DEBUG_ALL;
         return 1;
      })));
      builder.then(Commands.m_82127_("get_target_soulbounds").then(Commands.m_82129_("target", EntityArgument.m_91449_()).executes((context) -> {
         Entity target = EntityArgument.m_91452_(context, "target");
         if (target instanceof LivingEntity living) {
            IEntityStats props = (IEntityStats)EntityStatsCapability.get(living).orElse((Object)null);
            if (props == null) {
               return -1;
            }

            props.setHeart(true);
            ItemStack heartStack = HeartItem.createHeartStack(living);
            ((CommandSourceStack)context.getSource()).m_81375_().m_36356_(heartStack);
            props.setHeart(false);
            props.setStrawDoll(true);
            ItemStack dollStack = StrawDollItem.createDollStack(living);
            ((CommandSourceStack)context.getSource()).m_81375_().m_36356_(dollStack);
            props.setStrawDoll(false);
         }

         return 1;
      })));
      builder.then(Commands.m_82127_("tp_to_challenge").executes((context) -> {
         ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();

         try {
            ResourceLocation dimName = ResourceLocation.fromNamespaceAndPath("mineminenomi", "challenges_" + player.m_20149_());
            ResourceKey<Level> dimension = ResourceKey.m_135785_(Registries.f_256858_, dimName);
            RegistryAccess registryAccess = player.m_9236_().m_9598_();
            Holder<DimensionType> type = registryAccess.m_175515_(Registries.f_256787_).m_246971_(ResourceKey.m_135785_(Registries.f_256787_, ModResources.DIMENSION_TYPE_CHALLENGES));
            ServerLevel shard = DynamicDimensionManager.getOrCreateWorld(player.m_20194_(), dimension, (minecraftServer, levelStemResourceKey) -> {
               Holder.Reference<Biome> biomeReference = ((HolderLookup.RegistryLookup)registryAccess.m_254861_(Registries.f_256952_).get()).m_255043_(Biomes.f_48173_);
               ChunkGenerator generator = new ChallengesChunkGenerator(biomeReference);
               return new LevelStem(type, generator);
            });
            player.m_8999_(shard, (double)0.0F, (double)0.0F, (double)0.0F, 0.0F, 0.0F);
         } catch (Exception e) {
            e.printStackTrace();
         }

         return 1;
      }));
      builder.then(Commands.m_82127_("simulate_challenges").executes((context) -> {
         ServerPlayer player = ((CommandSourceStack)context.getSource()).m_81375_();

         try {
            for(int i = 0; i < 20; ++i) {
               UUID id = UUID.randomUUID();
               ResourceLocation dimName = ResourceLocation.fromNamespaceAndPath("mineminenomi", "challenges_" + String.valueOf(id));
               RegistryAccess registryAccess = player.m_9236_().m_9598_();
               ResourceKey<Level> dimension = ResourceKey.m_135785_(Registries.f_256858_, dimName);
               Holder<DimensionType> type = registryAccess.m_175515_(Registries.f_256787_).m_246971_(ResourceKey.m_135785_(Registries.f_256787_, ModResources.DIMENSION_TYPE_CHALLENGES));
               DynamicDimensionManager.getOrCreateWorld(player.m_20194_(), dimension, (minecraftServer, levelStemResourceKey) -> {
                  Holder.Reference<Biome> biomeReference = ((HolderLookup.RegistryLookup)registryAccess.m_254861_(Registries.f_256952_).get()).m_255043_(Biomes.f_48173_);
                  ChunkGenerator generator = new ChallengesChunkGenerator(biomeReference);
                  return new LevelStem(type, generator);
               });
            }
         } catch (Exception e) {
            e.printStackTrace();
         }

         return 1;
      }));
      builder.then(Commands.m_82127_("no_cooldowns").executes((context) -> {
         NO_COOLDOWN = !NO_COOLDOWN;
         ((CommandSourceStack)context.getSource()).m_81375_().m_213846_(Component.m_237113_((NO_COOLDOWN ? "Enabled" : "Disabled") + " No Cooldown Mode"));
         return 1;
      }));
      builder.then(Commands.m_82127_("ignore_structure_size").executes((context) -> {
         IGNORE_STRUCTURE_SIZE = !IGNORE_STRUCTURE_SIZE;
         ((CommandSourceStack)context.getSource()).m_81375_().m_213846_(Component.m_237113_((IGNORE_STRUCTURE_SIZE ? "Enabled" : "Disabled") + " Ignore Structure Size"));
         return 1;
      }));
      builder.then(Commands.m_82127_("get_poster").executes((ctx) -> {
         ServerPlayer player = ((CommandSourceStack)ctx.getSource()).m_81375_();
         ItemStack stack = WantedPosterItem.getWantedPosterStack(player, 100000L);
         player.m_9236_().m_7967_(new ItemEntity(player.m_9236_(), (double)player.m_20183_().m_123341_(), (double)(player.m_20183_().m_123342_() + 1), (double)player.m_20183_().m_123343_(), stack));
         return 1;
      }));
      builder.then(Commands.m_82127_("clue").then(((RequiredArgumentBuilder)Commands.m_82129_("rolls", IntegerArgumentType.integer(1)).executes((context) -> giveRandomClue(context, ((CommandSourceStack)context.getSource()).m_81375_(), IntegerArgumentType.getInteger(context, "rolls"), (ResourceLocation)null))).then(Commands.m_82129_("fruit", ResourceLocationArgument.m_106984_()).executes((context) -> giveRandomClue(context, ((CommandSourceStack)context.getSource()).m_81375_(), IntegerArgumentType.getInteger(context, "rolls"), ResourceLocationArgument.m_107011_(context, "fruit"))))));
      builder.then(Commands.m_82127_("spawner").then(Commands.m_82129_("spawn", ResourceArgument.m_247102_(commandBuildContext, Registries.f_256939_)).suggests(SuggestionProviders.f_121645_).then(Commands.m_82129_("limit", IntegerArgumentType.integer()).executes((ctx) -> {
         ServerLevel level = ((CommandSourceStack)ctx.getSource()).m_81372_();
         Vec3 vecPos = ((CommandSourceStack)ctx.getSource()).m_81371_();
         BlockPos pos = BlockPos.m_274561_(vecPos.f_82479_, vecPos.f_82480_, vecPos.f_82481_).m_7494_();
         level.m_7731_(pos, ((Block)ModBlocks.CUSTOM_SPAWNER.get()).m_49966_(), 3);
         BlockEntity be = level.m_7702_(pos);
         if (be != null && be instanceof CustomSpawnerBlockEntity cbe) {
            EntityType<?> entityType = (EntityType)ResourceArgument.m_247713_(ctx, "spawn").get();
            int limit = IntegerArgumentType.getInteger(ctx, "limit");
            cbe.setSpawnerMob(entityType);
            cbe.setSpawnerLimit(limit);
            cbe.lock();
         }

         return 1;
      }))));
      return builder;
   }

   private static int giveRandomClue(CommandContext<CommandSourceStack> context, ServerPlayer player, int rolls, @Nullable ResourceLocation fruitTemplate) {
      AkumaNoMiItem fruit = null;
      if (fruitTemplate == null) {
         fruit = (AkumaNoMiItem)WyHelper.shuffle(new ArrayList(ModValues.DEVIL_FRUITS)).stream().findFirst().orElse((Object)null);
      } else {
         fruit = (AkumaNoMiItem)ModValues.DEVIL_FRUITS.stream().filter((df) -> df.getRegistryKey().equals(fruitTemplate)).findFirst().orElse((Object)null);
      }

      if (fruit == null) {
         return 1;
      } else {
         DFEncyclopediaEntry template = fruit.getRandomElements(player.m_9236_());
         Optional<Integer> shape = Optional.empty();
         Optional<Color> baseColor = Optional.empty();
         Optional<Color> stemColor = Optional.empty();
         int maxRolls = 2;
         if (player.m_217043_().m_188503_(100) < 10) {
            maxRolls = 3;
         }

         for(int i = 0; i < rolls; ++i) {
            int rand = player.m_217043_().m_188503_(3);
            if (rand == 0) {
               shape = template.getShape();
            } else if (rand == 1) {
               baseColor = template.getBaseColor();
            } else {
               stemColor = template.getStemColor();
            }
         }

         ItemStack stack = new ItemStack(Items.f_42516_);
         CompoundTag nbt = stack.m_41698_("fruitClues");
         nbt.m_128359_("fruitKey", fruit.getRegistryKey().toString());
         if (shape.isPresent()) {
            nbt.m_128405_("baseShape", (Integer)shape.get());
         }

         if (baseColor.isPresent()) {
            nbt.m_128405_("baseColor", ((Color)baseColor.get()).getRGB());
         }

         if (stemColor.isPresent()) {
            nbt.m_128405_("stemColor", ((Color)stemColor.get()).getRGB());
         }

         stack.m_41714_(ModI18n.ITEM_FRUIT_CLUE);
         stack.m_41764_(1);
         player.m_36356_(stack);
         return -1;
      }
   }

   private static int runTest(CommandContext<CommandSourceStack> context, ServerPlayer player) {
      return 1;
   }
}
