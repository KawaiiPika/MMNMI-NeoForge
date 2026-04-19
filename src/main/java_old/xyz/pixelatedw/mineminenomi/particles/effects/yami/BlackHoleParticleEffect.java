package xyz.pixelatedw.mineminenomi.particles.effects.yami;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BlackHoleParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double motionX = WyHelper.randomDouble() / (double)5.0F;
      double motionY = 0.05 + (double)Mth.m_14154_((float)WyHelper.randomDouble() / 12.0F);
      double motionZ = WyHelper.randomDouble() / (double)5.0F;
      SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.DARKNESS.get());
      data.setLife(21);
      data.setAnimationSpeed(3);
      data.setSize(20.0F);
      data.setMotion(motionX, motionY, motionZ);
      data.setRotation(0.0F, 0.0F, 1.0F);
      world.m_6493_(data, true, posX + WyHelper.randomDouble(), posY, posZ + WyHelper.randomDouble(), (double)0.0F, (double)0.0F, (double)0.0F);
   }
}
