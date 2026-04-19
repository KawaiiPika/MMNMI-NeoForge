package xyz.pixelatedw.mineminenomi.particles.effects.artofweather;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WeatherCloudChargedParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      int i = 0;
      double phi = (double)0.0F;
      double radius = (double)15.0F;

      while(phi < Math.PI) {
         phi += 2.0943951023931953;

         for(double theta = (double)0.0F; theta <= (Math.PI * 2D); theta += (Math.PI / 4D)) {
            double x = radius * Math.cos(theta) * Math.sin(phi) + WyHelper.randomDouble() * radius;
            double y = radius * Math.cos(phi);
            double z = radius * Math.sin(theta) * Math.sin(phi) + WyHelper.randomDouble() * radius;
            ParticleType<SimpleParticleData> particle;
            if (i % 4 == 0) {
               particle = (ParticleType)ModParticleTypes.GORO_YELLOW.get();
            } else {
               particle = (ParticleType)ModParticleTypes.GORO2_YELLOW.get();
            }

            SimpleParticleData part = new SimpleParticleData(particle);
            part.setLife((int)((double)20.0F + WyHelper.randomDouble()));
            part.setSize(10.0F);
            part.setHasScaleDecay(true);
            world.m_6493_(part, true, posX + x, posY + (double)5.0F + y, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
            ++i;
         }
      }

   }
}
