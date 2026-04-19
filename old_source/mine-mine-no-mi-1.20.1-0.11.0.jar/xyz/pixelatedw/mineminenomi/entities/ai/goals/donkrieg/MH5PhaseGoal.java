package xyz.pixelatedw.mineminenomi.entities.ai.goals.donkrieg;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.data.world.FactionsWorldData;
import xyz.pixelatedw.mineminenomi.entities.mobs.BruteEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.GruntEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.kriegpirates.DonKriegEntity;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModMobs;
import xyz.pixelatedw.mineminenomi.init.ModNPCGroups;

public class MH5PhaseGoal extends TickedGoal<DonKriegEntity> {
   private BlockPos[] spawnPositions = new BlockPos[4];
   private int findIter = 0;
   private int spawnIter = 0;
   private final FactionsWorldData worldData = FactionsWorldData.get();
   private final int spawnIterMax;
   private final int spawnRate;

   public MH5PhaseGoal(DonKriegEntity entity) {
      super(entity);
      this.spawnRate = entity.isDifficultyHardOrAbove() ? 40 : 60;
      this.spawnIterMax = entity.isDifficultyHardOrAbove() ? 7 : 5;
   }

   public boolean m_8036_() {
      if (this.hasSpawnArrayEmptyPos() && this.findIter < 100) {
         this.findSpawnPos();
         return false;
      } else {
         return ((DonKriegEntity)this.entity).hasMH5PhaseActive();
      }
   }

   public boolean m_8045_() {
      if (!((DonKriegEntity)this.entity).hasMH5PhaseActive()) {
         return false;
      } else {
         return this.spawnIter < this.spawnIterMax;
      }
   }

   public void m_8056_() {
      super.m_8056_();
   }

   public void m_8037_() {
      super.m_8037_();
      if (!((DonKriegEntity)this.entity).m_9236_().f_46443_) {
         if (this.getTickCount() % (long)this.spawnRate == 0L) {
            for(BlockPos pos : this.spawnPositions) {
               if (pos != null) {
                  boolean isBrute = ((DonKriegEntity)this.entity).isDifficultyHardOrAbove() && ((DonKriegEntity)this.entity).m_217043_().m_188503_(3) == 0;
                  Mob spawnEntity;
                  if (isBrute) {
                     spawnEntity = BruteEntity.createPirateBrute((EntityType)ModMobs.PIRATE_BRUTE.get(), ((DonKriegEntity)this.entity).m_9236_());
                     spawnEntity.m_6034_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
                     this.worldData.addTemporaryCrewMember(ModNPCGroups.KRIEG_PIRATES, spawnEntity);
                  } else {
                     spawnEntity = GruntEntity.createPirateGrunt((EntityType)ModMobs.PIRATE_GRUNT.get(), ((DonKriegEntity)this.entity).m_9236_());
                     spawnEntity.m_6034_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
                     this.worldData.addTemporaryCrewMember(ModNPCGroups.KRIEG_PIRATES, spawnEntity);
                  }

                  if (((DonKriegEntity)this.entity).getChallengeInfo().getChallengerGroup().size() > 1) {
                     List<LivingEntity> targets = WyHelper.<LivingEntity>shuffle(((DonKriegEntity)this.entity).getChallengeInfo().getChallengerGroup());
                     spawnEntity.m_6710_((LivingEntity)targets.get(0));
                  } else {
                     spawnEntity.m_6710_(((DonKriegEntity)this.entity).m_5448_());
                  }

                  ((DonKriegEntity)this.entity).m_9236_().m_7967_(spawnEntity);
                  if (((DonKriegEntity)this.entity).m_217043_().m_188503_(3) == 0) {
                     spawnEntity.m_8061_(EquipmentSlot.HEAD, ((Item)ModArmors.MH5_GAS_MASK.get()).m_7968_());
                  }
               }
            }

            ++this.spawnIter;
         }

      }
   }

   public void m_8041_() {
      super.m_8041_();
      this.cleanSpawnArray();
      this.findIter = 0;
   }

   private void findSpawnPos() {
      BlockPos pos = WyHelper.findValidGroundLocation((Level)((DonKriegEntity)this.entity).m_9236_(), (BlockPos)((DonKriegEntity)this.entity).m_20183_(), 20, 10);
      if (pos != null) {
         for(int i = 0; i < this.spawnPositions.length; ++i) {
            if (this.spawnPositions[i] == null) {
               this.spawnPositions[i] = pos;
               break;
            }
         }
      } else {
         ++this.findIter;
      }

   }

   private boolean hasSpawnArrayEmptyPos() {
      boolean flag = false;

      for(BlockPos pos : this.spawnPositions) {
         if (pos == null) {
            flag = true;
            break;
         }
      }

      return flag;
   }

   private void cleanSpawnArray() {
      for(int i = 0; i < this.spawnPositions.length; ++i) {
         this.spawnPositions[i] = null;
      }

   }
}
