package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;

public class SwimUpOnDrowningGoal extends TickedGoal<PathfinderMob> {
   private static final int MAX_AIR_ATTEMPTS = 20;
   private static final int NEXT_AIR_ATTEMPT_COOLDOWN = 300;
   private boolean isFishman;
   private boolean isDrowining;
   private BlockPos surfacePosition;
   private int airAttempts = 0;
   private long lastAirAttempt = 0L;

   public SwimUpOnDrowningGoal(PathfinderMob entity) {
      super(entity);
      this.isFishman = (Boolean)EntityStatsCapability.get(entity).map((props) -> props.isFishman()).orElse(false);
   }

   public boolean m_8036_() {
      if (this.isFishman) {
         return false;
      } else if (!((PathfinderMob)this.entity).isEyeInFluidType((FluidType)ForgeMod.WATER_TYPE.get())) {
         return false;
      } else {
         return ((PathfinderMob)this.entity).m_20146_() < ((PathfinderMob)this.entity).m_6062_() / 2;
      }
   }

   public boolean m_8045_() {
      if (!((PathfinderMob)this.entity).isEyeInFluidType((FluidType)ForgeMod.WATER_TYPE.get())) {
         return false;
      } else {
         return ((PathfinderMob)this.entity).m_20146_() < ((PathfinderMob)this.entity).m_6062_() / 2;
      }
   }

   public void m_8056_() {
      super.m_8056_();
      this.isDrowining = true;
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();
      mutpos.m_122190_(((PathfinderMob)this.entity).m_20183_());
      if (((PathfinderMob)this.entity).m_9236_().m_45527_(((PathfinderMob)this.entity).m_20183_())) {
         for(int i = 0; i < 20; ++i) {
            FluidState fluidState = ((PathfinderMob)this.entity).m_9236_().m_6425_(mutpos.m_6630_(i));
            if (fluidState.m_76178_()) {
               this.surfacePosition = mutpos.m_7949_();
               break;
            }
         }
      } else {
         this.tryFindingAir();
      }

   }

   public void m_8037_() {
      super.m_8037_();
      if (this.surfacePosition != null) {
         ((PathfinderMob)this.entity).m_21573_().m_26573_();
         ((PathfinderMob)this.entity).m_21573_().m_26519_((double)this.surfacePosition.m_123341_(), (double)this.surfacePosition.m_123342_(), (double)this.surfacePosition.m_123343_(), (double)1.55F);
      } else if (((PathfinderMob)this.entity).m_9236_().m_46467_() >= this.lastAirAttempt + 300L) {
         this.tryFindingAir();
      }

   }

   public void m_8041_() {
      super.m_8041_();
      this.isDrowining = false;
      this.surfacePosition = null;
      this.airAttempts = 0;
   }

   private void tryFindingAir() {
      while(true) {
         if (this.airAttempts < 20) {
            this.surfacePosition = this.lookForAir(((PathfinderMob)this.entity).m_9236_(), 40, 40);
            if (this.surfacePosition == null) {
               ++this.airAttempts;
               continue;
            }
         }

         this.lastAirAttempt = ((PathfinderMob)this.entity).m_9236_().m_46467_();
         return;
      }
   }

   @Nullable
   protected BlockPos lookForAir(LevelAccessor level, int horizontalRange, int verticalRange) {
      BlockPos blockpos = ((PathfinderMob)this.entity).m_20183_();
      int x0 = blockpos.m_123341_();
      int y0 = blockpos.m_123342_();
      int z0 = blockpos.m_123343_();
      float f = (float)(horizontalRange * horizontalRange * verticalRange * 2);
      BlockPos targetPosition = null;
      BlockPos.MutableBlockPos mutpos = new BlockPos.MutableBlockPos();

      for(int x = x0 - horizontalRange; x <= x0 + horizontalRange; ++x) {
         for(int y = y0 - verticalRange; y <= y0 + verticalRange; ++y) {
            for(int z = z0 - horizontalRange; z <= z0 + horizontalRange; ++z) {
               mutpos.m_122178_(x, y, z);
               if (level.m_6425_(mutpos).m_76178_()) {
                  float dist = (float)((x - x0) * (x - x0) + (y - y0) * (y - y0) + (z - z0) * (z - z0));
                  if (dist < f) {
                     f = dist;
                     targetPosition = mutpos.m_7949_();
                  }
               }
            }
         }
      }

      return targetPosition;
   }
}
