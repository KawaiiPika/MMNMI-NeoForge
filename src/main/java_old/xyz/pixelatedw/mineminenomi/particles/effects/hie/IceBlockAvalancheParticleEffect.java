package xyz.pixelatedw.mineminenomi.particles.effects.hie;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class IceBlockAvalancheParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double phi = (double)0.0F;

      while(phi < Math.PI) {
         phi += (Math.PI / 4D);

         for(double theta = (double)0.0F; theta <= (Math.PI * 2D); theta += (Math.PI / 6D)) {
            double x = (double)8.0F * Math.cos(theta) * Math.sin(phi) + WyHelper.randomDouble();
            double y = posY - (double)3.0F - (double)1.0F;
            double z = (double)8.0F * Math.sin(theta) * Math.sin(phi) + WyHelper.randomDouble();
            double motionX = x / (double)40.0F;
            double motionY = -0.2;
            double motionZ = z / (double)40.0F;
            SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.HIE.get());
            part.setLife(14);
            part.setAnimationSpeed(2);
            part.setRotation(0.0F, 0.0F, 1.0F);
            part.setRotationSpeed(theta % (double)2.0F == (double)0.0F ? 0.07F : -0.07F);
            part.setSize(10.0F);
            part.setMotion(-motionX, motionY, -motionZ);
            if (Math.random() > 0.7) {
               world.m_6493_(part, true, posX + x + WyHelper.randomDouble(), y, posZ + z + WyHelper.randomDouble(), (double)0.0F, (double)0.0F, (double)0.0F);
            }
         }
      }

   }
}
