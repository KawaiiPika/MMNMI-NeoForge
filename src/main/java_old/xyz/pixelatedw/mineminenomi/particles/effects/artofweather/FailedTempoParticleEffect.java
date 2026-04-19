package xyz.pixelatedw.mineminenomi.particles.effects.artofweather;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FailedTempoParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 10; ++i) {
         double offsetX = WyHelper.randomDouble();
         double offsetY = WyHelper.randomDouble();
         double offsetZ = WyHelper.randomDouble();
         if (i % 2 == 0) {
            world.m_6493_(ParticleTypes.f_123759_, true, posX + offsetX, posY + (double)1.0F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
         } else {
            world.m_6493_(ParticleTypes.f_123762_, true, posX + offsetX, posY + (double)1.0F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
