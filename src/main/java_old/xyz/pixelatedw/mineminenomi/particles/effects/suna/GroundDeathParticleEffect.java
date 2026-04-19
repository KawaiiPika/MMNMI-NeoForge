package xyz.pixelatedw.mineminenomi.particles.effects.suna;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GroundDeathParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double t = (double)0.0F;

      while(t < (double)1.0F) {
         t += (Math.PI / 10D);

         for(double theta = (double)0.0F; theta <= 12.566370614359172; theta += 0.19634954084936207) {
            double x = t * Math.cos(theta);
            double y = WyHelper.randomDouble();
            double z = t * Math.sin(theta);
            double motionX = WyHelper.randomDouble() / (double)3.0F;
            double motionY = (double)0.0F;
            double motionZ = WyHelper.randomDouble() / (double)3.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.SUNA2.get());
            data.setLife(10);
            data.setSize(2.5F);
            data.setMotion(motionX, motionY, motionZ);
            entity.m_9236_().m_6493_(data, true, posX + x * (double)2.25F, posY + (double)0.5F + y, posZ + z * (double)2.25F, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
