package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RaigoParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      ParticleType<SimpleParticleData> goro2_particle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO2.get() : (ParticleType)ModParticleTypes.GORO2_YELLOW.get();
      ParticleType<SimpleParticleData> goro3_particle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO3.get() : (ParticleType)ModParticleTypes.GORO3_YELLOW.get();
      int range = 128;

      for(int i = 0; i < range; ++i) {
         double offsetX = WyHelper.randomWithRange(-range, range) + WyHelper.randomDouble();
         double offsetY = (double)40.0F + WyHelper.randomWithRange(-5, 5) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-range, range) + WyHelper.randomDouble();
         SimpleParticleData data = new SimpleParticleData(goro3_particle);
         data.setLife(100);
         data.setSize(100.0F);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
         if (i % 2 == 0) {
            data.setColor(0.4F, 0.4F, 0.4F);
         } else {
            data.setColor(0.3F, 0.3F, 0.3F);
         }
      }

      for(int i = 0; i < range / 2; ++i) {
         double offsetX = WyHelper.randomWithRange(-range, range) + WyHelper.randomDouble();
         double offsetY = (double)30.0F + WyHelper.randomWithRange(-5, 0) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-range, range) + WyHelper.randomDouble();
         SimpleParticleData data = new SimpleParticleData(goro2_particle);
         data.setLife(10);
         data.setSize(40.0F);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
