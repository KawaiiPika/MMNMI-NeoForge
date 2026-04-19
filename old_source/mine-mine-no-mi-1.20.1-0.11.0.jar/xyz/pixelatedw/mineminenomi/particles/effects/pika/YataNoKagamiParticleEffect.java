package xyz.pixelatedw.mineminenomi.particles.effects.pika;

import java.util.Random;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class YataNoKagamiParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 20; ++i) {
         double offsetX = ((double)(new Random()).nextInt(40) + (double)1.0F - (double)20.0F) / (double)20.0F;
         double offsetY = ((double)(new Random()).nextInt(40) + (double)1.0F) / (double)20.0F;
         double offsetZ = ((double)(new Random()).nextInt(40) + (double)1.0F - (double)20.0F) / (double)20.0F;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.PIKA.get());
         data.setLife(20);
         data.setSize(4.0F);
         data.setRotation(0.0F, 1.0F, 0.0F);
         world.m_6493_(data, true, posX + offsetX, posY + (double)0.5F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
