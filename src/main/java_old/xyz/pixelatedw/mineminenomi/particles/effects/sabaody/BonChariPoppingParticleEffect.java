package xyz.pixelatedw.mineminenomi.particles.effects.sabaody;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BonChariPoppingParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 12; ++i) {
         double motionX = WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble();
         double motionY = WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble();
         double motionZ = WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble();
         double middlePoint = (double)0.25F;
         middlePoint *= WyHelper.randomDouble() * (double)2.0F + (double)0.3F;
         motionX *= middlePoint / (double)2.0F;
         motionY *= middlePoint / (double)2.0F;
         motionZ *= middlePoint / (double)2.0F;
         SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.AWA.get());
         part.setLife(15);
         part.setSize(7.0F);
         part.setMotion(motionX, motionY, motionZ);
         part.setRotation(0.0F, 0.0F, 1.0F);
         part.setRotationSpeed(1.0F);
         world.m_6493_(part, true, posX, posY + 0.3, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
