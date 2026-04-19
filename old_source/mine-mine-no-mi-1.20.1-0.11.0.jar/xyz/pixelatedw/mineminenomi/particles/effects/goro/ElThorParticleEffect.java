package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElThorParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      ParticleType<SimpleParticleData> goro2_particle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO2.get() : (ParticleType)ModParticleTypes.GORO2_YELLOW.get();
      ParticleType<SimpleParticleData> goro3_particle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO3.get() : (ParticleType)ModParticleTypes.GORO3_YELLOW.get();

      for(int i = 0; i < 30; ++i) {
         double offsetX = WyHelper.randomWithRange(-32, 32) + WyHelper.randomDouble();
         double offsetY = (double)72.0F + WyHelper.randomWithRange(-3, 3) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-32, 32) + WyHelper.randomDouble();
         SimpleParticleData data = new SimpleParticleData(goro3_particle);
         data.setLife(140);
         data.setSize(50.0F);
         if (i % 2 == 0) {
            data.setColor(0.4F, 0.4F, 0.4F);
         } else {
            data.setColor(0.3F, 0.3F, 0.3F);
         }

         world.m_6493_(data, true, posX + (double)0.5F + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

      for(int i = 0; i < 16; ++i) {
         double offsetX = WyHelper.randomWithRange(-4, 4) + WyHelper.randomDouble();
         double offsetY = (double)72.0F + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-4, 4) + WyHelper.randomDouble();
         SimpleParticleData data = new SimpleParticleData(goro2_particle);
         data.setLife(100);
         data.setSize(15.0F);
         world.m_6493_(data, true, posX + (double)0.5F + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
