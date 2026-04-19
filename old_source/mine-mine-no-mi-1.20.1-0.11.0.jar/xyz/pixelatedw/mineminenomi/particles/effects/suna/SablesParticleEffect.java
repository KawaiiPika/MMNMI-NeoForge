package xyz.pixelatedw.mineminenomi.particles.effects.suna;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SablesParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   private float size = 1.0F;

   public SablesParticleEffect() {
      this.size = 1.0F;
   }

   public SablesParticleEffect(float size) {
      this.size = size;
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      int angle = 0;
      int maxHeight = 35 + (int)(2.0F * this.size);
      double minRadius = (double)(1.0F * this.size);
      double maxRadius = (double)(20.0F * this.size);
      int lines = 12;
      double heightIncrease = (double)0.25F;
      double radiusIncrement = maxRadius / (double)maxHeight * (double)(1.0F - this.size / 2.0F);

      for(int l = 0; l < lines; ++l) {
         for(double y = (double)0.0F; y < (double)maxHeight; y += heightIncrease) {
            double radius = y * radiusIncrement;
            double v = (double)(360.0F / (float)lines * (float)l) + y * (double)25.0F;
            double x = Math.cos(Math.toRadians(v - (double)angle)) * Math.max(radius, minRadius);
            double z = Math.sin(Math.toRadians(v - (double)angle)) * Math.max(radius, minRadius);
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.SUNA2.get());
            data.setLife(70);
            data.setSize(2.5F * this.size);
            data.setMotion(entity.m_20184_().f_82479_, entity.m_20184_().f_82480_ + 0.1 - Math.abs(WyHelper.randomDouble() / (double)5.0F), entity.m_20184_().f_82481_);
            entity.m_9236_().m_6493_(data, true, posX + x, posY + y, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

      angle += 2;
   }
}
