package xyz.pixelatedw.mineminenomi.particles.effects.pero;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CandyWaveParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 40; ++i) {
         double offsetX = WyHelper.randomDouble() * (double)3.0F;
         double offsetZ = WyHelper.randomDouble() * (double)3.0F;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.SUNA2.get());
         data.setLife(8);
         data.setSize(1.4F);
         data.setMotion((double)0.0F, 0.2 + Math.abs(WyHelper.randomDouble()) / (double)3.0F, (double)0.0F);
         data.setColor(0.5F, 0.0F, 0.5F);
         world.m_6493_(data, true, posX + offsetX, posY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
