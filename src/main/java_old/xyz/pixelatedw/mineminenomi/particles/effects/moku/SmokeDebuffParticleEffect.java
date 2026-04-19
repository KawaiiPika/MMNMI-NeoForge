package xyz.pixelatedw.mineminenomi.particles.effects.moku;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SmokeDebuffParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 80; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)2.0F;
         double offsetY = (double)1.0F + WyHelper.randomDouble() / (double)2.0F;
         double offsetZ = WyHelper.randomDouble() / (double)2.0F;
         ParticleType<SimpleParticleData> particle = (ParticleType)ModParticleTypes.MOKU2.get();
         if (i % 3 == 0) {
            particle = (ParticleType)ModParticleTypes.MOKU.get();
         }

         SimpleParticleData data = new SimpleParticleData(particle);
         data.setLife((int)WyHelper.randomWithRange(1, 10));
         data.setSize((float)WyHelper.randomWithRange(1, 4));
         world.m_6493_(data, true, entity.m_20185_() + offsetX, entity.m_20186_() + offsetY, entity.m_20189_() + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
