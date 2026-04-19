package xyz.pixelatedw.mineminenomi.particles.effects.fishkarate;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SamehadaParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      ++posY;
      double radius = (double)1.0F;
      double phi = (double)0.0F;

      while(phi < Math.PI) {
         phi += (Math.PI / 4D);

         for(double theta = (double)0.0F; theta <= (Math.PI * 2D); theta += (Math.PI / 6D)) {
            double x = radius * Math.cos(theta) * Math.sin(phi);
            double y = radius * Math.cos(phi);
            double z = radius * Math.sin(theta) * Math.sin(phi);
            world.m_6493_(ParticleTypes.f_123769_, true, posX + x, posY + y, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
