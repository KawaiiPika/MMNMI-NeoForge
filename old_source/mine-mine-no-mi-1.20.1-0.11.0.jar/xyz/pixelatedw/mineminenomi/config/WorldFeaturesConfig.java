package xyz.pixelatedw.mineminenomi.config;

import java.util.Arrays;
import xyz.pixelatedw.mineminenomi.api.config.options.BooleanOption;
import xyz.pixelatedw.mineminenomi.api.config.options.IntegerOption;
import xyz.pixelatedw.mineminenomi.api.config.options.StringListOption;

public class WorldFeaturesConfig {
   public static final BooleanOption SPAWN_BIOMES = new BooleanOption(true, "Spawn One Piece Biomes", "Adds couple of new one piece themed biomes to the world\nWARNING: This is a very experimental option, and can have some major downsides such as biomes being too vast, which is why its disabled by default\nDefault: true");
   public static final StringListOption DIMENSION_STRUCTURES_BANLIST = new StringListOption(Arrays.asList("minecraft:the_nether", "minecraft:the_end"), "Dimension Banlist for Structures", "List of dimension ids where mod structures should never spawn\nNames should be written using the modid:name model similar to the below examples, if no modid is provided 'minecraft' will be applied by default.");
   public static final IntegerOption SPAWN_CHANCE_TRAINING_STRUCTURES = new IntegerOption(25, 0, 100, "Training Structure Spawn Chance", "Sets the % chance for a training structure to spawn\nDefault: 50");
   public static final IntegerOption SPAWN_CHANCE_SKY_ISLANDS = new IntegerOption(25, 0, 100, "Sky Islands Spawn Chance", "Sets the % chance for a Sky Island to spawn, the % is calculated per chunk (16x16)\nDefault: 50");
   public static final IntegerOption SPAWN_CHANCE_GHOST_SHIPS = new IntegerOption(25, 0, 100, "Ghost Ships Spawn Chance", "Sets the % chance for a Ghost Ship to spawn, the % is calculated per chunk (16x16)\nDefault: 50");
   public static final IntegerOption SPAWN_CHANCE_SMALL_SHIPS = new IntegerOption(25, 0, 100, "Small Ships Spawn Chance", "Sets the % chance for a Small Ship to spawn, the % is calculated per chunk (16x16)\nDefault: 50");
   public static final IntegerOption SPAWN_CHANCE_MEDIUM_SHIPS = new IntegerOption(25, 0, 100, "Medium Ships Spawn Chance", "Sets the % chance for a Medium Ship to spawn, the % is calculated per chunk (16x16)\nDefault: 50");
   public static final IntegerOption SPAWN_CHANCE_LARGE_SHIPS = new IntegerOption(25, 0, 100, "Large Ships Spawn Chance", "Sets the % chance for a Large Ship to spawn, the % is calculated per chunk (16x16)\nDefault: 50");
   public static final IntegerOption SPAWN_CHANCE_CAMPS = new IntegerOption(10, 0, 100, "Camps Spawn Chance", "Sets the % chance for a Camp to spawn, the % is calculated per chunk (16x16)\nDefault: 10");
   public static final IntegerOption SPAWN_CHANCE_SMALL_BASES = new IntegerOption(25, 0, 100, "Small Bases Spawn Chance", "Sets the % chance for a Small Base to spawn, the % is calculated per chunk (16x16)\nDefault: 50");
   public static final IntegerOption SPAWN_CHANCE_LARGE_BASES = new IntegerOption(25, 0, 100, "Large Bases Spawn Chance", "Sets the % chance for a Large Base to spawn, the % is calculated per chunk (16x16)\nDefault: 50");
   public static final IntegerOption SPAWN_CHANCE_WATCH_TOWERS = new IntegerOption(25, 0, 100, "Watch Towers Spawn Chance", "Sets the % chance for a Watch Tower to spawn, the % is calculated per chunk (16x16)\nDefault: 50");
   public static final IntegerOption SPAWN_CHANCE_PONEGLYPHS = new IntegerOption(10, 0, 100, "Poneglyphs Spawn Chance", "Sets the % chance for a Poneglyph to spawn, the % is calculated per chunk (16x16)\nDefault: 10");
   public static final IntegerOption KAIROSEKI_SPAWN_COUNT = new IntegerOption(2, 1, 20, "Kairoseki Count", "Kairoseki count per chunk\nDefault: 2");
}
