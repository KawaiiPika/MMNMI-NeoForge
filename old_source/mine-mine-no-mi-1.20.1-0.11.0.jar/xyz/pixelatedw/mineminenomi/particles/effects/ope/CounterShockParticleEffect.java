package xyz.pixelatedw.mineminenomi.particles.effects.ope;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CounterShockParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 20; ++i) {
         double x = WyHelper.randomDouble();
         double y = WyHelper.randomDouble();
         double z = WyHelper.randomDouble();
         double motionY = 0.005 + Math.abs(WyHelper.randomDouble() / (double)8.0F);
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.GORO2.get());
         data.setLife(5);
         data.setSize(3.5F);
         data.setMotion((double)0.0F, motionY, (double)0.0F);
         world.m_6493_(data, true, posX + x, posY + (double)1.5F + y, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
