package xyz.pixelatedw.mineminenomi.particles.effects.gasu;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GastanetParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 256; ++i) {
         double offsetX = WyHelper.randomDouble();
         double offsetY = WyHelper.randomDouble();
         double offsetZ = WyHelper.randomDouble();
         double motionX = WyHelper.randomWithRange(-5, 5) + WyHelper.randomDouble();
         double motionY = WyHelper.randomWithRange(0, 5) + WyHelper.randomDouble();
         double motionZ = WyHelper.randomWithRange(-5, 5) + WyHelper.randomDouble();
         double middlePoint = 2.8;
         motionX *= middlePoint / (double)15.0F;
         motionY *= middlePoint / (double)25.0F;
         motionZ *= middlePoint / (double)15.0F;
         motionY = Math.abs(motionY);
         SimpleParticleData data;
         if (i % 4 == 0) {
            data = new SimpleParticleData((ParticleType)ModParticleTypes.GASU.get());
         } else {
            data = new SimpleParticleData((ParticleType)ModParticleTypes.GASU2.get());
         }

         data.setLife(7);
         data.setSize(1.5F);
         data.setMotion(motionX, motionY, motionZ);
         world.m_6493_(data, true, posX + offsetX, posY + (double)0.25F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
