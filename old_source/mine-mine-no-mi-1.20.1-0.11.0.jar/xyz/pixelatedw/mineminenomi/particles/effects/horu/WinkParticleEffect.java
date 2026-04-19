package xyz.pixelatedw.mineminenomi.particles.effects.horu;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WinkParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double motionX = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
      double motionY = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
      double motionZ = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
      double middlePoint = 0.1;
      middlePoint *= WyHelper.randomDouble() * (double)2.0F + (double)0.25F;
      motionX *= middlePoint / (double)5.0F;
      motionY *= middlePoint / (double)5.0F;
      motionZ *= middlePoint / (double)5.0F;
      SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.HORU.get());
      data.setLife(5);
      data.setSize(5.0F);
      data.setMotion(motionX, motionY, motionZ);
      world.m_7106_(data, posX, posY + (double)1.5F, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
   }
}
