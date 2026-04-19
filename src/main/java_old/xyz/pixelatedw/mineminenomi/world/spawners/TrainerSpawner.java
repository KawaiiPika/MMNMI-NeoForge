package xyz.pixelatedw.mineminenomi.world.spawners;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ITrainer;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class TrainerSpawner {
   private static final Predicate<Entity> TRAINER_CHECK = (target) -> target instanceof ITrainer;
   private Random random = new Random();
   private int cooldown;

   public void tick(ServerLevel world) {
      world.m_46473_().m_6180_("trainerSpawnerTick");
      if (--this.cooldown <= 0) {
         this.cooldown = ServerConfig.getTimeBetweenTrainerSpawns();
         if (this.random.nextInt(100) <= ServerConfig.getChanceForTrainerSpawn()) {
            this.spawn(world);
         }
      }

      world.m_46473_().m_7238_();
   }

   public void spawn(ServerLevel world) {
      world.m_46473_().m_6180_("trainerSpawnerSpawn");
      int listSize = Mth.m_14045_(world.m_6907_().size() / 3, 1, 10);
      Player[] cachedPlayers = new Player[listSize];

      for(int i = 0; i < cachedPlayers.length; ++i) {
         Player player = world.m_8890_();
         if (player != null) {
            boolean alreadyCached = Arrays.stream(cachedPlayers).anyMatch((target) -> target == player);
            if (!alreadyCached) {
               cachedPlayers[i] = player;
               IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
               if (props != null) {
                  EntityType<? extends ITrainer> entityType = null;
                  BlockPos targetPos = player.m_20183_();
                  Holder<Biome> biome = world.m_204166_(targetPos);
                  if (props.isSwordsman()) {
                     entityType = (EntityType)ModMobs.SWORDSMAN_TRAINER.get();
                  } else if (props.isSniper()) {
                     entityType = (EntityType)ModMobs.SNIPER_TRAINER.get();
                  } else if (props.isWeatherWizard()) {
                     entityType = (EntityType)ModMobs.ART_OF_WEATHER_TRAINER.get();
                  } else if (props.isDoctor()) {
                     entityType = (EntityType)ModMobs.DOCTOR_TRAINER.get();
                  } else if (props.isBrawler()) {
                     entityType = (EntityType)ModMobs.BRAWLER_TRAINER.get();
                  } else if (props.isBlackLeg()) {
                     entityType = (EntityType)ModMobs.BLACK_LEG_TRAINER.get();
                  }

                  boolean dugongMasterChance = (double)world.m_213780_().m_188501_() <= (double)0.25F;
                  if (dugongMasterChance && biome.m_203656_(ModTags.Biomes.CAN_SPAWN_DUGONGS)) {
                     entityType = (EntityType)ModMobs.LEGENDARY_MASTER_DUGONG.get();
                  }

                  if (entityType == null) {
                     return;
                  }

                  BlockPos spawnPos = WyHelper.findOnGroundSpawnLocation(world, entityType, targetPos, 20);
                  if (spawnPos == null) {
                     return;
                  }

                  List<LivingEntity> trainers = WyHelper.<LivingEntity>getNearbyEntities(new Vec3((double)spawnPos.m_123341_(), (double)spawnPos.m_123342_(), (double)spawnPos.m_123343_()), world, (double)100.0F, TRAINER_CHECK, LivingEntity.class);
                  boolean canSpawn = trainers.size() < 2 && NaturalSpawner.m_47051_(Type.ON_GROUND, world, spawnPos, entityType);
                  if (canSpawn) {
                     entityType.m_20592_(world, (ItemStack)null, (Player)null, spawnPos, MobSpawnType.EVENT, false, false);
                     String var10000 = String.valueOf(entityType);
                     WyDebug.debug("Trainer (" + var10000 + ") spawned at: " + String.valueOf(spawnPos));
                  }
               }
            }
         }
      }

      world.m_46473_().m_7238_();
   }
}
