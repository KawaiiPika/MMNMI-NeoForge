package xyz.pixelatedw.mineminenomi.particles.effects.zushi;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GraviZoneParticleEffect extends ParticleEffect<Details> {
   public GraviZoneParticleEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      for(double z = (double)0.0F; z < 7.283185307179586; z += 0.09817477042468103) {
         SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.GASU.get());
         part.setLife(12);
         part.setSize(2.0F);
         double offsetX = Math.cos(z) * (double)details.getRange();
         double offsetZ = Math.sin(z) * (double)details.getRange();
         part.setMotion(offsetX / (double)20.0F, (double)0.0F, offsetZ / (double)20.0F);
         part.setRotation(0.0F, 0.0F, 1.0F);
         part.setRotationSpeed(world.m_213780_().m_188501_() / 2.0F);
         world.m_6493_(part, true, posX + offsetX, posY + (double)1.0F + (double)details.getYOffset(), posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }

   public static class Details extends ParticleEffect.Details {
      private int range;
      private int yOffset;

      public void save(CompoundTag nbt) {
         nbt.m_128405_("range", this.range);
         nbt.m_128405_("yOffset", this.yOffset);
      }

      public void load(CompoundTag nbt) {
         this.range = nbt.m_128451_("range");
         this.yOffset = nbt.m_128451_("yOffset");
      }

      public void setYOffset(int offset) {
         this.yOffset = offset;
      }

      public int getYOffset() {
         return this.yOffset;
      }

      public void setRange(int range) {
         this.range = range;
      }

      public int getRange() {
         return this.range;
      }
   }
}
