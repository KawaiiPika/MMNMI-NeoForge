package xyz.pixelatedw.mineminenomi.particles.effects.sniper;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KemuriBoshiParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 512; ++i) {
         double offsetX = WyHelper.randomWithRange(-4, 4) + WyHelper.randomDouble();
         double offsetY = WyHelper.randomWithRange(-2, 3) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-4, 4) + WyHelper.randomDouble();
         if (i % 2 == 0) {
            world.m_6493_(ParticleTypes.f_123796_, true, posX + offsetX + WyHelper.randomWithRange(-7, 7), posY + (double)0.5F + offsetY + WyHelper.randomWithRange(-1, 3), posZ + offsetZ + WyHelper.randomWithRange(-7, 7), (double)0.0F, (double)0.0F, (double)0.0F);
         } else {
            world.m_6493_(ParticleTypes.f_123796_, true, posX + offsetX + WyHelper.randomWithRange(-7, 7), posY + (double)0.5F + offsetY + WyHelper.randomWithRange(-1, 3), posZ + offsetZ + WyHelper.randomWithRange(-7, 7), (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
