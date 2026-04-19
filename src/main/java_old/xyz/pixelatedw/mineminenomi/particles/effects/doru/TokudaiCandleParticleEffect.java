package xyz.pixelatedw.mineminenomi.particles.effects.doru;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class TokudaiCandleParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 2048; ++i) {
         double offsetX = WyHelper.randomWithRange(-50, 50) + WyHelper.randomDouble();
         double offsetY = WyHelper.randomWithRange(0, 50) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-50, 50) + WyHelper.randomDouble();
         double motionX = WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble();
         double motionZ = WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble();
         double middlePoint = 1.2;
         motionX *= middlePoint / (double)25.0F;
         motionZ *= middlePoint / (double)25.0F;
         float scale = (float)((double)1.0F + WyHelper.randomWithRange(0, 2));
         float rotation = world.m_213780_().m_188501_() / 4.0F;
         SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU.get());
         part.setLife(300);
         part.setSize(scale);
         part.setRotation(0.0F, 0.0F, 1.0F);
         part.setRotationSpeed(rotation);
         part.setMotion(motionX, -0.05, motionZ);
         part.setHasMotionDecay(false);
         world.m_6493_(part, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
