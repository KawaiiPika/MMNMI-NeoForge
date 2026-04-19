package xyz.pixelatedw.mineminenomi.particles.effects;

import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class SpecialFlyingParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   private Supplier<ParticleType<SimpleParticleData>> particle;

   public SpecialFlyingParticleEffect(Supplier<ParticleType<SimpleParticleData>> particle) {
      this.particle = particle;
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; (double)i < WyHelper.randomWithRange(5, 10); ++i) {
         double offsetX = WyHelper.randomDouble() / 1.7;
         double offsetY = -0.55 + WyHelper.randomDouble() / (double)3.0F;
         double offsetZ = WyHelper.randomDouble() / 1.7;
         int age = (int)((double)1.0F + WyHelper.randomWithRange(0, 20));
         double motionY = WyHelper.randomDouble() / (double)10.0F * (double)-1.0F;
         if (motionY > (double)0.0F) {
            motionY = -0.005;
         }

         SimpleParticleData data = new SimpleParticleData((ParticleType)this.particle.get());
         data.setLife(age);
         data.setSize(2.5F);
         data.setMotion((double)0.0F, motionY, (double)0.0F);
         world.m_6493_(data, true, posX + offsetX, posY + (double)1.0F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
