package xyz.pixelatedw.mineminenomi.particles.effects.doctor;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FirstAidParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 45; ++i) {
         double motionX = WyHelper.randomWithRange(-5, 5) + WyHelper.randomDouble();
         double motionY = WyHelper.randomWithRange(1, 2);
         double motionZ = WyHelper.randomWithRange(-5, 5) + WyHelper.randomDouble();
         double middlePoint = 0.1;
         middlePoint *= (double)(world.f_46441_.m_188501_() * 2.0F + 0.3F);
         motionX *= middlePoint / (double)50.0F;
         motionY *= middlePoint / (double)2.0F;
         motionZ *= middlePoint / (double)50.0F;
         double offsetX = WyHelper.randomDouble() / 1.2;
         double offsetZ = WyHelper.randomDouble() / 1.2;
         SimpleParticleData data;
         if (i % 2 == 0) {
            data = new SimpleParticleData((ParticleType)ModParticleTypes.CHIYU.get());
         } else {
            data = new SimpleParticleData((ParticleType)ModParticleTypes.PIKA.get());
         }

         data.setLife(10);
         data.setSize(1.5F);
         data.setColor(0.0F, 0.8F, 0.0F);
         data.setMotion(motionX, motionY, motionZ);
         world.m_6493_(data, true, posX + offsetX, posY + (double)0.5F, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
