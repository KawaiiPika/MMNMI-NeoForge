package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class WaterExplosionParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 12; ++i) {
         double motionX = WyHelper.randomWithRange(-3, 3) + WyHelper.randomDouble();
         double motionY = WyHelper.randomWithRange(-3, 3) + WyHelper.randomDouble();
         double motionZ = WyHelper.randomWithRange(-3, 3) + WyHelper.randomDouble();
         double middlePoint = (double)0.25F;
         middlePoint *= WyHelper.randomDouble() * (double)2.0F + (double)0.3F;
         motionX *= middlePoint / (double)2.0F;
         motionY *= middlePoint / (double)2.0F;
         motionZ *= middlePoint / (double)2.0F;
         world.m_7106_(ParticleTypes.f_123816_, posX, posY, posZ, motionX, motionY, motionZ);
         world.m_7106_(ParticleTypes.f_123816_, posX, posY, posZ, motionX, motionY, motionZ);
      }

   }
}
