package xyz.pixelatedw.mineminenomi.particles.effects.pika;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ChargingPikaParticleEvent extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 2; ++i) {
         double offsetX = WyHelper.randomDouble() * 1.55;
         double offsetY = WyHelper.randomDouble();
         double offsetZ = WyHelper.randomDouble() * 1.55;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.PIKA.get());
         data.setLife(5);
         data.setSize(3.0F);
         data.setMotion((double)0.0F, 0.15, (double)0.0F);
         data.setRotation(0.0F, 1.0F, 0.0F);
         world.m_6493_(data, true, posX + offsetX, posY + (double)0.5F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
