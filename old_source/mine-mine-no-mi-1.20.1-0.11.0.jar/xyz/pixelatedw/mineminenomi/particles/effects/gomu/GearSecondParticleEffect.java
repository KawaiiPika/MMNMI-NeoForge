package xyz.pixelatedw.mineminenomi.particles.effects.gomu;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GearSecondParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 8; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)1.25F;
         double offsetY = WyHelper.randomDouble() / (double)1.25F;
         double offsetZ = WyHelper.randomDouble() / (double)1.25F;
         world.m_6493_(ParticleTypes.f_123759_, true, posX + offsetX, posY + (double)1.0F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
