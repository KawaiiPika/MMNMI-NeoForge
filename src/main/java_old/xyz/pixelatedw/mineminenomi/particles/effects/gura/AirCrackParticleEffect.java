package xyz.pixelatedw.mineminenomi.particles.effects.gura;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class AirCrackParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.GURA.get());
      data.setLife(20);
      data.setSize(16.0F);
      data.setColor(1.0F, 1.0F, 1.0F, 0.7F);
      world.m_6493_(data, true, posX + xyz.pixelatedw.mineminenomi.api.WyHelper.random() / (double)2.0F, posY + xyz.pixelatedw.mineminenomi.api.WyHelper.random() / (double)2.0F, posZ + xyz.pixelatedw.mineminenomi.api.WyHelper.random() / (double)2.0F, (double)0.0F, (double)0.0F, (double)0.0F);
   }
}
