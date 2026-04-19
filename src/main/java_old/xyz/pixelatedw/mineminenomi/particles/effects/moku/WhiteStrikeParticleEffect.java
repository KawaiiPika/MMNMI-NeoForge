package xyz.pixelatedw.mineminenomi.particles.effects.moku;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WhiteStrikeParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double t = (double)0.0F;
      RandomSource rand = world.f_46441_;

      while(t < (double)1.0F) {
         ++t;

         for(double theta = (double)0.0F; theta <= 12.566370614359172; theta += 0.19634954084936207) {
            double x = t * Math.cos(theta);
            double y = (double)rand.m_188503_(1);
            double z = t * Math.sin(theta);
            double motionX = x / (double)7.0F;
            double motionY = 0.02 + rand.m_188500_() / (double)10.0F;
            double motionZ = z / (double)7.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU.get());
            data.setLife(3);
            data.setSize(1.3F);
            data.setMotion(motionX, motionY, motionZ);
            world.m_6493_(data, true, posX + x * 1.65 + WyHelper.randomDouble(), posY + y, posZ + z * 1.65 + WyHelper.randomDouble(), (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
