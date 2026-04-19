package xyz.pixelatedw.mineminenomi.config;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.challenges.ChallengeDifficulty;
import xyz.pixelatedw.mineminenomi.api.config.IConfigEnum;

public class ServerConfig {
   public static final ForgeConfigSpec SPEC;
   private static ForgeConfigSpec.ConfigValue<List<? extends String>> bannedItemsFromImbuing;
   private static ForgeConfigSpec.BooleanValue forceSelection;
   private static ForgeConfigSpec.BooleanValue abilityInvulnerability;
   private static ForgeConfigSpec.BooleanValue specialSourceEvents;
   private static ForgeConfigSpec.BooleanValue stopContinuousAbilities;
   private static ForgeConfigSpec.BooleanValue animeScreaming;
   private static List<AbilityCore<? extends IAbility>> protectionWhitelistedAbilities = new ArrayList();
   private static List<Item> bannedImbueableItems = new ArrayList();
   private static List<AbilityCore<? extends IAbility>> bannedAbilities = new ArrayList();
   private static List<ResourceLocation> bannedDimensionsForStructures = new ArrayList();

   public void clearCachedLists() {
      bannedAbilities.clear();
      bannedImbueableItems.clear();
      protectionWhitelistedAbilities.clear();
      bannedDimensionsForStructures.clear();
   }

   private static void setupConfig(ForgeConfigSpec.Builder builder) {
      builder.push("General");
      GeneralConfig.EXTRA_HEARTS.createValue(builder);
      GeneralConfig.MOB_REWARDS.createValue(builder);
      GeneralConfig.MINIMUM_DORIKI_PER_KILL.createValue(builder);
      GeneralConfig.DESTROY_SPAWNER.createValue(builder);
      GeneralConfig.DESTROY_WATER.createValue(builder);
      GeneralConfig.RACE_RANDOMIZER.createValue(builder);
      GeneralConfig.ALLOW_SUB_RACE_SELECT.createValue(builder);
      GeneralConfig.NATIVE_HAKI.createValue(builder);
      GeneralConfig.PUBLIC_REMOVEDF.createValue(builder);
      GeneralConfig.PUBLIC_CHECK_FRUITS.createValue(builder);
      GeneralConfig.DESPAWN_WITH_NAMETAG.createValue(builder);
      forceSelection = builder.comment("Forces new players to select their race, faction and fighting style upon joining the world\nDefault: false").define("Force Selection", false);
      Predicate<Object> bannedItemsTest = new Predicate<Object>() {
         public boolean test(Object t) {
            if (!(t instanceof String str)) {
               return false;
            } else {
               return !Strings.isNullOrEmpty(str);
            }
         }
      };
      List<String> defaultBannedItems = new ArrayList();
      defaultBannedItems.add("mineminenomi:bubbly_coral");
      defaultBannedItems.add("mineminenomi:medic_bag");
      bannedItemsFromImbuing = builder.comment("List with item ids that will not get the durability protection of Imbuing Haki").defineList("Banned Items from Imbuing", defaultBannedItems, bannedItemsTest);
      GeneralConfig.DORIKI_LIMIT.createValue(builder);
      GeneralConfig.HAKI_EXP_LIMIT.createValue(builder);
      GeneralConfig.TRAINING_POINT_LIMIT.createValue(builder);
      GeneralConfig.HEALTH_GAIN_FREQUENCY.createValue(builder);
      GeneralConfig.DORIKI_REWARD_MULTIPLIER.createValue(builder);
      GeneralConfig.BELLY_REWARD_MULTIPLIER.createValue(builder);
      GeneralConfig.BOUNTY_REWARD_MULTIPLIER.createValue(builder);
      GeneralConfig.HAKI_EXP_MULTIPLIER.createValue(builder);
      GeneralConfig.LOYALTY_MULTIPLIER.createValue(builder);
      builder.push("Stats to Keep");
      GeneralConfig.RACE_KEEP.createValue(builder);
      GeneralConfig.FACTION_KEEP.createValue(builder);
      GeneralConfig.FIGHTING_STYLE_KEEP.createValue(builder);
      GeneralConfig.DEVIL_FRUIT_KEEP.createValue(builder);
      GeneralConfig.DORIKI_KEEP_PERCENTAGE.createValue(builder);
      GeneralConfig.BOUNTY_KEEP_PERCENTAGE.createValue(builder);
      GeneralConfig.BELLY_KEEP_PERCENTAGE.createValue(builder);
      GeneralConfig.HAKI_EXP_KEEP_PERCENTAGE.createValue(builder);
      GeneralConfig.LOYALTY_KEEP_PERCENTAGE.createValue(builder);
      builder.pop();
      builder.push("Quests & Trials");
      GeneralConfig.ENABLE_TRIALS.createValue(builder);
      GeneralConfig.ENABLE_STYLES_PROGRESSION.createValue(builder);
      builder.pop();
      builder.pop();
      builder.push("Devil Fruits / Abilities");
      AbilitiesConfig.ABILITY_GRIEFING.createValue(builder);
      AbilitiesConfig.ABILITY_FRAUD_CHECKS.createValue(builder);
      AbilitiesConfig.ABILITY_BARS.createValue(builder);
      AbilitiesConfig.YAMI_POWER.createValue(builder);
      AbilitiesConfig.ENABLE_AWAKENINGS.createValue(builder);
      AbilitiesConfig.WATER_CHECKS.createValue(builder);
      AbilitiesConfig.SHARED_COOLDOWNS.createValue(builder);
      AbilitiesConfig.REMOVE_Y_RESTRICTION.createValue(builder);
      AbilitiesConfig.RANDOMIZED_FRUITS.createValue(builder);
      AbilitiesConfig.DEVIL_FRUIT_DROP_FROM_LEAVES.createValue(builder);
      AbilitiesConfig.LOGIA_INVULNERABILITY.createValue(builder);
      AbilitiesConfig.LOGIA_RETURN_EFFECT.createValue(builder);
      AbilitiesConfig.LOGIA_PROJECTILE_HIT_LOGIC.createValue(builder);
      abilityInvulnerability = builder.comment("Invulnerability to avoid attacks\nDefault: true").define("Ability Invulnerability", true);
      specialSourceEvents = builder.comment("Makes the fire and lava damage source to reduce fire resistance; only applies to move attacks from fruits \nDefault: true").define("Special Source Events", true);
      stopContinuousAbilities = builder.comment("Used to determine the logic for when a continuous ability is used while another continuous ability is being used;\n true - Currently used ability is stopped and the newly used ability starts its process\n false - The current ability is NOT stopped and the used ability has no effect\nDefault: true").define("Stop Continuous Abilities", true);
      animeScreaming = builder.comment("Will send a chat message to nearby players with the used ability's name\nDefault: false").define("Anime Scream", false);
      AbilitiesConfig.BANNED_ABILITIES.createValue(builder);
      builder.comment("These options only work when \"One Fruit per World\" option is set to EXTENDED!").push("One Fruit Per World");
      AbilitiesConfig.ONE_FRUIT_PER_WORLD.createValue(builder);
      AbilitiesConfig.UNABLE_TO_PICKUP_DF.createValue(builder);
      AbilitiesConfig.FRUITS_LIMIT_INVENTORY.createValue(builder);
      AbilitiesConfig.ONE_FRUIT_PER_WORLD_CHUNK_DELETION.createValue(builder);
      AbilitiesConfig.DAYS_FOR_INACTIVITY.createValue(builder);
      builder.pop();
      builder.push("Devil Fruits Reincarnation");
      AbilitiesConfig.DROPPED_APPLES_RESPAWN_CHANCE.createValue(builder);
      AbilitiesConfig.ENTITY_INVENTORY_APPLES_RESPAWN_CHANCE.createValue(builder);
      AbilitiesConfig.CHESTS_APPLES_RESPAWN_CHANCE.createValue(builder);
      builder.pop();
      builder.push("Ability Protection");
      AbilitiesConfig.GLOBAL_PROTECTION_WHITELIST.createValue(builder);
      AbilitiesConfig.GLOBAL_PROTECTION_RESTORATION_GRACE.createValue(builder);
      builder.pop();
      builder.push("Haki");
      AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.createValue(builder);
      AbilitiesConfig.HAOSHOKU_HAKI_COLORING_LOGIC.createValue(builder);
      builder.pop();
      builder.pop();
      builder.push("World Features");
      WorldFeaturesConfig.SPAWN_BIOMES.createValue(builder);
      WorldFeaturesConfig.DIMENSION_STRUCTURES_BANLIST.createValue(builder);
      WorldFeaturesConfig.SPAWN_CHANCE_TRAINING_STRUCTURES.createValue(builder);
      WorldFeaturesConfig.SPAWN_CHANCE_GHOST_SHIPS.createValue(builder);
      WorldFeaturesConfig.SPAWN_CHANCE_SKY_ISLANDS.createValue(builder);
      WorldFeaturesConfig.SPAWN_CHANCE_SMALL_SHIPS.createValue(builder);
      WorldFeaturesConfig.SPAWN_CHANCE_MEDIUM_SHIPS.createValue(builder);
      WorldFeaturesConfig.SPAWN_CHANCE_LARGE_SHIPS.createValue(builder);
      WorldFeaturesConfig.SPAWN_CHANCE_CAMPS.createValue(builder);
      WorldFeaturesConfig.SPAWN_CHANCE_SMALL_BASES.createValue(builder);
      WorldFeaturesConfig.SPAWN_CHANCE_LARGE_BASES.createValue(builder);
      WorldFeaturesConfig.SPAWN_CHANCE_WATCH_TOWERS.createValue(builder);
      WorldFeaturesConfig.SPAWN_CHANCE_PONEGLYPHS.createValue(builder);
      WorldFeaturesConfig.KAIROSEKI_SPAWN_COUNT.createValue(builder);
      builder.pop();
      builder.push("Challenges");
      ChallengesConfig.ENABLE_CHALLENGES.createValue(builder);
      ChallengesConfig.CHALLENGE_CACHING.createValue(builder);
      ChallengesConfig.RETURN_TO_SAFETY.createValue(builder);
      ChallengesConfig.DORIKI_REWARD_POOL.createValue(builder);
      ChallengesConfig.BELLY_REWARD_POOL.createValue(builder);
      ChallengesConfig.HAKI_REWARD_POOL.createValue(builder);
      ChallengesConfig.ARENA_CONSTRUCTION_SPEED.createValue(builder);
      builder.pop();
      builder.push("World Events");
      WorldEventsConfig.SPAWN_WORLD_HUMANOIDS.createValue(builder);
      WorldEventsConfig.SPAWN_WORLD_ANIMALS.createValue(builder);
      builder.push("Traders");
      WorldEventsConfig.TIME_BETWEEN_TRADER_SPAWNS.createValue(builder);
      WorldEventsConfig.SPAWN_CHANCE_TRADER.createValue(builder);
      builder.pop();
      builder.push("Trainers");
      WorldEventsConfig.TIME_BETWEEN_TRAINER_SPAWNS.createValue(builder);
      WorldEventsConfig.SPAWN_CHANCE_TRAINER.createValue(builder);
      builder.pop();
      builder.push("Ambushes");
      WorldEventsConfig.TIME_BETWEEN_AMBUSH_SPAWNS.createValue(builder);
      WorldEventsConfig.SPAWN_CHANCE_AMBUSH.createValue(builder);
      builder.pop();
      builder.push("Spawn Chances");
      WorldEventsConfig.GRUNT_SPAWN_CHANCE.createValue(builder);
      WorldEventsConfig.BRUTE_SPAWN_CHANCE.createValue(builder);
      WorldEventsConfig.SNIPER_SPAWN_CHANCE.createValue(builder);
      WorldEventsConfig.CAPTAIN_SPAWN_CHANCE.createValue(builder);
      WorldEventsConfig.PACIFISTA_SPAWN_CHANCE.createValue(builder);
      builder.pop();
      builder.pop();
      builder.push("Factions");
      FactionsConfig.DISABLE_FRIENDLY_FIRE.createValue(builder);
      builder.push("Bounty");
      FactionsConfig.TIME_BETWEEN_PACKAGE_DROPS.createValue(builder);
      builder.push("Crews");
      FactionsConfig.CREW_BOUNTY_REQUIREMENT.createValue(builder);
      FactionsConfig.WORLD_MESSAGE_ON_CREW_CREATE.createValue(builder);
      builder.pop();
      builder.push("System");
      SystemConfig.EXPERIMENTAL_TIMERS.createValue(builder);
      builder.pop();
   }

   public static int getArenaGenerationSpeed() {
      return (Integer)ChallengesConfig.ARENA_CONSTRUCTION_SPEED.get();
   }

   public static boolean hasExperimentalTimers() {
      return (Boolean)SystemConfig.EXPERIMENTAL_TIMERS.get();
   }

   public static boolean canSpawnCaravans() {
      return (Boolean)WorldEventsConfig.SPAWN_CARAVANS.get();
   }

   public static boolean canSpawnNotoriousTargets() {
      return (Boolean)WorldEventsConfig.SPAWN_NOTORIOUS_TARGETS.get();
   }

   public static boolean canSpawnCelestialVisits() {
      return (Boolean)WorldEventsConfig.SPAWN_CELESTIAL_VISITS.get();
   }

   public static int getHakiRewardPoolForDifficulty(ChallengeDifficulty diff) {
      List<Integer> list = (List)ChallengesConfig.HAKI_REWARD_POOL.get();
      if (!list.isEmpty() && list.size() >= diff.ordinal()) {
         int value = (Integer)list.get(diff.ordinal());
         value = Math.min(value, getHakiExpLimit());
         return value;
      } else {
         return 0;
      }
   }

   public static int getBellyRewardPoolForDifficulty(ChallengeDifficulty diff) {
      List<Integer> list = (List)ChallengesConfig.BELLY_REWARD_POOL.get();
      if (!list.isEmpty() && list.size() >= diff.ordinal()) {
         int value = (Integer)list.get(diff.ordinal());
         value = Math.min(value, 999999999);
         return value;
      } else {
         return 0;
      }
   }

   public static int getDorikiRewardPoolForDifficulty(ChallengeDifficulty diff) {
      List<Integer> list = (List)ChallengesConfig.DORIKI_REWARD_POOL.get();
      if (!list.isEmpty() && list.size() >= diff.ordinal()) {
         int value = (Integer)list.get(diff.ordinal());
         value = Math.min(value, getDorikiLimit());
         return value;
      } else {
         return 0;
      }
   }

   public static boolean isReturnToSafetyEnabled() {
      return (Boolean)ChallengesConfig.RETURN_TO_SAFETY.get();
   }

   public static double getPacifistaSpawnChance() {
      return (Double)WorldEventsConfig.PACIFISTA_SPAWN_CHANCE.get();
   }

   public static double getCaptainSpawnChance() {
      return (Double)WorldEventsConfig.CAPTAIN_SPAWN_CHANCE.get();
   }

   public static double getSniperSpawnChance() {
      return (Double)WorldEventsConfig.SNIPER_SPAWN_CHANCE.get();
   }

   public static double getBruteSpawnChance() {
      return (Double)WorldEventsConfig.BRUTE_SPAWN_CHANCE.get();
   }

   public static double getGruntSpawnChance() {
      return (Double)WorldEventsConfig.GRUNT_SPAWN_CHANCE.get();
   }

   public static boolean hasPermissionsEnabled() {
      return (Boolean)SystemConfig.ENABLE_PERMISSIONS.get();
   }

   public static boolean isForcedSelectionEnabled() {
      return (Boolean)forceSelection.get();
   }

   public static boolean isAnimeScreamingEnabled() {
      return (Boolean)animeScreaming.get();
   }

   public static boolean hasAwakeningsEnabled() {
      return (Boolean)AbilitiesConfig.ENABLE_AWAKENINGS.get();
   }

   public static List<ResourceLocation> getBannedDimensionsForStructures() {
      if (bannedDimensionsForStructures.isEmpty()) {
         for(Object o : (List)WorldFeaturesConfig.DIMENSION_STRUCTURES_BANLIST.get()) {
            if (o instanceof String) {
               String s = (String)o;
               String[] arr = s.split(":");
               if (arr.length != 0) {
                  if (arr.length == 1) {
                     arr = new String[]{"minecraft", s};
                  }

                  bannedDimensionsForStructures.add(ResourceLocation.fromNamespaceAndPath(arr[0], arr[1]));
               }
            }
         }
      }

      return bannedDimensionsForStructures;
   }

   public static boolean hasPublicCheckFruitsCommand() {
      return (Boolean)GeneralConfig.PUBLIC_CHECK_FRUITS.get();
   }

   public static long getGlobalProtectionGraceTime() {
      return (Long)AbilitiesConfig.GLOBAL_PROTECTION_RESTORATION_GRACE.get();
   }

   public static boolean isItemBannedFromImbuing(Item item) {
      return getBannedImbuingItems().contains(item);
   }

   public static List<Item> getBannedImbuingItems() {
      if (bannedImbueableItems.isEmpty()) {
         for(String str : (List)bannedItemsFromImbuing.get()) {
            ResourceLocation res = ResourceLocation.parse(str);
            Item item = (Item)ForgeRegistries.ITEMS.getValue(res);
            if (item != null) {
               bannedImbueableItems.add(item);
            }
         }
      }

      return bannedImbueableItems;
   }

   public static int getHealthGainFrequency() {
      return (Integer)GeneralConfig.HEALTH_GAIN_FREQUENCY.get();
   }

   public static boolean hasYRestrictionRemoved() {
      return (Boolean)AbilitiesConfig.REMOVE_Y_RESTRICTION.get();
   }

   public static boolean isHaoColoringRandom() {
      return AbilitiesConfig.HAOSHOKU_HAKI_COLORING_LOGIC.get() == ServerConfig.HaoshokuColoringLogic.RANDOM;
   }

   public static boolean isHaoColoringCustom() {
      return AbilitiesConfig.HAOSHOKU_HAKI_COLORING_LOGIC.get() == ServerConfig.HaoshokuColoringLogic.CUSTOM;
   }

   public static boolean isHaoColoringStandard() {
      return AbilitiesConfig.HAOSHOKU_HAKI_COLORING_LOGIC.get() == ServerConfig.HaoshokuColoringLogic.STANDARD;
   }

   public static HaoshokuColoringLogic getHaoshokuColoringLogic() {
      return (HaoshokuColoringLogic)AbilitiesConfig.HAOSHOKU_HAKI_COLORING_LOGIC.get();
   }

   public static boolean isNativeHakiEnabled() {
      return (Boolean)GeneralConfig.NATIVE_HAKI.get();
   }

   public static boolean isAbilityProtectionWhitelisted(AbilityCore<?> core) {
      return getProtectionWhitelistedAbilities().contains(core);
   }

   public static List<AbilityCore<? extends IAbility>> getProtectionWhitelistedAbilities() {
      // $FF: Couldn't be decompiled
   }

   public static boolean getDespawnWithNametag() {
      return (Boolean)GeneralConfig.DESPAWN_WITH_NAMETAG.get();
   }

   public static boolean getRandomizedFruits() {
      return (Boolean)AbilitiesConfig.RANDOMIZED_FRUITS.get();
   }

   public static boolean getRaceRandomizer() {
      return (Boolean)GeneralConfig.RACE_RANDOMIZER.get();
   }

   public static boolean getAllowSubRaceSelect() {
      return (Boolean)GeneralConfig.ALLOW_SUB_RACE_SELECT.get();
   }

   public static double getChanceForSkyIslandSpawn() {
      return (double)(Integer)WorldFeaturesConfig.SPAWN_CHANCE_SKY_ISLANDS.get();
   }

   public static boolean canSpawnSkyIslands() {
      return getChanceForSkyIslandSpawn() > (double)0.0F;
   }

   public static double getChanceForGhostShipSpawn() {
      return (double)(Integer)WorldFeaturesConfig.SPAWN_CHANCE_GHOST_SHIPS.get();
   }

   public static boolean canSpawnGhostShips() {
      return getChanceForGhostShipSpawn() > (double)0.0F;
   }

   public static boolean isChallengesEnabled() {
      return (Boolean)ChallengesConfig.ENABLE_CHALLENGES.get();
   }

   public static boolean isChallengesCachingEnabled() {
      return (Boolean)ChallengesConfig.CHALLENGE_CACHING.get();
   }

   public static double getChanceForPoneglyphSpawn() {
      return (double)(Integer)WorldFeaturesConfig.SPAWN_CHANCE_PONEGLYPHS.get();
   }

   public static boolean canSpawnPoneglyphs() {
      return getChanceForPoneglyphSpawn() > (double)0.0F;
   }

   public static int getDaysForInactivity() {
      return (Integer)AbilitiesConfig.DAYS_FOR_INACTIVITY.get();
   }

   public static int getBellyKeepPercentage() {
      return (Integer)GeneralConfig.BELLY_KEEP_PERCENTAGE.get();
   }

   public static int getHakiExpKeepPercentage() {
      return (Integer)GeneralConfig.HAKI_EXP_KEEP_PERCENTAGE.get();
   }

   public static int getBountyKeepPercentage() {
      return (Integer)GeneralConfig.BOUNTY_KEEP_PERCENTAGE.get();
   }

   public static int getDorikiKeepPercentage() {
      return (Integer)GeneralConfig.DORIKI_KEEP_PERCENTAGE.get();
   }

   public static int getInventoryLimitForFruits() {
      return (Integer)AbilitiesConfig.FRUITS_LIMIT_INVENTORY.get();
   }

   public static boolean getUnableToPickDFAsUser() {
      return (Boolean)AbilitiesConfig.UNABLE_TO_PICKUP_DF.get();
   }

   public static int getAbilityBars() {
      return (Integer)AbilitiesConfig.ABILITY_BARS.get();
   }

   public static boolean getStopContinuousAbility() {
      return (Boolean)stopContinuousAbilities.get();
   }

   public static int getHakiExpLimit() {
      return (Integer)GeneralConfig.HAKI_EXP_LIMIT.get();
   }

   public static int getDorikiLimit() {
      return (Integer)GeneralConfig.DORIKI_LIMIT.get();
   }

   public static int getTrainingPointLimit() {
      return (Integer)GeneralConfig.TRAINING_POINT_LIMIT.get();
   }

   public static boolean getDestroySpawner() {
      return (Boolean)GeneralConfig.DESTROY_SPAWNER.get();
   }

   public static boolean getDestroyWater() {
      return (Boolean)GeneralConfig.DESTROY_WATER.get();
   }

   public static double getChanceForChestAppleReincarnation() {
      return (double)(Integer)AbilitiesConfig.CHESTS_APPLES_RESPAWN_CHANCE.get();
   }

   public static double getChanceForInventoryAppleReincarnation() {
      return (double)(Integer)AbilitiesConfig.ENTITY_INVENTORY_APPLES_RESPAWN_CHANCE.get();
   }

   public static double getChanceForDroppedAppleReincarnation() {
      return (double)(Integer)AbilitiesConfig.DROPPED_APPLES_RESPAWN_CHANCE.get();
   }

   public static boolean isFriendlyDamageDisabled() {
      return (Boolean)FactionsConfig.DISABLE_FRIENDLY_FIRE.get();
   }

   public static boolean hasOneFruitPerWorldExtendedLogic() {
      return ((OneFruitPerWorldLogic)AbilitiesConfig.ONE_FRUIT_PER_WORLD.get()).equals(ServerConfig.OneFruitPerWorldLogic.EXTENDED);
   }

   public static boolean hasOneFruitPerWorldSimpleLogic() {
      return ((OneFruitPerWorldLogic)AbilitiesConfig.ONE_FRUIT_PER_WORLD.get()).equals(ServerConfig.OneFruitPerWorldLogic.SIMPLE) || hasOneFruitPerWorldExtendedLogic();
   }

   public static boolean hasChunkUnloadingInstantLogic() {
      return ((OneFruitPerWorldChunkDeletionLogic)AbilitiesConfig.ONE_FRUIT_PER_WORLD_CHUNK_DELETION.get()).equals(ServerConfig.OneFruitPerWorldChunkDeletionLogic.INSTANT);
   }

   public static boolean hasChunkUnloadingRealisticLogic() {
      return ((OneFruitPerWorldChunkDeletionLogic)AbilitiesConfig.ONE_FRUIT_PER_WORLD_CHUNK_DELETION.get()).equals(ServerConfig.OneFruitPerWorldChunkDeletionLogic.REALISTIC);
   }

   public static double getChanceForLargeBasesSpawn() {
      return (double)(Integer)WorldFeaturesConfig.SPAWN_CHANCE_LARGE_BASES.get();
   }

   public static boolean canSpawnLargeBases() {
      return getChanceForLargeBasesSpawn() > (double)0.0F;
   }

   public static double getChanceForWatchTowersSpawn() {
      return (double)(Integer)WorldFeaturesConfig.SPAWN_CHANCE_WATCH_TOWERS.get();
   }

   public static boolean canSpawnWatchTowers() {
      return getChanceForWatchTowersSpawn() > (double)0.0F;
   }

   public static double getChanceForLargeShipsSpawn() {
      return (double)(Integer)WorldFeaturesConfig.SPAWN_CHANCE_LARGE_SHIPS.get();
   }

   public static boolean canSpawnLargeShips() {
      return getChanceForLargeShipsSpawn() > (double)0.0F;
   }

   public static double getChanceForSmallBasesSpawn() {
      return (double)(Integer)WorldFeaturesConfig.SPAWN_CHANCE_SMALL_BASES.get();
   }

   public static boolean canSpawnSmallBases() {
      return getChanceForSmallBasesSpawn() > (double)0.0F;
   }

   public static double getChanceForCampsSpawn() {
      return (double)(Integer)WorldFeaturesConfig.SPAWN_CHANCE_CAMPS.get();
   }

   public static boolean canSpawnCamps() {
      return getChanceForCampsSpawn() > (double)0.0F;
   }

   public static double getChanceForSmallShipsSpawn() {
      return (double)(Integer)WorldFeaturesConfig.SPAWN_CHANCE_SMALL_SHIPS.get();
   }

   public static boolean canSpawnSmallShips() {
      return getChanceForSmallShipsSpawn() > (double)0.0F;
   }

   public static double getChanceForMediumShipsSpawn() {
      return (double)(Integer)WorldFeaturesConfig.SPAWN_CHANCE_MEDIUM_SHIPS.get();
   }

   public static boolean canSpawnMediumShips() {
      return getChanceForMediumShipsSpawn() > (double)0.0F;
   }

   public static double getChanceForTrainingStructureSpawn() {
      return (double)(Integer)WorldFeaturesConfig.SPAWN_CHANCE_TRAINING_STRUCTURES.get();
   }

   public static boolean canSpawnTrainingStructures() {
      return getChanceForTrainingStructureSpawn() > (double)0.0F;
   }

   public static boolean isCrewWorldMessageEnabled() {
      return (Boolean)FactionsConfig.WORLD_MESSAGE_ON_CREW_CREATE.get();
   }

   public static int getBountyRequirementForCrews() {
      return (Integer)FactionsConfig.CREW_BOUNTY_REQUIREMENT.get();
   }

   public static boolean isHaoshokuUnlockLogicExpBased() {
      return AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.get() == ServerConfig.HaoshokuUnlockLogic.EXPERIENCE || AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.get() == ServerConfig.HaoshokuUnlockLogic.COMBINED;
   }

   public static boolean isHaoshokuUnlockLogicChanceBased() {
      return AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.get() == ServerConfig.HaoshokuUnlockLogic.RANDOM || AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.get() == ServerConfig.HaoshokuUnlockLogic.COMBINED || AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.get() == ServerConfig.HaoshokuUnlockLogic.TRUE_RANDOM;
   }

   public static HaoshokuUnlockLogic getHaoshokuUnlockLogic() {
      return (HaoshokuUnlockLogic)AbilitiesConfig.HAOSHOKU_HAKI_UNLOCK_LOGIC.get();
   }

   public static int getChanceForAmbushSpawn() {
      return (Integer)WorldEventsConfig.SPAWN_CHANCE_AMBUSH.get();
   }

   public static int getTimeBetweenAmbushSpawns() {
      return (Integer)WorldEventsConfig.TIME_BETWEEN_AMBUSH_SPAWNS.get() * 60 * 20;
   }

   public static boolean canSpawnAmbushes() {
      return getChanceForAmbushSpawn() > 0 && getTimeBetweenAmbushSpawns() > 0;
   }

   public static int getChanceForTrainerSpawn() {
      return (Integer)WorldEventsConfig.SPAWN_CHANCE_TRAINER.get();
   }

   public static int getTimeBetweenTrainerSpawns() {
      return (Integer)WorldEventsConfig.TIME_BETWEEN_TRAINER_SPAWNS.get() * 60 * 20;
   }

   public static boolean canSpawnTrainers() {
      return getChanceForTrainerSpawn() > 0 && getTimeBetweenTrainerSpawns() > 0;
   }

   public static int getChanceForTraderSpawn() {
      return (Integer)WorldEventsConfig.SPAWN_CHANCE_TRADER.get();
   }

   public static int getTimeBetweenTraderSpawns() {
      return (Integer)WorldEventsConfig.TIME_BETWEEN_TRADER_SPAWNS.get() * 60 * 20;
   }

   public static boolean canSpawnTraders() {
      return getChanceForTraderSpawn() > 0 && getTimeBetweenTraderSpawns() > 0;
   }

   public static int getTimeBetweenPackages() {
      return (Integer)FactionsConfig.TIME_BETWEEN_PACKAGE_DROPS.get() * 20 * 60;
   }

   public static boolean isWantedPosterPackagesEnabled() {
      return getTimeBetweenPackages() > 0;
   }

   public static double getDorikiRewardMultiplier() {
      return (Double)GeneralConfig.DORIKI_REWARD_MULTIPLIER.get();
   }

   public static double getBellyRewardMultiplier() {
      return (Double)GeneralConfig.BELLY_REWARD_MULTIPLIER.get();
   }

   public static double getBountyRewardMultiplier() {
      return (Double)GeneralConfig.BOUNTY_REWARD_MULTIPLIER.get();
   }

   public static double getHakiExpMultiplier() {
      return (Double)GeneralConfig.HAKI_EXP_MULTIPLIER.get();
   }

   public static double getLoyaltyMultiplier() {
      return (Double)GeneralConfig.LOYALTY_MULTIPLIER.get();
   }

   public static boolean isQuestProgressionEnabled() {
      return (Boolean)GeneralConfig.ENABLE_STYLES_PROGRESSION.get();
   }

   public static boolean isQuestsEnabled() {
      return (Boolean)GeneralConfig.ENABLE_TRIALS.get();
   }

   public static List<AbilityCore<? extends IAbility>> getBannedAbilities() {
      // $FF: Couldn't be decompiled
   }

   public static boolean isAbilityInvulnerabilityEnabled() {
      return (Boolean)abilityInvulnerability.get();
   }

   public static boolean areExtraWaterChecksEnabled() {
      return (Boolean)AbilitiesConfig.WATER_CHECKS.get();
   }

   public static boolean isLogiaDamageEffectEnabled() {
      return (Boolean)AbilitiesConfig.LOGIA_RETURN_EFFECT.get();
   }

   public static boolean doLogiasHaveHurtHakiLogic() {
      return ((LogiaProjectileHitLogic)AbilitiesConfig.LOGIA_PROJECTILE_HIT_LOGIC.get()).equals(ServerConfig.LogiaProjectileHitLogic.HAKI) || ((LogiaProjectileHitLogic)AbilitiesConfig.LOGIA_PROJECTILE_HIT_LOGIC.get()).equals(ServerConfig.LogiaProjectileHitLogic.EXTENDED);
   }

   public static boolean doLogiasHaveHurtExtendedLogic() {
      return ((LogiaProjectileHitLogic)AbilitiesConfig.LOGIA_PROJECTILE_HIT_LOGIC.get()).equals(ServerConfig.LogiaProjectileHitLogic.EXTENDED);
   }

   public static double getDevilFruitDropsFromLeavesChance() {
      return (Double)AbilitiesConfig.DEVIL_FRUIT_DROP_FROM_LEAVES.get();
   }

   public static boolean isExtraHeartsEnabled() {
      return (Boolean)GeneralConfig.EXTRA_HEARTS.get();
   }

   public static boolean isMobRewardsEnabled() {
      return (Boolean)GeneralConfig.MOB_REWARDS.get();
   }

   public static boolean isAbilityGriefingEnabled() {
      return (Boolean)AbilitiesConfig.ABILITY_GRIEFING.get();
   }

   public static boolean isSpecialSourceEventsEnabled() {
      return (Boolean)specialSourceEvents.get();
   }

   public static boolean isYamiPowerEnabled() {
      return (Boolean)AbilitiesConfig.YAMI_POWER.get();
   }

   public static boolean isAbilityFraudChecksEnabled() {
      return (Boolean)AbilitiesConfig.ABILITY_FRAUD_CHECKS.get();
   }

   public static boolean isMinimumDorikiPerKillEnabled() {
      return (Boolean)GeneralConfig.MINIMUM_DORIKI_PER_KILL.get();
   }

   public static int getKairosekiSpawnCount() {
      return (Integer)WorldFeaturesConfig.KAIROSEKI_SPAWN_COUNT.get();
   }

   static {
      ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
      setupConfig(configBuilder);
      SPEC = configBuilder.build();
   }

   public static enum KeepStatsLogic implements IConfigEnum {
      NONE,
      AUTO,
      FULL,
      CUSTOM;

      public <T extends IConfigEnum> T next() {
         return (T)values()[(this.ordinal() + 1) % values().length];
      }

      // $FF: synthetic method
      private static KeepStatsLogic[] $values() {
         return new KeepStatsLogic[]{NONE, AUTO, FULL, CUSTOM};
      }
   }

   public static enum HaoshokuUnlockLogic implements IConfigEnum {
      NONE,
      RANDOM,
      EXPERIENCE,
      COMBINED,
      TRUE_RANDOM;

      public <T extends IConfigEnum> T next() {
         return (T)values()[(this.ordinal() + 1) % values().length];
      }

      // $FF: synthetic method
      private static HaoshokuUnlockLogic[] $values() {
         return new HaoshokuUnlockLogic[]{NONE, RANDOM, EXPERIENCE, COMBINED, TRUE_RANDOM};
      }
   }

   public static enum OneFruitPerWorldLogic implements IConfigEnum {
      NONE,
      SIMPLE,
      EXTENDED;

      public <T extends IConfigEnum> T next() {
         return (T)values()[(this.ordinal() + 1) % values().length];
      }

      // $FF: synthetic method
      private static OneFruitPerWorldLogic[] $values() {
         return new OneFruitPerWorldLogic[]{NONE, SIMPLE, EXTENDED};
      }
   }

   public static enum OneFruitPerWorldChunkDeletionLogic implements IConfigEnum {
      NONE,
      INSTANT,
      REALISTIC;

      public <T extends IConfigEnum> T next() {
         return (T)values()[(this.ordinal() + 1) % values().length];
      }

      // $FF: synthetic method
      private static OneFruitPerWorldChunkDeletionLogic[] $values() {
         return new OneFruitPerWorldChunkDeletionLogic[]{NONE, INSTANT, REALISTIC};
      }
   }

   public static enum LogiaProjectileHitLogic implements IConfigEnum {
      NONE,
      HAKI,
      EXTENDED;

      public <T extends IConfigEnum> T next() {
         return (T)values()[(this.ordinal() + 1) % values().length];
      }

      // $FF: synthetic method
      private static LogiaProjectileHitLogic[] $values() {
         return new LogiaProjectileHitLogic[]{NONE, HAKI, EXTENDED};
      }
   }

   public static enum HaoshokuColoringLogic implements IConfigEnum {
      STANDARD,
      CUSTOM,
      RANDOM;

      public <T extends IConfigEnum> T next() {
         return (T)values()[(this.ordinal() + 1) % values().length];
      }

      // $FF: synthetic method
      private static HaoshokuColoringLogic[] $values() {
         return new HaoshokuColoringLogic[]{STANDARD, CUSTOM, RANDOM};
      }
   }
}
