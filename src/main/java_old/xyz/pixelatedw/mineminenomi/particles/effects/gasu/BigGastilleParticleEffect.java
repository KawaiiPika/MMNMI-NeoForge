package xyz.pixelatedw.mineminenomi.particles.effects.gasu;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BigGastilleParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 2; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)5.0F;
         double offsetY = WyHelper.randomDouble() / (double)5.0F;
         double offsetZ = WyHelper.randomDouble() / (double)5.0F;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.GASU2.get());
         data.setLife(5);
         data.setSize(0.8F);
         data.setColor(0.4F, 0.7F, 1.0F);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
