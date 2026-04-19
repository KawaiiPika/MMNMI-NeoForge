package xyz.pixelatedw.mineminenomi.config;

import xyz.pixelatedw.mineminenomi.api.config.options.BooleanOption;
import xyz.pixelatedw.mineminenomi.api.config.options.DoubleOption;
import xyz.pixelatedw.mineminenomi.api.config.options.IntegerOption;

public class WorldEventsConfig {
   public static final BooleanOption SPAWN_WORLD_HUMANOIDS = new BooleanOption(true, "World Humanoids Spawns", "Allows random aggressive faction humans to spawn in the world (Marines, Pirates, Bandits)\nDefault: true");
   public static final BooleanOption SPAWN_WORLD_ANIMALS = new BooleanOption(true, "World Animals Spawns", "Allows random animal NPCs to spawn in the world (Dugongs, Bananwanis, Gorillas etc.)\nDefault: true");
   public static final BooleanOption SPAWN_NOTORIOUS_TARGETS = new BooleanOption(true, "Spawn Notorious Targets", "Allows Notorious Target events to spawn in the world\nDefault: true");
   public static final BooleanOption SPAWN_CARAVANS = new BooleanOption(true, "Spawn Caravans", "Allows Caravan events to spawn in the world\nDefault: true");
   public static final BooleanOption SPAWN_CELESTIAL_VISITS = new BooleanOption(true, "Spawn Celestial Visits", "Allows Celestial Visit events to spawn in the world\nDefault: true");
   public static final IntegerOption TIME_BETWEEN_TRADER_SPAWNS = new IntegerOption(2, 0, 30, "Time Between Trader Spawns", "Determines the time (in minutes) between two spawn attempts\nDefault: 2 (minutes)");
   public static final IntegerOption SPAWN_CHANCE_TRADER = new IntegerOption(1, 0, 100, "Chance for Trader Spawns", "Determines the % chance for a trader to spawn\nDefault: 1");
   public static final IntegerOption TIME_BETWEEN_TRAINER_SPAWNS = new IntegerOption(2, 0, 30, "Time Between Trainer Spawns", "Determines the time (in minutes) between two spawn attempts\nDefault: 2 (minutes)");
   public static final IntegerOption SPAWN_CHANCE_TRAINER = new IntegerOption(15, 0, 100, "Chance for Trainer Spawns", "Determines the % chance for a trainer to spawn\nDefault: 15");
   public static final IntegerOption TIME_BETWEEN_AMBUSH_SPAWNS = new IntegerOption(15, 0, 30, "Time Between Ambush Spawns", "Determines the time (in minutes) between two spawn attempts\nDefault: 15 (minutes)");
   public static final IntegerOption SPAWN_CHANCE_AMBUSH = new IntegerOption(15, 0, 100, "Chance for Ambush Spawns", "Determines the % chance for an ambush to spawn\nDefault: 15");
   public static final DoubleOption GRUNT_SPAWN_CHANCE = new DoubleOption((double)100.0F, (double)0.0F, (double)100.0F, "Grunts Spawn Chance", "Chance for grunts to spawn in world\nDefault: 100");
   public static final DoubleOption BRUTE_SPAWN_CHANCE = new DoubleOption((double)100.0F, (double)0.0F, (double)100.0F, "Brutes Spawn Chance", "Chance for brutes to spawn in world\nDefault: 100");
   public static final DoubleOption SNIPER_SPAWN_CHANCE = new DoubleOption((double)100.0F, (double)0.0F, (double)100.0F, "Snipers Spawn Chance", "Chance for snipers to spawn in world\nDefault: 100");
   public static final DoubleOption CAPTAIN_SPAWN_CHANCE = new DoubleOption((double)100.0F, (double)0.0F, (double)100.0F, "Captains Spawn Chance", "Chance for captains to spawn in world\nDefault: 100");
   public static final DoubleOption PACIFISTA_SPAWN_CHANCE = new DoubleOption((double)100.0F, (double)0.0F, (double)100.0F, "Pacifistas Spawn Chance", "Chance for pacifistas to spawn in world\nDefault: 100");
}
