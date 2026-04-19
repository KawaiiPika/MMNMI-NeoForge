package xyz.pixelatedw.mineminenomi.particles.effects.yami;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BlackWorldParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 500; ++i) {
         double offsetX = WyHelper.randomWithRange(-10, 10) + WyHelper.randomDouble();
         double offsetY = WyHelper.randomWithRange(0, 2) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-10, 10) + WyHelper.randomDouble();
         SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.DARKNESS.get());
         part.setLife(14);
         part.setAnimationSpeed(2);
         part.setRotation(0.0F, 0.0F, 1.0F);
         part.setRotationSpeed(i % 2 == 0 ? 0.07F : -0.07F);
         part.setSize(1.2F);
         part.setMotion((double)0.0F, 0.02, (double)0.0F);
         world.m_6493_(part, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
         part.setLife(7);
         part.setAnimationSpeed(1);
         part.setRotation(0.0F, 0.0F, 1.0F);
         part.setRotationSpeed(i % 2 == 0 ? 0.15F : -0.15F);
         part.setSize(1.2F);
         part.setMotion((double)0.0F, 0.02, (double)0.0F);
         world.m_6493_(part, true, posX + offsetX, posY + (double)0.5F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
