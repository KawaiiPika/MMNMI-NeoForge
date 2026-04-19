package xyz.pixelatedw.mineminenomi.particles.effects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BreakingBlocksParticleEffect extends ParticleEffect<Details> {
   public BreakingBlocksParticleEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      if (details.isVecList) {
         for(Vec3 pos : details.vecPositions) {
            posX = pos.m_7096_();
            posY = pos.m_7098_();
            posZ = pos.m_7094_();

            for(int i = 0; i < details.amount; ++i) {
               double offsetX = world.f_46441_.m_188500_();
               double offsetY = (double)1.0F;
               double offsetZ = world.f_46441_.m_188500_();
               BlockState blockState = world.m_8055_((new BlockPos((int)(posX + offsetX), (int)posY, (int)(posZ + offsetZ))).m_7495_());
               BlockParticleOption particleData = new BlockParticleOption(ParticleTypes.f_123794_, blockState);
               world.m_6493_(particleData, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
            }
         }

      } else {
         for(BlockPos pos : details.positions) {
            posX = (double)pos.m_123341_();
            posY = (double)pos.m_123342_();
            posZ = (double)pos.m_123343_();

            for(int i = 0; i < details.amount; ++i) {
               double offsetX = world.f_46441_.m_188500_();
               double offsetY = (double)1.0F;
               double offsetZ = world.f_46441_.m_188500_();
               BlockState blockState = world.m_8055_((new BlockPos((int)(posX + offsetX), (int)posY, (int)(posZ + offsetZ))).m_7495_());
               BlockParticleOption particleData = new BlockParticleOption(ParticleTypes.f_123794_, blockState);
               world.m_6493_(particleData, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
            }
         }

      }
   }

   public static class Details extends ParticleEffect.Details {
      private int amount = 60;
      private boolean isVecList;
      private List<BlockPos> positions = new ArrayList();
      private List<Vec3> vecPositions = new ArrayList();

      public Details() {
      }

      public Details(int amount) {
         this.amount = amount;
      }

      public void save(CompoundTag nbt) {
         nbt.m_128405_("amount", this.amount);
         nbt.m_128379_("isVec", this.isVecList);
         ListTag positionsList = new ListTag();
         if (this.positions.size() > 0) {
            for(BlockPos pos : this.positions) {
               CompoundTag entry = new CompoundTag();
               entry.m_128405_("x", pos.m_123341_());
               entry.m_128405_("y", pos.m_123342_());
               entry.m_128405_("z", pos.m_123343_());
               positionsList.add(entry);
            }
         }

         nbt.m_128365_("positions", positionsList);
         ListTag vecPositionsList = new ListTag();
         if (this.vecPositions.size() > 0) {
            for(Vec3 pos : this.vecPositions) {
               CompoundTag entry = new CompoundTag();
               entry.m_128347_("x", pos.m_7096_());
               entry.m_128347_("y", pos.m_7098_());
               entry.m_128347_("z", pos.m_7094_());
               vecPositionsList.add(entry);
            }
         }

         nbt.m_128365_("vecPositions", vecPositionsList);
      }

      public void load(CompoundTag nbt) {
         this.amount = nbt.m_128451_("amount");
         this.isVecList = nbt.m_128471_("isVec");
         ListTag positionsList = nbt.m_128437_("positions", 10);
         this.positions.clear();

         for(int i = 0; i < positionsList.size(); ++i) {
            CompoundTag entry = positionsList.m_128728_(i);
            BlockPos pos = new BlockPos(entry.m_128451_("x"), entry.m_128451_("y"), entry.m_128451_("z"));
            this.positions.add(pos);
         }

         ListTag vecPositionsList = nbt.m_128437_("vecPositions", 10);
         this.vecPositions.clear();

         for(int i = 0; i < vecPositionsList.size(); ++i) {
            CompoundTag entry = vecPositionsList.m_128728_(i);
            Vec3 pos = new Vec3(entry.m_128459_("x"), entry.m_128459_("y"), entry.m_128459_("z"));
            this.vecPositions.add(pos);
         }

      }

      public void setParticleAmount(int amount) {
         this.amount = amount;
      }

      public void setPositions(List<BlockPos> positions) {
         this.positions = positions;
      }

      public void setPositions(BlockPos[] positions) {
         this.positions.addAll(Arrays.asList(positions));
      }

      public void setVecPositions(List<Vec3> positions) {
         this.vecPositions = positions;
         this.isVecList = true;
      }

      public void addPosition(BlockPos pos) {
         this.positions.add(pos);
      }

      public void clearPositions() {
         this.positions.clear();
      }
   }
}
