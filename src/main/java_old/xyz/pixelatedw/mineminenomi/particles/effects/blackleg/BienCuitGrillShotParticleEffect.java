package xyz.pixelatedw.mineminenomi.particles.effects.blackleg;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BienCuitGrillShotParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double motionX = entity.m_20184_().f_82479_;
      double motionY = entity.m_20184_().f_82480_;
      double motionZ = entity.m_20184_().f_82481_;

      for(int k = -2; k <= 2; ++k) {
         for(int i = -10; i <= 10; ++i) {
            for(int j = 0; j < 3; ++j) {
               double offsetX = WyHelper.randomDouble() / (double)2.0F;
               double offsetY = WyHelper.randomDouble() / (double)2.0F;
               double offsetZ = WyHelper.randomDouble() / (double)2.0F;
               SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
               data.setLife(10);
               data.setSize(1.0F);
               data.setMotion(motionX / 1.7, motionY / 1.7, motionZ / 1.7);
               world.m_6493_(data, true, posX - (double)(i / 5) + offsetX, posY + (double)1.5F + (double)k / 1.2 + offsetY, posZ - (double)(i / 5) + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
            }
         }
      }

      SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.GRILL.get());
      data.setLife(10);
      data.setSize(40.0F);
      data.setMotion(motionX / 1.4, motionY / 1.4, motionZ / 1.4);
      world.m_6493_(data, true, posX, posY + (double)1.5F, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
   }
}
