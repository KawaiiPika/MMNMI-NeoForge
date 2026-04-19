package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class GuardParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 25; ++i) {
         double x = posX + WyHelper.randomDouble() / 1.2;
         double y = posY + WyHelper.randomDouble() / 1.2;
         double z = posZ + WyHelper.randomDouble() / 1.2;
         world.m_7106_(ParticleTypes.f_123797_, x, y + (double)1.0F, z, (double)0.0F, (double)0.0F, (double)0.0F);
         world.m_7106_(ParticleTypes.f_123808_, x, y + (double)1.0F, z, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
