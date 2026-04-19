package xyz.pixelatedw.mineminenomi.particles.effects.fishkarate;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KarakusagawaraSeikenChargeParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      int i = 0;
      double t = (double)0.0F;
      RandomSource rand = world.f_46441_;

      while(t < (double)1.0F) {
         ++t;

         for(double theta = (double)0.0F; theta <= 12.566370614359172; theta += 0.19634954084936207) {
            double x = t * Math.cos(theta);
            double y = (double)rand.m_188503_(1);
            double z = t * Math.sin(theta);
            double motionX = -x / (double)20.0F;
            double motionY = 0.05 + rand.m_188500_() / (double)10.0F;
            double motionZ = -z / (double)20.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.AWA.get());
            if (i % 2 == 0) {
               data = new SimpleParticleData((ParticleType)ModParticleTypes.AWA.get());
            }

            double offsetX = world.f_46441_.m_188500_() * WyHelper.randomWithRange(7, 9);
            double offsetY = (double)1.0F;
            double offsetZ = world.f_46441_.m_188500_() * WyHelper.randomWithRange(7, 9);
            world.m_6493_(ParticleTypes.f_123803_, true, posX - (double)4.0F + offsetX, posY - (double)1.0F + offsetY, posZ - (double)4.0F + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
            data.setLife(1);
            data.setSize(1.0F);
            data.setMotion(motionX, motionY, motionZ);
            data.setColor(0.0F, 0.0F, 1.0F, 0.8F);
            world.m_6493_(data, true, posX + x * (double)1.25F + WyHelper.randomDouble() * (double)2.0F, posY + y, posZ + z * (double)1.25F + WyHelper.randomDouble() * (double)2.0F, (double)0.0F, (double)0.0F, (double)0.0F);
            ++i;
         }
      }

   }
}
