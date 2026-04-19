package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SangoParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      ParticleType<SimpleParticleData> goro_particle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO.get() : (ParticleType)ModParticleTypes.GORO_YELLOW.get();
      ParticleType<SimpleParticleData> goro2_particle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO2.get() : (ParticleType)ModParticleTypes.GORO2_YELLOW.get();

      for(int i = 0; i < 16; ++i) {
         double offsetX = WyHelper.randomDouble();
         double offsetY = WyHelper.randomDouble();
         double offsetZ = WyHelper.randomDouble();
         SimpleParticleData data = new SimpleParticleData(goro2_particle);
         data.setLife(5);
         data.setSize(2.0F);
         data.setColor(1.0F, 1.0F, 1.0F, 0.7F);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
         SimpleParticleData data2 = new SimpleParticleData(goro_particle);
         data2.setLife(5);
         data2.setSize(2.0F);
         data2.setColor(1.0F, 1.0F, 1.0F, 0.7F);
         data2.setRotation(0.0F, 0.0F, 1.0F);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
