package xyz.pixelatedw.mineminenomi.particles.effects.ito;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KumoNoSugakiParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.ITO.get());
      data.setLife(2);
      data.setSize(30.0F);
      data.setHasScaleDecay(false);
      world.m_6493_(data, true, posX, posY + 1.2, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
   }
}
