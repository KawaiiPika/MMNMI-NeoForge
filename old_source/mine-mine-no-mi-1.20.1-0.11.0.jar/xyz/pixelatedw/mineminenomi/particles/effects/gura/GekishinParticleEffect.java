package xyz.pixelatedw.mineminenomi.particles.effects.gura;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GekishinParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double motionX, double motionY, double motionZ, ParticleEffect.NoDetails details) {
      SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.GURA.get());
      data.setLife(20);
      data.setSize(50.0F);
      world.m_6493_(data, true, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), (double)0.0F, (double)0.0F, (double)0.0F);
   }
}
