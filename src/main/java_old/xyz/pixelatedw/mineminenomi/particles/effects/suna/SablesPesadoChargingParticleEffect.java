package xyz.pixelatedw.mineminenomi.particles.effects.suna;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SablesPesadoChargingParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      double multiplier = (double)1.0F;
      double phi = (double)0.0F;
      SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.SUNA2.get());
      data.setLife(20);
      data.setSize(1.3F);

      while(phi < (Math.PI * 2D)) {
         phi += 0.19634954084936207;

         for(double t = (double)0.0F; t <= (Math.PI * 2D); t += 0.19634954084936207) {
            double x = 0.45 * multiplier * ((Math.PI * 2D) + t) * Math.cos(t * phi * Math.PI) + WyHelper.randomDouble();
            double y = (double)1.5F * multiplier * t;
            double z = 0.45 * multiplier * ((Math.PI * 2D) + t) * Math.sin(t * phi * Math.PI) + WyHelper.randomDouble();
            data.setMotion((double)0.0F, 0.01 + Math.abs(WyHelper.randomDouble()) / (double)5.0F, (double)0.0F);
            entity.m_9236_().m_6493_(data, true, posX + x, posY + y, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
