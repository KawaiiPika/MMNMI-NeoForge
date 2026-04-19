package xyz.pixelatedw.mineminenomi.world.spawners;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.entities.mobs.OPEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class AmbushSpawner {
   private Random random = new Random();
   private int cooldown;

   public void tick(ServerLevel world) {
      world.m_46473_().m_6180_("ambushSpawnerTick");
      if (--this.cooldown <= 0) {
         this.cooldown = ServerConfig.getTimeBetweenAmbushSpawns();
         if (this.random.nextInt(100) <= ServerConfig.getChanceForAmbushSpawn()) {
            this.spawn(world);
         }
      }

      world.m_46473_().m_7238_();
   }

   public void spawn(ServerLevel world) {
      world.m_46473_().m_6180_("ambushSpawnerSpawn");
      Player player = world.m_8890_();
      if (player != null) {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         if (props != null) {
            if (props.isPirate() || props.isBandit() || props.isRevolutionary()) {
               BlockPos targetPos = player.m_20183_();
               long bounty = props.getBounty();
               boolean canSeeSky = player.m_9236_().m_45527_(targetPos);
               if (bounty < 10000L || !canSeeSky) {
                  return;
               }

               EntityType<? extends OPEntity> captainEntity = (EntityType)ModMobs.MARINE_CAPTAIN.get();
               EntityType<? extends OPEntity> gruntEntity = (EntityType)ModMobs.MARINE_GRUNT.get();
               int r = this.random.nextInt(2);
               int r2 = this.random.nextInt(2);
               if (r == 1) {
               }

               List<OPEntity> dangers = WyHelper.<OPEntity>getNearbyEntities(player.m_20182_(), world, (double)80.0F, (Predicate)null, OPEntity.class);
               if (dangers.size() > 50) {
                  return;
               }

               int nrCaptains = 0 + (int)Math.ceil((double)(bounty / 200000L));
               int nrGrunts = 3 + (int)Math.ceil((double)(bounty / 100000L));
               String name = captainEntity.m_20676_().getString();
               if (nrCaptains > 3) {
                  nrCaptains = 3;
               }

               if (nrGrunts > 30) {
                  nrGrunts = 30;
               }

               if (nrCaptains > 0) {
                  for(int i = 0; i < nrCaptains; ++i) {
                     BlockPos spawnPos = WyHelper.findOnGroundSpawnLocation(world, captainEntity, targetPos, 10);
                     if (spawnPos != null) {
                        captainEntity.m_20592_(world, (ItemStack)null, (Player)null, spawnPos, MobSpawnType.EVENT, false, false);
                     }
                  }
               } else {
                  name = "Officer";
               }

               for(int i = 0; i < nrGrunts; ++i) {
                  BlockPos spawnPos = WyHelper.findOnGroundSpawnLocation(world, gruntEntity, targetPos, 20);
                  if (spawnPos != null) {
                     gruntEntity.m_20592_(world, (ItemStack)null, (Player)null, spawnPos, MobSpawnType.EVENT, false, false);
                  }
               }

               Component message = Component.m_237113_("<" + name + "> We've come to arrest you, surrender now criminal scum!");
               if (r2 == 1) {
                  message = Component.m_237113_("<" + name + "> You're surrounded and have no escape, surrender now!");
               }

               player.m_9236_().m_5594_((Player)null, player.m_20183_(), SoundEvents.f_11699_, SoundSource.HOSTILE, 1.0F, 1.0F);
               WyHelper.sendMessage(player, message);
               WyDebug.debug("Ambush spawned around these coords: " + String.valueOf(targetPos));
            }

            world.m_46473_().m_7238_();
         }
      }
   }
}
