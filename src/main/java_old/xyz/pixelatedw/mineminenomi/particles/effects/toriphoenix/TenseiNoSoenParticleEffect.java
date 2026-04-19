package xyz.pixelatedw.mineminenomi.particles.effects.toriphoenix;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class TenseiNoSoenParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      ++posY;
      double radius = (double)1.0F;
      double phi = (double)0.0F;

      while(phi < Math.PI) {
         phi += (Math.PI / 4D);

         for(double theta = (double)0.0F; theta <= (Math.PI * 2D); ++theta) {
            double x = radius * Math.cos(theta) * Math.sin(phi) + WyHelper.randomDouble();
            double y = radius * Math.cos(phi) + WyHelper.randomDouble();
            double z = radius * Math.sin(theta) * Math.sin(phi) + WyHelper.randomDouble();
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.BLUE_FLAME.get());
            data.setLife(20);
            data.setSize(2.0F);
            data.setMotion((double)0.0F, 0.02, (double)0.0F);
            world.m_6493_(data, true, posX + x, posY - (double)1.0F + y, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
