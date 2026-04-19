package xyz.pixelatedw.mineminenomi.particles.effects.jiki;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class AttractParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; (double)i < WyHelper.randomWithRange(6, 9); ++i) {
         double offsetX = WyHelper.randomDouble() / 1.7;
         double offsetY = WyHelper.randomDouble() * 1.55;
         double offsetZ = WyHelper.randomDouble() / 1.7;
         int age = (int)((double)3.0F + WyHelper.randomWithRange(0, 4));
         double motionY = WyHelper.randomDouble() / (double)50.0F;
         if (motionY < (double)0.0F) {
            motionY = 0.02;
         }

         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MAGNET.get());
         data.setLife(age);
         data.setSize(4.0F);
         data.setMotion((double)0.0F, motionY, (double)0.0F);
         world.m_6493_(data, true, posX + offsetX, posY + (double)1.25F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
