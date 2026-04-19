package xyz.pixelatedw.mineminenomi.particles.effects.yami;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DarkMatterParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double t = (double)0.0F;
      RandomSource rand = world.f_46441_;

      while(t < (double)1.0F) {
         ++t;

         for(double theta = (double)0.0F; theta <= 12.566370614359172; theta += 0.09817477042468103) {
            double x = t * Math.cos(theta);
            double z = t * Math.sin(theta);
            double motionX = -x / (double)3.0F;
            double motionY = -0.1 + rand.m_188500_() / (double)10.0F;
            double motionZ = -z / (double)3.0F;
            SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.DARKNESS.get());
            part.setLife(14);
            part.setAnimationSpeed(2);
            part.setRotation(0.0F, 0.0F, 1.0F);
            part.setRotationSpeed(t % (double)2.0F == (double)0.0F ? 0.07F : -0.07F);
            part.setSize(3.3F);
            part.setMotion(motionX, motionY, motionZ);
            world.m_6493_(part, true, posX + x * (double)5.25F, posY + 1.2, posZ + z * (double)5.25F, (double)0.0F, (double)0.0F, (double)0.0F);
            part.setLife(14);
            part.setAnimationSpeed(2);
            part.setRotation(0.0F, 0.0F, 1.0F);
            part.setRotationSpeed(t % (double)2.0F == (double)0.0F ? 0.07F : -0.07F);
            part.setSize(3.3F);
            part.setMotion(motionX, motionY, motionZ);
            world.m_6493_(part, true, posX + x * (double)5.25F, posY + 3.2, posZ + z * (double)5.25F, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
