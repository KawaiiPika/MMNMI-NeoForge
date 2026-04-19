package xyz.pixelatedw.mineminenomi.particles.effects.beta;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class NoseParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 10; ++i) {
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.BETA.get());
         data.setLife(20);
         data.setSize(0.6F);
         data.setMotion((double)0.0F, 0.03, (double)0.0F);
         world.m_6493_(data, true, posX, posY + 1.45, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
