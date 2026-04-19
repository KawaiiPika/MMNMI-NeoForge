package xyz.pixelatedw.mineminenomi.particles.effects.blackleg;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class PartyTableKickCourseParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double t = (double)0.0F;
      RandomSource rand = world.f_46441_;
      double size = (double)1.0F;

      while(t < (double)1.0F) {
         ++t;

         for(double theta = (double)0.0F; theta <= 12.566370614359172; theta += 0.19634954084936207) {
            double x = t * Math.cos(theta);
            double z = t * Math.sin(theta);
            double motionX = Math.sin(theta) / (double)10.0F;
            double motionY = 0.01 + rand.m_188500_() / (double)10.0F;
            double motionZ = -Math.cos(theta) / (double)10.0F;
            SimpleParticleData data;
            if (rand.m_188503_(10) % 2 == 0) {
               data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
            } else {
               data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA2.get());
            }

            data.setLife((int)WyHelper.randomWithRange(5, 10));
            data.setSize((float)WyHelper.randomWithRange(1, 3));
            data.setColor(1.0F, 1.0F, 1.0F, 0.5F);
            data.setMotion(motionX, motionY, motionZ);
            world.m_6493_(data, true, posX + x * size + WyHelper.randomDouble() / (double)2.0F, posY + 1.4, posZ + z * size + WyHelper.randomDouble() / (double)2.0F, (double)0.0F, (double)0.0F, (double)0.0F);
            world.m_6493_(data, true, posX + x * (size / (double)2.0F) + WyHelper.randomDouble() / (double)2.0F, posY + 1.4, posZ + z * (size / (double)2.0F) + WyHelper.randomDouble() / (double)2.0F, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
