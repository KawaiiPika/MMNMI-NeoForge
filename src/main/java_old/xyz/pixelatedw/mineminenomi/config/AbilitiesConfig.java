package xyz.pixelatedw.mineminenomi.config;

import java.util.Arrays;
import xyz.pixelatedw.mineminenomi.api.config.options.BooleanOption;
import xyz.pixelatedw.mineminenomi.api.config.options.ColorOption;
import xyz.pixelatedw.mineminenomi.api.config.options.DoubleOption;
import xyz.pixelatedw.mineminenomi.api.config.options.EnumOption;
import xyz.pixelatedw.mineminenomi.api.config.options.IntegerOption;
import xyz.pixelatedw.mineminenomi.api.config.options.LongOption;
import xyz.pixelatedw.mineminenomi.api.config.options.StringListOption;

public class AbilitiesConfig {
   public static final BooleanOption ABILITY_GRIEFING = new BooleanOption(true, "Ability Griefing", "Allows abilities to break or replace blocks; if turned OFF it will make some abilities completly useless\nDefault: true");
   public static final BooleanOption ABILITY_FRAUD_CHECKS = new BooleanOption(true, "Ability Fraud Checks", "Runs a check for all abilities on a player to remove dupes or suspicious abilities when the player joins the world\nDefault: true");
   public static final BooleanOption YAMI_POWER = new BooleanOption(false, "Yami Power", "Allows Yami Yami no Mi users to eat an additional fruit\nDefault: false");
   public static final BooleanOption WATER_CHECKS = new BooleanOption(false, "Devil Fruit Extended Weakness Checks", "Makes getting out of water much harder as it's supposed to be\nDefault: false");
   public static final BooleanOption SHARED_COOLDOWNS = new BooleanOption(true, "Shared Cooldowns", "Enables the shared cooldown between similar abilities\nDefault: true");
   public static final BooleanOption REMOVE_Y_RESTRICTION = new BooleanOption(false, "Remove Y Restriction", "Remove the Y level restriction for flying moves\nDefault: false");
   public static final BooleanOption LOGIA_INVULNERABILITY = new BooleanOption(true, "Logia Invulnerability", "Enables logia's invulnerability");
   public static final BooleanOption LOGIA_RETURN_EFFECT = new BooleanOption(false, "Logia Return Effect", "Allows logia users to have different effects when punched\nDefault: false");
   public static final BooleanOption RANDOMIZED_FRUITS = (BooleanOption)(new BooleanOption(false, "Randomized Fruits", "Will randomize all visual aspects of a devil fruit, making them impossible to identify\nDefault: false")).worldRestart();
   public static final BooleanOption ENABLE_AWAKENINGS = new BooleanOption(false, "Enable Awakenings", "Enables fruit awakenings\nDefault: false");
   public static final BooleanOption COMBAT_STATE_UPDATE_CHAT_MESSAGGE = new BooleanOption(false, "Combat State Update Chat Message", "Will send a (client sided only) chat message announcing when entering or leaving combat\nDefault: false");
   public static final EnumOption<ServerConfig.LogiaProjectileHitLogic> LOGIA_PROJECTILE_HIT_LOGIC;
   public static final IntegerOption ABILITY_BARS;
   public static final IntegerOption OPEN_WORLD_FRUIT_USERS;
   public static final DoubleOption DEVIL_FRUIT_DROP_FROM_LEAVES;
   public static final StringListOption BANNED_ABILITIES;
   public static final EnumOption<ServerConfig.HaoshokuUnlockLogic> HAOSHOKU_HAKI_UNLOCK_LOGIC;
   public static final EnumOption<ServerConfig.HaoshokuColoringLogic> HAOSHOKU_HAKI_COLORING_LOGIC;
   public static final ColorOption HAKI_COLOR;
   public static final StringListOption GLOBAL_PROTECTION_WHITELIST;
   public static final LongOption GLOBAL_PROTECTION_RESTORATION_GRACE;
   public static final EnumOption<ServerConfig.OneFruitPerWorldLogic> ONE_FRUIT_PER_WORLD;
   public static final BooleanOption UNABLE_TO_PICKUP_DF;
   public static final IntegerOption FRUITS_LIMIT_INVENTORY;
   public static final IntegerOption DAYS_FOR_INACTIVITY;
   public static final EnumOption<ServerConfig.OneFruitPerWorldChunkDeletionLogic> ONE_FRUIT_PER_WORLD_CHUNK_DELETION;
   public static final IntegerOption DROPPED_APPLES_RESPAWN_CHANCE;
   public static final IntegerOption ENTITY_INVENTORY_APPLES_RESPAWN_CHANCE;
   public static final IntegerOption CHESTS_APPLES_RESPAWN_CHANCE;

   static {
      LOGIA_PROJECTILE_HIT_LOGIC = new EnumOption<ServerConfig.LogiaProjectileHitLogic>(ServerConfig.LogiaProjectileHitLogic.EXTENDED, ServerConfig.LogiaProjectileHitLogic.values(), "Logia Projectiles Invulnerability", "How logias get affected by projectiles; \n NONE - No projectile can damage logias \n HAKI - Physical projectiles with buso cause damage \n EXTENDED - HAKI + any attack considered special deals damage to logias \nDefault: EXTENDED");
      ABILITY_BARS = new IntegerOption(2, 1, 10, "Ability Bars", "Number of ability bars;\nDefault: 2");
      OPEN_WORLD_FRUIT_USERS = new IntegerOption(0, 0, 100, "Open World Fruit Users Chance", "Chance for vice admirals and notorious captains to spawn with devil fruits\nDefault: 0");
      DEVIL_FRUIT_DROP_FROM_LEAVES = new DoubleOption((double)0.0F, (double)0.0F, (double)100.0F, "Chance for Devil Fruits to drop from leaves", "Allows Devil Fruits to drop from leaves if higher than 0\nDefault: 0");
      BANNED_ABILITIES = new StringListOption(Arrays.asList("mineminenomi:example1"), "Banned Abilities", "List with ability names that are banned from the mod entirely\nNames should be written using the modid:name model similar to the below examples, if no modid is provided 'mineminenomi' will be applied by default.");
      HAOSHOKU_HAKI_UNLOCK_LOGIC = new EnumOption<ServerConfig.HaoshokuUnlockLogic>(ServerConfig.HaoshokuUnlockLogic.COMBINED, ServerConfig.HaoshokuUnlockLogic.values(), "Haoshoku Haki Unlock Logic", "Responsible for how player unlock Haoshoku Haki; \n NONE - Haoshoku Haki cannot be unlocked naturally \n RANDOM - Only a few chosen ones receive it when they spawn \n EXPERIENCE - Will unlock based on the total amount of Haki experience a player has\n COMBINED - Combiens the logic of RANDOM and EXPERIENCE\n TRUE_RANDOM - Each world / server has its own pool of randomly chosen Haoshoku Haki users\nDefault: COMBINED");
      HAOSHOKU_HAKI_COLORING_LOGIC = new EnumOption<ServerConfig.HaoshokuColoringLogic>(ServerConfig.HaoshokuColoringLogic.STANDARD, ServerConfig.HaoshokuColoringLogic.values(), "Haoshoku Haki Coloring Logic", "Responsible for how a player's Haoshoku Haki outline will be colored; \n STANDARD - Standard red outline \n CUSTOM - Allows the player to customize it using their own client config \n RANDOM - Assignes a random color for each player based on their account's UUID\nDefault: STANDARD");
      HAKI_COLOR = new ColorOption("#ff0000", "Haki Color", "Changes the outline of the player's Haoshoku Haki, uses the hexadecimal format which MUST start with a # (ex: \"#FF0000\")\nDefault: #FF0000");
      GLOBAL_PROTECTION_WHITELIST = new StringListOption(Arrays.asList("mineminenomi:example1"), "Protection Whitelist", "List with ability names that can be used inside ability protection zones\nNames should be written using the modid:name model similar to the below examples, if no modid is provided 'mineminenomi' will be applied by default.");
      GLOBAL_PROTECTION_RESTORATION_GRACE = new LongOption(400L, 0L, 72000L, "Global Protection Restoration Grace", "Time (in ticks) before a newly replaced block will get restored inside ability protection areas\nDefault 400");
      ONE_FRUIT_PER_WORLD = new EnumOption<ServerConfig.OneFruitPerWorldLogic>(ServerConfig.OneFruitPerWorldLogic.NONE, ServerConfig.OneFruitPerWorldLogic.values(), "One Fruit per World", "Restricts the Devil Fruit spawns to only 1 of each type per world; \n NONE - No logic is applied, an infinite number of each fruit can exist \n SIMPLE - No more than one fruit type can be acquired via natural means (chests, leaves, fruit reincarnations etc) \n EXTENDED - Extra rules are applied on top of the SIMPLE set that blocks any means (or as many as possible) of storing/hoarding fruits \nDefault: NONE");
      UNABLE_TO_PICKUP_DF = new BooleanOption(false, "Unable to pickup Devil Fruit as a fruit user", "If the player already has a devil fruit then they will be unable to pickup any other fruit;\nDefault: false");
      FRUITS_LIMIT_INVENTORY = new IntegerOption(3, 1, 10, "Inventory Fruit Limit", "Sets the limit for how many fruits a player can hold in their inventory;\nDefault: 3");
      DAYS_FOR_INACTIVITY = new IntegerOption(6, 0, 30, "Days for Inactivity", "Defines how many days a player has to be offline before their Devil Fruits are removed\nA value of 0 means the setting is disabled and fruits will not be removed for inactivity!;\nDefault: 6");
      ONE_FRUIT_PER_WORLD_CHUNK_DELETION = new EnumOption<ServerConfig.OneFruitPerWorldChunkDeletionLogic>(ServerConfig.OneFruitPerWorldChunkDeletionLogic.NONE, ServerConfig.OneFruitPerWorldChunkDeletionLogic.values(), "One Fruit per World Chunk deletion Logic", "Update fruit history for fruits that have been in unloaded chunks; \n NONE - Default. No handling applied. Can cause the fruit status to not update properly \n INSTANT - Instantly delete any fruit found in an unloaded chunk. Most secure method \n REALISTIC - Wait 5 Minutes until marking the fruit as lost and delete it on next chunk load");
      DROPPED_APPLES_RESPAWN_CHANCE = new IntegerOption(15, 0, 100, "Dropped Apple Reincarnation Chance", "Sets the % chance for a Devil Fruit to get reincarnated from a dropped apple\nDefault: 15");
      ENTITY_INVENTORY_APPLES_RESPAWN_CHANCE = new IntegerOption(15, 0, 100, "Entity's Inventory Apple Reincarnation Chance", "Sets the % chance for a Devil Fruit to get reincarnated from an apple inside an entity's inventory\nDefault: 1");
      CHESTS_APPLES_RESPAWN_CHANCE = new IntegerOption(15, 0, 100, "Chest Blocks Apple Reincarnation Chance", "Sets the % chance for a Devil Fruit to get reincarnated from an apple inside of a nearby chest\nDefault: 1");
   }
}
