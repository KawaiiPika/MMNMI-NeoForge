package xyz.pixelatedw.mineminenomi.particles.effects.toriphoenix;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class TenseiNoSoen2ParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double t = (double)0.0F;
      RandomSource rand = world.f_46441_;

      while(t < (double)3.0F) {
         t += (Math.PI / 10D);

         for(double theta = (double)0.0F; theta <= 12.566370614359172; theta += 0.19634954084936207) {
            double x = t * Math.cos(theta);
            double y = (double)rand.m_188503_(1);
            double z = t * Math.sin(theta);
            double motionX = x / (double)4.0F;
            double motionY = 0.05 + (double)Mth.m_14154_((float)WyHelper.randomDouble() / 12.0F);
            double motionZ = z / (double)4.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.BLUE_FLAME.get());
            data.setLife(20);
            data.setSize(2.0F);
            data.setMotion(motionX, motionY, motionZ);
            world.m_6493_(data, true, posX + x * (double)1.25F + WyHelper.randomDouble(), posY + (double)0.5F + y, posZ + z * (double)1.25F + WyHelper.randomDouble(), (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
