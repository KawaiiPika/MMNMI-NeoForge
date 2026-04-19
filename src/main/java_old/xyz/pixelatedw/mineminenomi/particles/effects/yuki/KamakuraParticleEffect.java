package xyz.pixelatedw.mineminenomi.particles.effects.yuki;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KamakuraParticleEffect extends ParticleEffect<Details> {
   public KamakuraParticleEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      int size = details.getSize();

      for(int i = 0; i < 60; ++i) {
         double offsetX = (double)2.0F * WyHelper.randomWithRange(-size, size) + WyHelper.randomDouble();
         double offsetY = (double)2.0F * WyHelper.randomWithRange(0, size) + WyHelper.randomDouble();
         double offsetZ = (double)2.0F * WyHelper.randomWithRange(-size, size) + WyHelper.randomDouble();
         double motionX = WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble();
         double motionZ = WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble();
         double middlePoint = 1.2;
         motionX *= middlePoint / (double)25.0F;
         motionZ *= middlePoint / (double)25.0F;
         float scale = (float)((double)1.0F + WyHelper.randomWithRange(5, 7));
         ParticleType<SimpleParticleData> particle = (ParticleType)ModParticleTypes.YUKI.get();
         if (i % 5 == 0) {
            particle = (ParticleType)ModParticleTypes.YUKI3.get();
         } else if (i % 2 == 0) {
            particle = (ParticleType)ModParticleTypes.YUKI2.get();
         } else {
            particle = (ParticleType)ModParticleTypes.YUKI.get();
         }

         float rotation = world.m_213780_().m_188501_() / 4.0F;
         SimpleParticleData part = new SimpleParticleData(particle);
         part.setLife(100);
         part.setSize(scale);
         part.setMotion(motionX, -0.05, motionZ);
         part.setRotation(0.0F, 0.0F, 1.0F);
         part.setRotationSpeed(rotation);
         part.setHasMotionDecay(false);
         world.m_6493_(part, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
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
