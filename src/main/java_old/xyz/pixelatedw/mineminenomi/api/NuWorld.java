package xyz.pixelatedw.mineminenomi.api;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.FullChunkStatus;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.api.challenges.InProgressChallenge;
import xyz.pixelatedw.mineminenomi.api.protection.BlockProtectionRule;
import xyz.pixelatedw.mineminenomi.api.protection.ProtectedArea;
import xyz.pixelatedw.mineminenomi.api.util.DBlockPos;
import xyz.pixelatedw.mineminenomi.config.ServerConfig;
import xyz.pixelatedw.mineminenomi.data.world.ChallengesWorldData;
import xyz.pixelatedw.mineminenomi.data.world.ProtectedAreasData;
import xyz.pixelatedw.mineminenomi.init.ModTags;

public class NuWorld {
   /** @deprecated */
   @Deprecated
   public static boolean canPlaceBlock(Level world, double posX, double posY, double posZ, BlockState toPlace, int flag, BlockProtectionRule rule) {
      BlockPos pos = new BlockPos((int)posX, (int)posY, (int)posZ);
      if (world.m_151570_(pos)) {
         return false;
      } else {
         BlockState currentBlockState = world.m_8055_(pos);
         ProtectedAreasData worldData = ProtectedAreasData.get((ServerLevel)world);
         Optional<ProtectedArea> area = worldData.getProtectedArea((int)posX, (int)posY, (int)posZ);
         boolean isGriefDisabled = !ServerConfig.isAbilityGriefingEnabled();
         boolean isGriefBypass = false;
         if (!isGriefBypass) {
            if (isGriefDisabled) {
               return false;
            }

            if (area.isPresent() && !((ProtectedArea)area.get()).canDestroyBlocks()) {
               return false;
            }
         }

         if (rule.check(world, pos, currentBlockState)) {
            if (currentBlockState.m_60713_(Blocks.f_50003_)) {
               WyDebug.debug(pos);
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public static boolean canMobGrief(@Nullable Entity entity) {
      if (entity == null) {
         return true;
      } else if (entity instanceof Mob) {
         if (isChallengeDimension(entity.m_9236_())) {
            return true;
         } else {
            return ForgeEventFactory.getMobGriefingEvent(entity.m_9236_(), entity);
         }
      } else {
         return !(entity instanceof Player) ? ForgeEventFactory.getMobGriefingEvent(entity.m_9236_(), entity) : true;
      }
   }

   public static boolean setBlockState(@Nullable Entity entity, BlockPos pos, BlockState toPlace, int flag, @Nullable BlockProtectionRule rule) {
      if (entity == null) {
         return false;
      } else {
         return !canMobGrief(entity) ? false : setBlockState(entity.m_9236_(), pos, toPlace, flag, rule);
      }
   }

   public static boolean setBlockState(Level world, BlockPos pos, BlockState toPlace, int flag, @Nullable BlockProtectionRule rule) {
      if (world.m_151570_(pos)) {
         return false;
      } else if (!toPlace.m_60795_() && !isWithinChallengeArenaBounds(world, pos)) {
         return false;
      } else {
         BlockState currentBlockState = world.m_8055_(pos);
         ProtectedAreasData worldData = ProtectedAreasData.get((ServerLevel)world);
         if (worldData == null) {
            return false;
         } else {
            Optional<ProtectedArea> area = worldData.getProtectedArea(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
            boolean isGriefDisabled = !ServerConfig.isAbilityGriefingEnabled();
            boolean isGriefBypass = false;
            boolean canPlace = !currentBlockState.m_204336_(ModTags.Blocks.BLOCK_PROT_RESTRICTED);
            boolean canActuallyPlace = true;
            if (rule != null) {
               canPlace &= rule.check(world, pos, currentBlockState);
            }

            if (isChallengeDimension(world)) {
               isGriefBypass = true;
               isGriefDisabled = false;
            }

            if (!isGriefBypass) {
               if (isGriefDisabled) {
                  return false;
               }

               if (area.isPresent()) {
                  if (!((ProtectedArea)area.get()).canDestroyBlocks()) {
                     return false;
                  }

                  if (((ProtectedArea)area.get()).canDestroyBlocks() && ((ProtectedArea)area.get()).canRestoreBlocks()) {
                     DBlockPos dpos = new DBlockPos(pos.m_123341_(), pos.m_123342_(), pos.m_123343_(), world.m_46472_());
                     BlockSnapshot snapshot = BlockSnapshot.create(world.m_46472_(), world, pos, 2);
                     ((ProtectedArea)area.get()).queueForRestoration((BlockPos)dpos, (ProtectedArea.RestorationEntry)(new ProtectedArea.RestorationEntry(world.m_46467_(), snapshot)));
                  }
               }
            }

            if (canPlace) {
               if (canActuallyPlace) {
                  setBlockStateInChunk(world, pos, toPlace, flag);
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   public static boolean setBlockStateInChunk(Level world, BlockPos pos, BlockState newState, int flags) {
      if (world instanceof ServerLevel && isChallengeDimension(world) && (flags & 512) != 0) {
         try {
            boolean placed = swapBlockData(world, pos, newState) != null;
            return placed;
         } catch (Exception ex) {
            ex.printStackTrace();
         }
      }

      if (world.m_151570_(pos)) {
         return false;
      } else if (!world.f_46443_ && world.m_46659_()) {
         return false;
      } else {
         LevelChunk chunk = world.m_46745_(pos);
         pos = pos.m_7949_();
         BlockState old = world.m_8055_(pos);
         int oldLight = old.getLightEmission(world, pos);
         int oldOpacity = old.m_60739_(world, pos);
         BlockState blockstate = chunk.m_6978_(pos, newState, (flags & 64) != 0);
         if (world instanceof ServerLevel && !newState.m_60795_() && isChallengeDimension(world)) {
            InProgressChallenge challenge = ChallengesWorldData.get().getInProgressChallengeFor((ServerLevel)world);
            challenge.trackBlockPos(pos);
         }

         if (blockstate != null) {
            BlockState blockstate1 = world.m_8055_(pos);
            if ((flags & 128) == 0 && blockstate1 != blockstate && (blockstate1.m_60739_(world, pos) != oldOpacity || blockstate1.getLightEmission(world, pos) != oldLight || blockstate1.m_60787_() || blockstate.m_60787_())) {
               world.m_46473_().m_6180_("queueCheckLight");
               world.m_7726_().m_7827_().m_7174_(pos);
               world.m_46473_().m_7238_();
            }

            if ((flags & 256) != 0) {
               world.markAndNotifyBlock(pos, chunk, blockstate, newState, flags, 512);
            } else if ((flags & 2) != 0 && (!world.f_46443_ || (flags & 4) == 0) && (world.f_46443_ || chunk.m_287138_() != null && chunk.m_287138_().m_287205_(FullChunkStatus.BLOCK_TICKING))) {
               world.m_7260_(pos, blockstate, newState, flags);
            }
         }

         return true;
      }
   }

   public static @Nullable BlockState swapBlockData(LevelAccessor world, BlockPos pos, BlockState newState) {
      ChunkAccess chunk = world.m_46865_(pos);
      LevelChunkSection cs = chunk.m_183278_(chunk.m_151564_(pos.m_123342_()));
      boolean flag = cs.m_188008_();
      if (flag && newState.m_60795_()) {
         return null;
      } else {
         int x = pos.m_123341_() & 15;
         int y = pos.m_123342_() & 15;
         int z = pos.m_123343_() & 15;
         BlockState state = (BlockState)cs.m_63019_().m_63127_(x, y, z, newState);
         if (state == newState) {
            return null;
         } else {
            chunk.m_8092_(true);
            return state;
         }
      }
   }

   public static boolean isWithinChallengeArenaBounds(Level world, BlockPos pos) {
      if (!isChallengeDimension(world)) {
         return true;
      } else if (world.f_46443_) {
         return false;
      } else {
         ServerLevel sworld = (ServerLevel)world;
         InProgressChallenge ch = ChallengesWorldData.get().getInProgressChallengeFor(sworld);
         if (ch == null) {
            return false;
         } else {
            BoundingBox mbb = ch.getArena().getArenaLimits();
            BoundingBox nmmb = new BoundingBox(mbb.m_162395_() - 4, mbb.m_162396_() - 4, mbb.m_162398_() - 4, mbb.m_162399_() + 3, mbb.m_162400_() + 3, mbb.m_162401_() + 3);
            mbb = nmmb.m_71045_(ch.getArenaPos().m_123341_(), ch.getArenaPos().m_123342_(), ch.getArenaPos().m_123343_());
            return mbb.m_71051_(pos);
         }
      }
   }

   public static boolean isChallengeDimension(Level world) {
      return isChallengeDimension(world.m_46472_());
   }

   public static boolean isChallengeDimension(ResourceKey<Level> world) {
      return world.m_135782_().toString().contains("challenges_");
   }
}
