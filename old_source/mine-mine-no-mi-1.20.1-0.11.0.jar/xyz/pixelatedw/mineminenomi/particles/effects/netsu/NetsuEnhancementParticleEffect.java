package xyz.pixelatedw.mineminenomi.particles.effects.netsu;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class NetsuEnhancementParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 8; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)1.25F;
         double offsetY = WyHelper.randomDouble() / (double)1.25F;
         double offsetZ = WyHelper.randomDouble() / (double)1.25F;
         ParticleType<SimpleParticleData> particle = (ParticleType)ModParticleTypes.NETSU.get();
         if (i % 3 == 0) {
            particle = (ParticleType)ModParticleTypes.NETSU2.get();
         }

         if (i % 7 == 0) {
            particle = (ParticleType)ModParticleTypes.MERA.get();
         }

         SimpleParticleData data = new SimpleParticleData(particle);
         data.setLife(10);
         data.setSize(1.3F);
         world.m_6493_(data, true, posX + offsetX, posY + (double)1.0F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
