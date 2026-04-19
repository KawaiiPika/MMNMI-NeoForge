package xyz.pixelatedw.mineminenomi.particles.effects.mero;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SlaveArrowParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      SimpleParticleData particle = new SimpleParticleData((ParticleType)ModParticleTypes.MERO.get());
      particle.setLife(60);
      particle.setSize(20.0F);
      particle.setColor(1.0F, 1.0F, 1.0F, 0.4F);
      particle.setHasScaleDecay(false);
      particle.setFunction(EasingFunction.ELASTIC_IN);
      world.m_6493_(particle, true, posX, posY + (double)1.0F, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
   }
}
