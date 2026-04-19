package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GenericUseLightningParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      ParticleType<SimpleParticleData> goro_particle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO.get() : (ParticleType)ModParticleTypes.GORO_YELLOW.get();

      for(int i = 0; i < 25; ++i) {
         double offsetX = WyHelper.randomDouble();
         double offsetY = WyHelper.randomWithRange(-1, 1) + WyHelper.randomDouble() / (double)2.0F;
         double offsetZ = WyHelper.randomDouble();
         int age = (int)WyHelper.randomWithRange(2, 8);
         SimpleParticleData data = new SimpleParticleData(goro_particle);
         data.setLife(age);
         data.setSize(1.4F);
         data.setMotion((double)0.0F, 0.01, (double)0.0F);
         world.m_6493_(data, true, posX + offsetX, posY + (double)1.0F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
