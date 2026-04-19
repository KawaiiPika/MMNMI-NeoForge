package xyz.pixelatedw.mineminenomi.particles.effects.mera;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KyokaenParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 40; ++i) {
         double offsetX = WyHelper.randomDouble() / 1.2;
         double offsetY = WyHelper.randomDouble();
         double offsetZ = WyHelper.randomDouble() / 1.2;
         SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
         part.setLife(5);
         part.setSize(1.0F);
         part.setColor(1.0F, 1.0F, 1.0F, 0.5F);
         part.setMotion((double)0.0F, 0.05, (double)0.0F);
         part.setHasScaleDecay(true);
         world.m_6493_(part, true, posX + offsetX, posY + (double)1.0F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
