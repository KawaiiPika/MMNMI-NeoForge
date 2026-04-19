package xyz.pixelatedw.mineminenomi.particles.effects.artofweather;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WeatherCloudParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
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
            float red;
            float green;
            float blue;
            ParticleType<SimpleParticleData> particle;
            if (i % 2 == 0) {
               red = 0.5F;
               green = 0.5F;
               blue = 0.5F;
               particle = (ParticleType)ModParticleTypes.MOKU2.get();
            } else {
               red = 0.3F;
               green = 0.3F;
               blue = 0.3F;
               particle = (ParticleType)ModParticleTypes.MOKU.get();
            }

            SimpleParticleData part = new SimpleParticleData(particle);
            part.setLife((int)((double)50.0F + WyHelper.randomDouble()));
            part.setSize(45.0F);
            part.setColor(red, green, blue);
            part.setHasScaleDecay(true);
            world.m_6493_(part, true, posX + x, posY + (double)7.0F + y, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
            ++i;
         }
      }

   }
}
