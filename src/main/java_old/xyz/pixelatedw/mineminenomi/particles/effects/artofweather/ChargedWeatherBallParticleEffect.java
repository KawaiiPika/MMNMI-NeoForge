package xyz.pixelatedw.mineminenomi.particles.effects.artofweather;

import java.awt.Color;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ChargedWeatherBallParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   private float red;
   private float green;
   private float blue;
   private Supplier<ParticleType<SimpleParticleData>> particle;

   public ChargedWeatherBallParticleEffect(Color color, Supplier<ParticleType<SimpleParticleData>> particle) {
      this.red = (float)color.getRed() / 255.0F;
      this.green = (float)color.getGreen() / 255.0F;
      this.blue = (float)color.getBlue() / 255.0F;
      this.particle = particle;
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 10; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)1.5F;
         double offsetY = WyHelper.randomDouble() / (double)1.5F;
         double offsetZ = WyHelper.randomDouble() / (double)1.5F;
         SimpleParticleData data = new SimpleParticleData((ParticleType)this.particle.get());
         data.setLife(4);
         data.setSize(2.0F);
         data.setMotion((double)0.0F, 0.02, (double)0.0F);
         data.setColor(this.red, this.green, this.blue);
         world.m_6493_(data, true, posX + offsetX, posY + (double)1.0F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
