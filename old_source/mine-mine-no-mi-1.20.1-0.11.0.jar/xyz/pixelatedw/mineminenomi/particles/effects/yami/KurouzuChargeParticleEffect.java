package xyz.pixelatedw.mineminenomi.particles.effects.yami;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KurouzuChargeParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.KUROUZU.get());
      data.setColor(1.0F, 1.0F, 1.0F, 0.25F);
      data.setLife(1);
      data.setSize(10.0F);
      data.setHasScaleDecay(false);
      data.setRotation(0.0F, 0.0F, 1.0F);
      world.m_6493_(data, true, posX, posY, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
   }
}
