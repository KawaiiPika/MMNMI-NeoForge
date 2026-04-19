package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class GroundParticlesEffect extends ParticleEffect<Details> {
   public GroundParticlesEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      for(int i = 0; i < details.amount; ++i) {
         double offsetX = WyHelper.randomWithRange(-details.offset, details.offset) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-details.offset, details.offset) + WyHelper.randomDouble();

         for(int j = 0; j < 2; ++j) {
            BlockState blockState = world.m_8055_((new BlockPos((int)(posX + offsetX), (int)(posY - (double)j), (int)(posZ + offsetZ))).m_7495_());
            world.m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, blockState), posX + offsetX, posY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }

   public static class Details extends ParticleEffect.Details {
      private int offset = 1;
      private int amount = 10;

      public Details() {
      }

      public Details(int offset, int amount) {
         this.offset = offset;
         this.amount = amount;
      }

      public void save(CompoundTag nbt) {
         nbt.m_128405_("offset", this.offset);
         nbt.m_128405_("amount", this.amount);
      }

      public void load(CompoundTag nbt) {
         this.offset = nbt.m_128451_("offset");
         this.amount = nbt.m_128451_("amount");
      }
   }
}
