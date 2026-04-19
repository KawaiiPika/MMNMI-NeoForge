package xyz.pixelatedw.mineminenomi.particles.effects.electro;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElectricalTempesta2ParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double t = (double)0.0F;
      RandomSource rand = world.f_46441_;

      for(double size = 0.1; t < (double)5.0F; size += 0.2) {
         t += size * Math.PI;

         for(double theta = (double)0.0F; theta <= 12.566370614359172; theta += 0.19634954084936207) {
            double x = t * Math.cos(theta);
            double z = t * Math.sin(theta);
            double motionX = x / (double)5.0F;
            double motionY = 0.05 + rand.m_188500_() / (double)15.0F;
            double motionZ = z / (double)5.0F;
            SimpleParticleData data;
            if (rand.m_188503_(10) % 2 == 0) {
               data = new SimpleParticleData((ParticleType)ModParticleTypes.GORO.get());
            } else {
               data = new SimpleParticleData((ParticleType)ModParticleTypes.GORO2.get());
            }

            data.setLife(0);
            data.setSize((float)WyHelper.randomWithRange(3, 5));
            data.setColor(1.0F, 1.0F, 1.0F, 0.8F);
            data.setMotion(motionX, motionY, motionZ);
            world.m_6493_(data, true, posX + x + WyHelper.randomDouble() / (double)2.0F, posY + (double)0.25F, posZ + z + WyHelper.randomDouble() / (double)2.0F, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
