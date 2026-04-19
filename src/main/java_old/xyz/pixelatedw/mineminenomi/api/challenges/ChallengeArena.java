package xyz.pixelatedw.mineminenomi.api.challenges;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import xyz.pixelatedw.mineminenomi.api.blockgen.BlockQueue;

public abstract class ChallengeArena {
   private ArenaStyle style;

   public ChallengeArena(ArenaStyle style) {
      this.style = style;
   }

   public abstract void buildArena(InProgressChallenge var1, BlockQueue var2);

   public abstract BoundingBox getArenaLimits();

   public int getGroundLevel() {
      return 32;
   }

   public ArenaStyle getStyle() {
      return this.style;
   }

   public static class SpawnPosition {
      private BlockPos pos;
      private float pitch;
      private float yaw;

      public SpawnPosition(BlockPos pos, float yaw, float pitch) {
         this.pos = pos;
         this.pitch = pitch;
         this.yaw = yaw;
      }

      public BlockPos getPos() {
         return this.pos;
      }

      public float getPitch() {
         return this.pitch;
      }

      public float getYaw() {
         return this.yaw;
      }
   }

   public static class EnemySpawn {
      private final LivingEntity entity;
      private final SpawnPosition spawnPos;

      public EnemySpawn(LivingEntity entity, SpawnPosition pos) {
         this.entity = entity;
         this.spawnPos = pos;
      }

      public LivingEntity getEntity() {
         return this.entity;
      }

      public SpawnPosition getSpawnPos() {
         return this.spawnPos;
      }
   }
}
