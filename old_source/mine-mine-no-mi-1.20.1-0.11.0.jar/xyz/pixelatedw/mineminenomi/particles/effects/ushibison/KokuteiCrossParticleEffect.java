package xyz.pixelatedw.mineminenomi.particles.effects.ushibison;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KokuteiCrossParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double t = (double)0.0F;
      RandomSource rand = world.f_46441_;

      while(t < (double)1.0F) {
         ++t;

         for(double theta = (double)0.0F; theta <= 12.566370614359172; theta += 0.09817477042468103) {
            double x = t * Math.cos(theta);
            double z = t * Math.sin(theta);
            double offsetX = x / (double)6.0F;
            double offsetY = -0.1 + rand.m_188500_() / (double)10.0F;
            double offsetZ = z / (double)6.0F;
            world.m_6493_(ParticleTypes.f_123796_, true, posX + x * 1.85 + offsetX, posY + 1.2 + offsetY, posZ + z * 1.85 + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
            world.m_6493_(ParticleTypes.f_123796_, true, posX + x * 1.85 + offsetX, posY + 2.2 + offsetY, posZ + z * 1.85 + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
