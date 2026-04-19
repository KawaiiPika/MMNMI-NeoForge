package xyz.pixelatedw.mineminenomi.particles.effects.kage;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ChargeableKageParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 2; ++i) {
         double offsetX = WyHelper.randomDouble() * 0.55;
         double offsetY = WyHelper.randomDouble();
         double offsetZ = WyHelper.randomDouble() * 0.55;
         SimpleParticleData particle = new SimpleParticleData((ParticleType)ModParticleTypes.DARKNESS_STATIC.get());
         particle.setRotation(0.0F, 0.0F, 1.0F);
         particle.setRotationSpeed(i % 2 == 0 ? 0.15F : -0.15F);
         particle.setLife(5);
         particle.setSize(2.0F);
         particle.setMotion((double)0.0F, 0.1, (double)0.0F);
         world.m_6493_(particle, true, posX + offsetX, posY + (double)0.5F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
