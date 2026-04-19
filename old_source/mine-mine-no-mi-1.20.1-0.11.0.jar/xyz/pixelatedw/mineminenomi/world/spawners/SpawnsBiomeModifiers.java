package xyz.pixelatedw.mineminenomi.world.spawners;

import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class SpawnsBiomeModifiers {
   public static final ResourceKey<BiomeModifier> SPAWN_DUGONGS = registerKey("spawn_dugongs");
   public static final ResourceKey<BiomeModifier> SPAWN_WANDERING_DUGONG = registerKey("spawn_wandering_dugong");
   public static final ResourceKey<BiomeModifier> SPAWN_BIG_DUCK = registerKey("spawn_big_duck");
   public static final ResourceKey<BiomeModifier> SPAWN_BANANAWANI = registerKey("spawn_bananawani");
   public static final ResourceKey<BiomeModifier> SPAWN_DEN_DEN_MUSHI = registerKey("spawn_den_den_mushi");
   public static final ResourceKey<BiomeModifier> SPAWN_GORILLAS = registerKey("spawn_gorillas");
   public static final ResourceKey<BiomeModifier> SPAWN_HUMANDRILL = registerKey("spawn_humandrill");
   public static final ResourceKey<BiomeModifier> SPAWN_LAPAHN = registerKey("spawn_lapahn");
   public static final ResourceKey<BiomeModifier> SPAWN_WHITE_WALKIE = registerKey("spawn_white_walkie");
   public static final ResourceKey<BiomeModifier> SPAWN_FIGHTING_FISH = registerKey("spawn_fighting_fish");
   public static final ResourceKey<BiomeModifier> SPAWN_NORMAL_FISH = registerKey("spawn_normal_fish");
   public static final ResourceKey<BiomeModifier> SPAWN_FLYING_FISH = registerKey("spawn_flying_fish");
   public static final ResourceKey<BiomeModifier> SPAWN_MARINES = registerKey("spawn_marines");
   public static final ResourceKey<BiomeModifier> SPAWN_PIRATES = registerKey("spawn_pirates");
   public static final ResourceKey<BiomeModifier> SPAWN_BANDITS = registerKey("spawn_bandits");

   public static void bootstrap(BootstapContext<BiomeModifier> context) {
      HolderGetter<Biome> biomes = context.m_255420_(Registries.f_256952_);
      context.m_255272_(SPAWN_FIGHTING_FISH, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_FIGHTING_FISH), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.FIGHTING_FISH.get(), 1, 1, 1))));
      context.m_255272_(SPAWN_NORMAL_FISH, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_FISH), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.PANDA_SHARK.get(), 4, 1, 1))));
      context.m_255272_(SPAWN_FLYING_FISH, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.IS_SWAMP), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.FLYING_FISH.get(), 1, 1, 1))));
      context.m_255272_(SPAWN_DUGONGS, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_DUGONGS), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.KUNG_FU_DUGONG.get(), 12, 3, 5), new MobSpawnSettings.SpawnerData((EntityType)ModMobs.BOXING_DUGONG.get(), 7, 1, 3), new MobSpawnSettings.SpawnerData((EntityType)ModMobs.WRESTLING_DUGONG.get(), 7, 1, 3), new MobSpawnSettings.SpawnerData((EntityType)ModMobs.LEGENDARY_MASTER_DUGONG.get(), 2, 1, 1))));
      context.m_255272_(SPAWN_WANDERING_DUGONG, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_WANDERING_DUGONG), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.WANDERING_DUGONG.get(), 200, 1, 1))));
      context.m_255272_(SPAWN_BIG_DUCK, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_BIG_DUCK), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.BIG_DUCK.get(), 5, 1, 4))));
      context.m_255272_(SPAWN_BANANAWANI, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_BANANAWANI), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.BANANAWANI.get(), 7, 1, 2))));
      context.m_255272_(SPAWN_DEN_DEN_MUSHI, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_DEN_DEN_MUSHI), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.DEN_DEN_MUSHI.get(), 8, 1, 4))));
      context.m_255272_(SPAWN_GORILLAS, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_GORILLAS), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.BLUGORI.get(), 8, 1, 3), new MobSpawnSettings.SpawnerData((EntityType)ModMobs.BLAGORI.get(), 3, 1, 1))));
      context.m_255272_(SPAWN_HUMANDRILL, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_HUMANDRILL), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.HUMANDRILL.get(), 8, 1, 3))));
      context.m_255272_(SPAWN_LAPAHN, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_LAPAHN), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.LAPAHN.get(), 8, 1, 3))));
      context.m_255272_(SPAWN_WHITE_WALKIE, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_WHITE_WALKIE), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.WHITE_WALKIE.get(), 5, 1, 2))));
      context.m_255272_(SPAWN_MARINES, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_HUMANS), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.MARINE_GRUNT.get(), 940, 2, 4), new MobSpawnSettings.SpawnerData((EntityType)ModMobs.MARINE_BRUTE.get(), 50, 1, 2), new MobSpawnSettings.SpawnerData((EntityType)ModMobs.MARINE_SNIPER.get(), 10, 1, 1), new MobSpawnSettings.SpawnerData((EntityType)ModMobs.MARINE_CAPTAIN.get(), 5, 1, 1), new MobSpawnSettings.SpawnerData((EntityType)ModMobs.PACIFISTA.get(), 1, 1, 1))));
      context.m_255272_(SPAWN_PIRATES, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_HUMANS), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.PIRATE_GRUNT.get(), 96, 2, 4), new MobSpawnSettings.SpawnerData((EntityType)ModMobs.PIRATE_BRUTE.get(), 2, 1, 2), new MobSpawnSettings.SpawnerData((EntityType)ModMobs.PIRATE_CAPTAIN.get(), 2, 1, 1))));
      context.m_255272_(SPAWN_BANDITS, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes.m_254956_(ModTags.Biomes.CAN_SPAWN_HUMANS), List.of(new MobSpawnSettings.SpawnerData((EntityType)ModMobs.BANDIT_GRUNT.get(), 94, 2, 4), new MobSpawnSettings.SpawnerData((EntityType)ModMobs.BANDIT_BRUTE.get(), 5, 1, 2), new MobSpawnSettings.SpawnerData((EntityType)ModMobs.BANDIT_SNIPER.get(), 1, 1, 1))));
   }

   private static ResourceKey<BiomeModifier> registerKey(String name) {
      return ResourceKey.m_135785_(Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath("mineminenomi", name));
   }
}
