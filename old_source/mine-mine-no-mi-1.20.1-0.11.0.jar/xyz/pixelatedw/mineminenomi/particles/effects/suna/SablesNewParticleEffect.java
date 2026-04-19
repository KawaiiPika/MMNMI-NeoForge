package xyz.pixelatedw.mineminenomi.particles.effects.suna;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SablesNewParticleEffect extends ParticleEffect<Details> {
   public SablesNewParticleEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      int angle = 0;
      int maxHeight = (int)(details.size * 1.25F);
      double minRadius = (double)(details.size / 7.0F);
      double maxRadius = (double)(details.size * 3.0F);
      int lines = 6;
      double heightIncrease = 0.15;
      double radiusIncrement = maxRadius / (double)maxHeight / (double)4.0F;

      for(int l = 0; l < lines; ++l) {
         for(double y = (double)0.0F; y < (double)maxHeight; y += heightIncrease) {
            double radius = y * radiusIncrement;
            double v = (double)(360.0F / (float)lines * (float)l) + y * (double)25.0F;
            double x = Math.cos(Math.toRadians(v - (double)angle)) * Math.max(radius, minRadius);
            double z = Math.sin(Math.toRadians(v - (double)angle)) * Math.max(radius, minRadius);
            float t = WyHelper.colorTolerance(0.1F);
            SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.SUNA2.get());
            part.setLife(100);
            part.setSize(2.5F * (details.size / 15.0F));
            part.setMotion((double)0.0F, 0.1 + Math.abs(WyHelper.randomDouble() / (double)15.0F), (double)0.0F);
            part.setColor(1.0F - t, 1.0F - t, 1.0F - t);
            entity.m_9236_().m_6493_(part, true, posX + x, posY + y, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

      angle += 2;
   }

   public static class Details extends ParticleEffect.Details {
      private float size;

      public void save(CompoundTag nbt) {
         nbt.m_128350_("size", this.size);
      }

      public void load(CompoundTag nbt) {
         this.size = nbt.m_128457_("size");
      }

      public float getSize() {
         return this.size;
      }

      public void setSize(float size) {
         this.size = size;
      }
   }
}
