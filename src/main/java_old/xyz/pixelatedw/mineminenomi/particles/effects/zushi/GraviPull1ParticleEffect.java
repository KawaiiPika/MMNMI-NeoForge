package xyz.pixelatedw.mineminenomi.particles.effects.zushi;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GraviPull1ParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(double i = (double)0.0F; i < 7.283185307179586; i += 0.09817477042468103) {
         SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.GASU.get());
         part.setLife(100);
         part.setSize(4.0F);
         double offsetX = Math.cos(i);
         double offsetZ = Math.sin(i);
         part.setMotion(offsetX / (double)5.0F, (double)0.0F, offsetZ / (double)5.0F);
         part.setHasMotionDecay(false);
         part.setRotation(0.0F, 0.0F, 1.0F);
         part.setRotationSpeed(world.m_213780_().m_188501_() / 2.0F);
         world.m_6493_(part, true, posX + offsetX, posY + (double)1.0F, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
