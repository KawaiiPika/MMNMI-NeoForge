package xyz.pixelatedw.mineminenomi.particles.effects.swordsman;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HiryuKaenParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double t = (double)0.0F;
      RandomSource rand = world.f_46441_;

      while(t < (double)1.0F) {
         ++t;

         for(double theta = (double)0.0F; theta <= 12.566370614359172; theta += 0.09817477042468103) {
            double x = t * Math.cos(theta);
            double z = t * Math.sin(theta);
            double motionX = x / (double)8.0F;
            double motionY = 0.01 + rand.m_188500_() / (double)4.0F;
            double motionZ = z / (double)8.0F;
            SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
            part.setLife(7);
            part.setAnimationSpeed(1);
            part.setSize(2.0F);
            part.setColor(1.0F, 1.0F, 1.0F, 0.5F);
            part.setMotion(motionX, motionY, motionZ);
            world.m_6493_(part, true, posX + x * 0.85, posY + 1.2, posZ + z * 0.85, (double)0.0F, (double)0.0F, (double)0.0F);
            world.m_6493_(part, true, posX + x * 0.45, posY + 1.8, posZ + z * 0.45, (double)0.0F, (double)0.0F, (double)0.0F);
            world.m_6493_(part, true, posX + x * 0.85, posY + 2.2, posZ + z * 0.85, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
