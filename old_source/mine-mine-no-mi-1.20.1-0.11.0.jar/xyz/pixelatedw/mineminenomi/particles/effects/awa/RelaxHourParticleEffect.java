package xyz.pixelatedw.mineminenomi.particles.effects.awa;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RelaxHourParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 15; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)2.0F;
         double offsetY = WyHelper.randomDouble() / (double)2.0F;
         double offsetZ = WyHelper.randomDouble() / (double)2.0F;
         ParticleType<SimpleParticleData> particle = (ParticleType)ModParticleTypes.AWA.get();
         if (i % 3 == 0) {
            particle = (ParticleType)ModParticleTypes.AWA3.get();
         }

         SimpleParticleData data = new SimpleParticleData(particle);
         data.setLife(7);
         data.setSize(1.3F);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

      for(int i = 0; i < 5; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)2.0F;
         double offsetY = WyHelper.randomDouble() / (double)2.0F;
         double offsetZ = WyHelper.randomDouble() / (double)2.0F;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.AWA_FOAM.get());
         data.setLife(7);
         data.setSize(1.3F);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
