package xyz.pixelatedw.mineminenomi.particles.effects.pika;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FlashParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 10; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)1.25F;
         double offsetY = WyHelper.randomDouble() * (double)1.25F;
         double offsetZ = WyHelper.randomDouble() / (double)1.25F;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.PIKA.get());
         data.setLife(5);
         data.setSize(4.0F);
         data.setRotation(0.0F, 1.0F, 0.0F);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
