package xyz.pixelatedw.mineminenomi.particles.effects.yomi;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KasuriutaFubukiGiriParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 20; ++i) {
         double offsetX = WyHelper.randomDouble();
         double offsetY = WyHelper.randomDouble();
         double offsetZ = WyHelper.randomDouble();
         SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.HIE.get());
         part.setLife(14);
         part.setAnimationSpeed(2);
         part.setRotation(0.0F, 0.0F, 1.0F);
         part.setRotationSpeed(i % 2 == 0 ? 0.07F : -0.07F);
         part.setSize(1.3F);
         world.m_6493_(part, true, posX + offsetX, posY + (double)1.5F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
