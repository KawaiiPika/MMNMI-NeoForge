package xyz.pixelatedw.mineminenomi.particles.effects.kobu;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ShoureiParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 20; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)2.0F;
         double offsetY = WyHelper.randomDouble() / (double)2.0F;
         double offsetZ = WyHelper.randomDouble() / (double)2.0F;
         SimpleParticleData particleData = new SimpleParticleData((ParticleType)ModParticleTypes.CHIYU.get());
         particleData.setLife(7);
         particleData.setSize(1.0F);
         double y = world.f_46441_.m_188500_() / (double)15.0F;
         particleData.setMotion((double)0.0F, y, (double)0.0F);
         particleData.setColor(0.5F, 1.0F, 0.5F, 0.7F);
         world.m_6493_(particleData, true, posX + offsetX, posY + (double)1.0F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
