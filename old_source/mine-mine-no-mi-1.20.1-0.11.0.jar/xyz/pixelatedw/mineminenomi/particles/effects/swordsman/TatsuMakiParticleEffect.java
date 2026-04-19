package xyz.pixelatedw.mineminenomi.particles.effects.swordsman;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class TatsuMakiParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double t = (double)0.0F;

      while(t < (double)3.0F) {
         ++t;

         for(double theta = (double)0.0F; theta <= 12.566370614359172; theta += 0.19634954084936207) {
            double x = t * Math.cos(theta);
            double z = t * Math.sin(theta);
            world.m_6493_(ParticleTypes.f_123766_, true, posX + x * 1.85, posY + 1.2 + WyHelper.randomDouble(), posZ + z * 1.85, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
