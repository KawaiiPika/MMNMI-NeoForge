package xyz.pixelatedw.mineminenomi.particles.effects.doctor;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MedicBagExplosionParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double t = (double)0.0F;
      RandomSource rand = world.f_46441_;

      while(t < (double)1.0F) {
         ++t;

         for(double theta = (double)0.0F; theta <= 12.566370614359172; theta += 0.09817477042468103) {
            double x = t * Math.cos(theta);
            double y = (double)rand.m_188503_(1);
            double z = t * Math.sin(theta);
            double motionX = x / (double)4.0F;
            double motionY = 0.05 + rand.m_188500_() / (double)7.0F;
            double motionZ = z / (double)4.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MEDIC.get());
            data.setLife(4);
            data.setSize(2.0F);
            data.setMotion(motionX, motionY, motionZ);
            data.setColor(0.0F, 0.8F, 0.0F);
            world.m_6493_(data, true, posX + x * (double)0.75F + WyHelper.randomDouble(), posY + y, posZ + z * (double)0.75F + WyHelper.randomDouble(), (double)0.0F, (double)0.0F, (double)0.0F);
            data = new SimpleParticleData((ParticleType)ModParticleTypes.MEDIC.get());
            data.setLife(7);
            data.setSize(2.5F);
            data.setMotion(motionX, motionY, motionZ);
            data.setColor(0.0F, 0.8F, 0.0F);
            world.m_6493_(data, true, posX + x * (double)2.0F + WyHelper.randomDouble(), posY + y, posZ + z * (double)2.0F + WyHelper.randomDouble(), (double)0.0F, (double)0.0F, (double)0.0F);
            data = new SimpleParticleData((ParticleType)ModParticleTypes.MEDIC.get());
            data.setLife(10);
            data.setSize(4.5F);
            data.setMotion(motionX, motionY * (double)2.25F, motionZ);
            data.setColor(0.0F, 0.8F, 0.0F);
            world.m_6493_(data, true, posX + x * (double)3.25F + WyHelper.randomDouble(), posY + y, posZ + z * (double)3.25F + WyHelper.randomDouble(), (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
