package xyz.pixelatedw.mineminenomi.particles.effects.blackleg;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ConcasseDiableParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double radius = (double)0.5F;
      double phi = (double)0.0F;

      while(phi < Math.PI) {
         ++phi;

         for(double theta = (double)0.0F; theta <= 12.566370614359172; ++theta) {
            double x = radius * Math.cos(theta) * Math.sin(phi) + WyHelper.randomDouble() / (double)2.0F;
            double y = radius * Math.cos(phi) + WyHelper.randomDouble() / (double)2.0F;
            double z = radius * Math.sin(theta) * Math.sin(phi) + WyHelper.randomDouble() / (double)2.0F;
            SimpleParticleData data;
            if (world.f_46441_.m_188503_(10) % 2 == 0) {
               data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
            } else {
               data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA2.get());
            }

            data.setLife((int)WyHelper.randomWithRange(5, 10));
            data.setSize((float)WyHelper.randomWithRange(1, 3));
            data.setColor(1.0F, 1.0F, 1.0F, 0.5F);
            world.m_7106_(data, posX + x, posY + y, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
