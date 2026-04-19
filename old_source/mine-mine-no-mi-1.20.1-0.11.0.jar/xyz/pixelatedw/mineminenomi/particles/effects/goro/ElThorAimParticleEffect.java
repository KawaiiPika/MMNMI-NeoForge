package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElThorAimParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      ParticleType<SimpleParticleData> goroParticle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO.get() : (ParticleType)ModParticleTypes.GORO_YELLOW.get();

      for(int i = 0; i < 128; ++i) {
         double offsetX = WyHelper.randomDouble() * (double)10.0F;
         double offsetZ = WyHelper.randomDouble() * (double)10.0F;
         SimpleParticleData part = new SimpleParticleData(goroParticle);
         part.setLife(10);
         part.setSize(10.0F);
         part.setFunction(EasingFunction.CUBIC_OUT);
         world.m_6493_(part, true, posX + (double)0.5F + offsetX, posY + (double)0.5F, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
