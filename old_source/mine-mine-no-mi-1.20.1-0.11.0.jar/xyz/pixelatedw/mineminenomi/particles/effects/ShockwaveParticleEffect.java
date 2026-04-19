package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class ShockwaveParticleEffect extends ParticleEffect<Details> {
   public ShockwaveParticleEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      int sizeOffset = details.getSize();

      for(int x = -sizeOffset; x < sizeOffset; ++x) {
         for(int z = -sizeOffset; z < sizeOffset; ++z) {
            for(int i = 0; i < 60; ++i) {
               double y = WyHelper.randomDouble() / (double)2.0F;
               BlockState blockState = world.m_8055_((new BlockPos((int)(posX + (double)x), (int)posY, (int)(posZ + (double)z))).m_7495_());
               BlockParticleOption particleData = new BlockParticleOption(ParticleTypes.f_123794_, blockState);
               world.m_6493_(particleData, true, posX + WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble() / (double)20.0F, posY + y + (double)0.5F, posZ + WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble() / (double)20.0F, (double)0.0F, (double)0.0F, (double)0.0F);
            }
         }
      }

   }

   public static class Details extends ParticleEffect.Details {
      private int size;

      public Details() {
      }

      public Details(int size) {
         this.size = size;
      }

      public void save(CompoundTag nbt) {
         nbt.m_128405_("size", this.size);
      }

      public void load(CompoundTag nbt) {
         this.size = nbt.m_128451_("size");
      }

      public int getSize() {
         return this.size;
      }
   }
}
