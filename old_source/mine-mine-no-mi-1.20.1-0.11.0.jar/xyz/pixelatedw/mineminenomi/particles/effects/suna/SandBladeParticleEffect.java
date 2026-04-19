package xyz.pixelatedw.mineminenomi.particles.effects.suna;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SandBladeParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   private boolean isActive = false;

   public SandBladeParticleEffect(boolean isActive) {
      this.isActive = isActive;
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      int area = 7;

      for(int i = 0; i < 128; ++i) {
         double motionY = (double)0.0F;
         if (this.isActive) {
            motionY = (WyHelper.randomWithRange(1, 10) + world.m_213780_().m_188500_()) * 0.05;
         } else {
            motionY = WyHelper.randomDouble() / (double)30.0F;
            area = 5;
         }

         posX += WyHelper.randomDouble() / (double)area;
         posZ += WyHelper.randomDouble() / (double)area;
         float particleSize = (world.m_213780_().m_188501_() + 2.5F) / 2.0F;
         float color = WyHelper.colorTolerance(0.65F);
         SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.SUNA2.get());
         part.setMotion((double)0.0F, motionY, (double)0.0F);
         part.setColor(color, color, color);
         part.setLife(20);
         part.setRotation(0.0F, 0.0F, 1.0F);
         part.setSize(particleSize);
         world.m_6493_(part, true, posX, posY + 0.2, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
