package xyz.pixelatedw.mineminenomi.particles.effects.gasu;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ShinokuniParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 10; ++i) {
         double offsetX = WyHelper.randomDouble() * (double)2.0F;
         double offsetY = WyHelper.randomDouble() * (double)2.0F;
         double offsetZ = WyHelper.randomDouble() * (double)2.0F;
         ParticleType<SimpleParticleData> type;
         if (i % 5 == 0) {
            type = (ParticleType)ModParticleTypes.GASU2.get();
         } else {
            type = (ParticleType)ModParticleTypes.GASU.get();
         }

         SimpleParticleData data = new SimpleParticleData(type);
         data.setMotion((double)0.0F, 0.1, (double)0.0F);
         data.setLife(40);
         data.setSize(3.0F);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
