package xyz.pixelatedw.mineminenomi.world.spawners;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.NaturalSpawner;
import xyz.pixelatedw.mineminenomi.api.WyDebug;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.entities.mobs.TraderEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class TraderSpawner {
   private Random random = new Random();
   private int cooldown;
   private static final List<Supplier<? extends EntityType<? extends TraderEntity>>> TRADER_TYPES;

   public void tick(ServerLevel world) {
      world.m_46473_().m_6180_("traderSpawnerTick");
      if (--this.cooldown <= 0) {
         this.cooldown = ServerConfig.getTimeBetweenTraderSpawns();
         if (this.random.nextInt(100) <= ServerConfig.getChanceForTraderSpawn()) {
            this.spawn(world);
         }
      }

      world.m_46473_().m_7238_();
   }

   public void spawn(ServerLevel world) {
      world.m_46473_().m_6180_("traderSpawnerSpawn");
      int listSize = Mth.m_14045_(world.m_6907_().size() / 3, 1, 10);
      Player[] cachedPlayers = new Player[listSize];

      for(int i = 0; i < cachedPlayers.length; ++i) {
         Player player = world.m_8890_();
         if (player != null) {
            boolean alreadyCached = Arrays.stream(cachedPlayers).anyMatch((target) -> target == player);
            if (!alreadyCached) {
               cachedPlayers[i] = player;
               int r = this.random.nextInt(TRADER_TYPES.size());
               EntityType<? extends TraderEntity> entityType = (EntityType)((Supplier)TRADER_TYPES.get(r)).get();
               BlockPos targetPos = player.m_20183_();
               BlockPos spawnPos = WyHelper.findOnGroundSpawnLocation(world, entityType, targetPos, 20);
               List<TraderEntity> traders = WyHelper.<TraderEntity>getNearbyEntities(player.m_20182_(), world, (double)40.0F, (Predicate)null, TraderEntity.class);
               if (spawnPos == null) {
                  return;
               }

               boolean canSpawn = traders.size() < 3 && NaturalSpawner.m_47051_(Type.ON_GROUND, world, spawnPos, entityType);
               if (canSpawn) {
                  entityType.m_20592_(world, (ItemStack)null, (Player)null, spawnPos, MobSpawnType.EVENT, false, false);
                  String var10000 = String.valueOf(entityType);
                  WyDebug.debug("Trader (" + var10000 + ") spawned at: " + String.valueOf(spawnPos));
               }
            }
         }
      }

      world.m_46473_().m_7238_();
   }

   static {
      TRADER_TYPES = Arrays.asList(ModMobs.MARINE_TRADER, ModMobs.PIRATE_TRADER);
   }
}
