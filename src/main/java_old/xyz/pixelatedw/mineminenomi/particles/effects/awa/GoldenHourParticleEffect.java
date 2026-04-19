package xyz.pixelatedw.mineminenomi.particles.effects.awa;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GoldenHourParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
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

         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.AWA2.get());
         data.setLife(age);
         data.setSize(1.5F);
         data.setMotion((double)0.0F, motionY, (double)0.0F);
         world.m_6493_(data, true, posX + offsetX, posY + (double)1.25F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
         data = new SimpleParticleData((ParticleType)ModParticleTypes.AWA_FOAM.get());
         data.setLife(age);
         data.setSize(1.5F);
         data.setMotion((double)0.0F, motionY / (double)2.0F, (double)0.0F);
         data.setColor(1.0F, 1.0F, 0.0F, 0.5F);
         world.m_6493_(data, true, posX + offsetX / (double)2.0F, posY + (double)1.0F + offsetY / (double)2.0F, posZ + offsetZ / (double)2.0F, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
