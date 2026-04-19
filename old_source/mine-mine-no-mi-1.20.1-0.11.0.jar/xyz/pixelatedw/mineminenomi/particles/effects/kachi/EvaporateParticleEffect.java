package xyz.pixelatedw.mineminenomi.particles.effects.kachi;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class EvaporateParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 3; ++i) {
         double offsetX = WyHelper.randomDouble();
         double offsetY = WyHelper.randomDouble();
         double offsetZ = WyHelper.randomDouble();
         double motionX = WyHelper.randomWithRange(0, 1) + WyHelper.randomDouble();
         double motionY = WyHelper.randomWithRange(0, 1) + WyHelper.randomDouble();
         double motionZ = WyHelper.randomWithRange(0, 1) + WyHelper.randomDouble();
         double middlePoint = 0.05;
         middlePoint *= WyHelper.randomDouble() * (double)2.0F + (double)0.3F;
         motionX *= middlePoint / (double)2.0F;
         motionY *= middlePoint / (double)2.0F;
         motionZ *= middlePoint / (double)2.0F;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU.get());
         data.setLife(10);
         data.setSize(2.5F);
         data.setMotion(motionX, motionY + 0.05, motionZ);
         world.m_6493_(data, true, posX + (double)1.5F + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
         offsetX = WyHelper.randomDouble();
         offsetY = WyHelper.randomDouble();
         offsetZ = WyHelper.randomDouble();
         data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
         data.setLife(10);
         data.setSize(2.5F);
         data.setMotion(motionX, motionY + 0.05, motionZ);
         world.m_6493_(data, true, posX + (double)1.5F + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
