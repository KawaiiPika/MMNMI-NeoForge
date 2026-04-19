package xyz.pixelatedw.mineminenomi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.pixelatedw.mineminenomi.api.MappedTag;

public class ModTags {
   public static void init() {
      ModTags.Blocks.init();
      ModTags.Items.init();
      ModTags.Entities.init();
      ModTags.DamageTypes.init();
      ModTags.Structures.init();
      ModTags.Biomes.init();
   }

   public static class Items {
      public static final MappedTag<Item> IRON;
      public static final MappedTag<Item> CONDUCTIVE;
      public static final TagKey<Item> KAIROSEKI;
      public static final TagKey<Item> MAGNETIC;
      public static final TagKey<Item> RUSTY;
      public static final TagKey<Item> RUST_IMMUNITY;
      public static final TagKey<Item> BLUNTS;
      public static final TagKey<Item> GUNS;
      public static final TagKey<Item> SUPREME_GRADE;
      public static final TagKey<Item> GREAT_GRADE;
      public static final TagKey<Item> CLIMA_TACTS;
      public static final TagKey<Item> GUN_AMMO;
      public static final TagKey<Item> BAZOOKA_AMMO;
      public static final TagKey<Item> RANGED_ENCHANTABLE_BY_SMITHING;
      public static final TagKey<Item> MELEE_ENCHANTABLE_BY_SMITHING;
      public static final TagKey<Item> PARAMECIA;
      public static final TagKey<Item> LOGIA;
      public static final TagKey<Item> ZOAN;
      public static final TagKey<Item> DEVIL_FRUIT;
      public static final TagKey<Item> MANGROVE_LOGS;
      public static final TagKey<Item> FRUIT_REINCARNATION;
      public static final TagKey<Item> BANNED_ITEMS_CHALLANGES;
      public static final TagKey<Item> HARDENING_DAMAGE_BONUS;
      public static final TagKey<Item> IMBUING_DAMAGE_BONUS;

      private static void init() {
      }

      private static TagKey<Item> tag(String id) {
         return ItemTags.create(ResourceLocation.fromNamespaceAndPath("mineminenomi", id));
      }

      static {
         IRON = new MappedTag<Item>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "iron_values"), "mapped_tags/items", ForgeRegistries.ITEMS);
         CONDUCTIVE = new MappedTag<Item>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "conductive_values"), "mapped_tags/items", ForgeRegistries.ITEMS);
         KAIROSEKI = tag("kairoseki");
         MAGNETIC = tag("magnetic");
         RUSTY = tag("rusty");
         RUST_IMMUNITY = tag("rust_immunity");
         BLUNTS = tag("blunts");
         GUNS = tag("guns");
         SUPREME_GRADE = tag("supreme_grade");
         GREAT_GRADE = tag("great_grade");
         CLIMA_TACTS = tag("clima_tacts");
         GUN_AMMO = tag("gun_ammo");
         BAZOOKA_AMMO = tag("bazooka_ammo");
         RANGED_ENCHANTABLE_BY_SMITHING = tag("ranged_enchantable_by_smithing");
         MELEE_ENCHANTABLE_BY_SMITHING = tag("melee_enchantable_by_smithing");
         PARAMECIA = tag("paramecia");
         LOGIA = tag("logia");
         ZOAN = tag("zoan");
         DEVIL_FRUIT = tag("devil_fruit");
         MANGROVE_LOGS = tag("mangrove_logs");
         FRUIT_REINCARNATION = tag("fruit_reincarnation");
         BANNED_ITEMS_CHALLANGES = tag("banned_items_challenges");
         HARDENING_DAMAGE_BONUS = tag("hardening_damage_bonus");
         IMBUING_DAMAGE_BONUS = tag("imbuing_damage_bonus");
      }
   }

   public static class Blocks {
      public static final TagKey<Block> KAIROSEKI = tag("kairoseki");
      public static final TagKey<Block> NO_PHASE = tag("nophase");
      public static final TagKey<Block> RUSTY = tag("rusty");
      public static final TagKey<Block> MANGROVE_LOGS = tag("mangrove_logs");
      public static final TagKey<Block> VANILLA_AIR = tag("vanilla/air");
      public static final TagKey<Block> VANILLA_BASALT = tag("vanilla/basalt");
      public static final TagKey<Block> VANILLA_BLACKSTONE = tag("vanilla/blackstone");
      public static final TagKey<Block> VANILLA_CONCRETE = tag("vanilla/concrete");
      public static final TagKey<Block> VANILLA_CONCRETE_POWDER = tag("vanilla/concrete_powder");
      public static final TagKey<Block> VANILLA_DEAD_CORAL_BLOCKS = tag("vanilla/dead_coral_blocks");
      public static final TagKey<Block> VANILLA_DEAD_CORAL_PLANTS = tag("vanilla/dead_coral_plants");
      public static final TagKey<Block> VANILLA_DEAD_CORALS = tag("vanilla/dead_corals");
      public static final TagKey<Block> VANILLA_DEAD_WALL_CORALS = tag("vanilla/dead_wall_corals");
      public static final TagKey<Block> VANILLA_DEEPSLATE = tag("vanilla/deepslate");
      public static final TagKey<Block> VANILLA_GLAZED_TERRACOTTA = tag("vanilla/glazed_terracotta");
      public static final TagKey<Block> VANILLA_PRISMARINE = tag("vanilla/prismarine");
      public static final TagKey<Block> VANILLA_PURPUR = tag("vanilla/purpur");
      public static final TagKey<Block> VANILLA_QUARTZ = tag("vanilla/quartz");
      public static final TagKey<Block> LOGIA_BLOCK_PASS_HIE = tag("logia_block_pass/hie");
      public static final TagKey<Block> LOGIA_BLOCK_PASS_GORO = tag("logia_block_pass/goro");
      public static final TagKey<Block> LOGIA_BLOCK_PASS_PIKA = tag("logia_block_pass/pika");
      public static final TagKey<Block> LOGIA_BLOCK_PASS_SUNA = tag("logia_block_pass/suna");
      public static final TagKey<Block> LOGIA_BLOCK_PASS_YUKI = tag("logia_block_pass/yuki");
      public static final TagKey<Block> BLOCK_PROT_CORE = tag("block_protection/core");
      public static final TagKey<Block> BLOCK_PROT_FOLIAGE = tag("block_protection/foliage");
      public static final TagKey<Block> BLOCK_PROT_LIQUIDS = tag("block_protection/liquids");
      public static final TagKey<Block> BLOCK_PROT_WATER = tag("block_protection/water");
      public static final TagKey<Block> BLOCK_PROT_AIR = tag("block_protection/air");
      public static final TagKey<Block> BLOCK_PROT_RESTRICTED = tag("block_protection/restricted");
      public static final TagKey<Block> BLOCK_PROT_SNOW = tag("block_protection/snow");
      public static final TagKey<Block> BLOCK_PROT_OCEAN_PLANTS = tag("block_protection/ocean_plants");
      public static final TagKey<Block> BLOCK_PROT_LAVA_IMMUNE = tag("block_protection/lava_immune");
      public static final TagKey<Block> BLOCK_PROT_LAVA = tag("block_protection/lava");

      private static void init() {
      }

      private static TagKey<Block> tag(String id) {
         return BlockTags.create(ResourceLocation.fromNamespaceAndPath("mineminenomi", id));
      }
   }

   public static class Entities {
      public static final MappedTag<EntityType<?>> CONDUCTIVE;
      public static final TagKey<EntityType<?>> MAGNETIC;
      public static final TagKey<EntityType<?>> KAIROSEKI;

      private static void init() {
      }

      private static TagKey<EntityType<?>> tag(String id) {
         return TagKey.m_203882_(Registries.f_256939_, ResourceLocation.fromNamespaceAndPath("mineminenomi", id));
      }

      static {
         CONDUCTIVE = new MappedTag<EntityType<?>>(ResourceLocation.fromNamespaceAndPath("mineminenomi", "conductive_values"), "mapped_tags/entity_types", ForgeRegistries.ENTITY_TYPES);
         MAGNETIC = tag("magnetic");
         KAIROSEKI = tag("kairoseki");
      }
   }

   public static class DamageTypes {
      public static final TagKey<DamageType> BYPASSES_LOGIA = tag("bypasses_logia");
      public static final TagKey<DamageType> BYPASSES_FRIENDLY_PROTECTION = tag("bypasses_friendly_protection");
      public static final TagKey<DamageType> UNAVOIDABLE = tag("unavoidable");

      private static void init() {
      }

      private static TagKey<DamageType> tag(String id) {
         return TagKey.m_203882_(Registries.f_268580_, ResourceLocation.fromNamespaceAndPath("mineminenomi", id));
      }
   }

   public static class Structures {
      public static final TagKey<Structure> PIRATE = tag("pirate");
      public static final TagKey<Structure> MARINE = tag("marine");
      public static final TagKey<Structure> BANDIT = tag("bandit");
      public static final TagKey<Structure> REVOLUTIONARY = tag("revolutionary");
      public static final TagKey<Structure> IS_SHIP = tag("is_ship");

      private static void init() {
      }

      private static TagKey<Structure> tag(String id) {
         return TagKey.m_203882_(Registries.f_256944_, ResourceLocation.fromNamespaceAndPath("mineminenomi", id));
      }
   }

   public static class Biomes {
      public static final TagKey<Biome> MINK_DEBUFF_BIOMES = tag("mink_debuff_biomes");
      public static final TagKey<Biome> CAN_SPAWN_DUGONGS = tag("can_spawn/dugongs");
      public static final TagKey<Biome> CAN_SPAWN_WANDERING_DUGONG = tag("can_spawn/wandering_dugong");
      public static final TagKey<Biome> CAN_SPAWN_BIG_DUCK = tag("can_spawn/big_duck");
      public static final TagKey<Biome> CAN_SPAWN_BANANAWANI = tag("can_spawn/bananawani");
      public static final TagKey<Biome> CAN_SPAWN_DEN_DEN_MUSHI = tag("can_spawn/den_den_mushi");
      public static final TagKey<Biome> CAN_SPAWN_GORILLAS = tag("can_spawn/gorillas");
      public static final TagKey<Biome> CAN_SPAWN_HUMANDRILL = tag("can_spawn/humandrill");
      public static final TagKey<Biome> CAN_SPAWN_LAPAHN = tag("can_spawn/lapahn");
      public static final TagKey<Biome> CAN_SPAWN_WHITE_WALKIE = tag("can_spawn/white_walkie");
      public static final TagKey<Biome> CAN_SPAWN_HUMANS = tag("can_spawn/humans");
      public static final TagKey<Biome> CAN_SPAWN_FISH = tag("can_spawn/fish");
      public static final TagKey<Biome> CAN_SPAWN_FIGHTING_FISH = tag("can_spawn/fighting_fish");
      public static final TagKey<Biome> HAS_STRUCTURE_CAMP = tag("has_structure/camp");
      public static final TagKey<Biome> HAS_STRUCTURE_SMALL_BASE = tag("has_structure/small_base");
      public static final TagKey<Biome> HAS_STRUCTURE_LARGE_BASE = tag("has_structure/large_base");
      public static final TagKey<Biome> HAS_STRUCTURE_SKY_ISLAND = tag("has_structure/sky_island");
      public static final TagKey<Biome> HAS_STRUCTURE_TRAINER = tag("has_structure/trainer_building");
      public static final TagKey<Biome> HAS_STRUCTURE_WATCH_TOWER = tag("has_structure/watch_tower");
      public static final TagKey<Biome> HAS_KAIROSEKI_ORE = tag("ores/kairoseki");
      public static final TagKey<Biome> IS_SWAMP = tag("is_swamp");

      private static void init() {
      }

      private static TagKey<Biome> tag(String id) {
         return TagKey.m_203882_(Registries.f_256952_, ResourceLocation.fromNamespaceAndPath("mineminenomi", id));
      }
   }
}
