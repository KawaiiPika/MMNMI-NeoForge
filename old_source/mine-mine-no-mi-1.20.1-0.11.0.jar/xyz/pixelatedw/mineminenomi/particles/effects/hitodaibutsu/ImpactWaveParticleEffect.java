package xyz.pixelatedw.mineminenomi.particles.effects.hitodaibutsu;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ImpactWaveParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double radius = (double)1.0F;

      for(int i = 0; i < 10; ++i) {
         double phi = (double)0.0F;

         while(phi < Math.PI) {
            phi += 0.19634954084936207;

            for(double theta = (double)0.0F; theta <= (Math.PI * 2D); theta += 0.19634954084936207) {
               double x = radius * Math.cos(theta) * Math.sin(phi);
               double y = radius * Math.cos(phi);
               double z = radius * Math.sin(theta) * Math.sin(phi);
               SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.SUNA2.get());
               data.setLife(50);
               double motionX = WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble();
               double motionZ = WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble();
               double middlePoint = 1.2;
               motionX *= middlePoint / (double)25.0F;
               motionZ *= middlePoint / (double)25.0F;
               data.setMotion(motionX, (double)0.1F, motionZ);
               data.setSize(10.0F);
               data.setColor(1.0F, 1.0F, 0.2F, 0.5F);
               world.m_6493_(data, true, posX + x, posY + y, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
            }
         }

         radius += 0.8;
      }

   }
}
