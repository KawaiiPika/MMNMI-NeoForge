package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KariParticleEffect extends ParticleEffect<Details> {
   public KariParticleEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      ParticleType<SimpleParticleData> goroParticle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO.get() : (ParticleType)ModParticleTypes.GORO_YELLOW.get();
      ParticleType<SimpleParticleData> goro2Particle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO2.get() : (ParticleType)ModParticleTypes.GORO2_YELLOW.get();

      for(int i = 0; i < 16 * details.getRange(); ++i) {
         double offsetX = WyHelper.randomWithRange(-details.getRange(), details.getRange()) + WyHelper.randomDouble();
         double offsetY = WyHelper.randomWithRange(-details.getRange(), 2 + details.getRange()) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-details.getRange(), details.getRange()) + WyHelper.randomDouble();
         SimpleParticleData data = new SimpleParticleData(goro2Particle);
         data.setLife(5);
         data.setSize(details.getSize());
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
         SimpleParticleData data2 = new SimpleParticleData(goroParticle);
         data2.setLife(5);
         data2.setSize(details.getSize());
         data.setRotation(0.0F, 1.0F, 0.0F);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }

   public static class Details extends ParticleEffect.Details {
      private int range;
      private float size;

      public void save(CompoundTag nbt) {
         nbt.m_128405_("range", this.range);
         nbt.m_128350_("size", this.size);
      }

      public void load(CompoundTag nbt) {
         this.range = nbt.m_128451_("range");
         this.size = nbt.m_128457_("size");
      }

      public int getRange() {
         return this.range;
      }

      public float getSize() {
         return this.size;
      }

      public void setRange(int range) {
         this.range = range;
      }

      public void setSize(float range) {
         this.size = range;
      }
   }
}
