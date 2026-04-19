package xyz.pixelatedw.mineminenomi.particles.effects;

import java.util.function.Supplier;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class LogiaParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   private Supplier<ParticleType<SimpleParticleData>> particle;

   public LogiaParticleEffect(Supplier<ParticleType<SimpleParticleData>> particle) {
      this.particle = particle;
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      Minecraft mc = Minecraft.m_91087_();
      boolean isOwner = mc.f_91074_.equals(entity);
      boolean isFirstPerson = mc.f_91066_.m_92176_() == CameraType.FIRST_PERSON;
      if (!isOwner || !isFirstPerson) {
         for(int i = 0; (double)i < WyHelper.randomWithRange(7, 12); ++i) {
            double offsetX = WyHelper.randomDouble() / 1.7;
            double offsetY = (double)0.25F + WyHelper.randomDouble() / (double)3.0F;
            double offsetZ = WyHelper.randomDouble() / 1.7;
            int age = (int)((double)1.0F + WyHelper.randomWithRange(0, 4));
            double motionY = WyHelper.randomDouble() / (double)50.0F;
            if (motionY < (double)0.0F) {
               motionY = 0.005;
            }

            if (this.particle == ModParticleTypes.GORO && ClientConfig.isGoroBlue()) {
               this.particle = ModParticleTypes.GORO_YELLOW;
            }

            SimpleParticleData part = new SimpleParticleData((ParticleType)this.particle.get());
            part.setLife(age);
            if (this.particle == ModParticleTypes.HIE) {
               part.setLife(7);
               part.setAnimationSpeed(1);
            }

            part.setSize(1.5F);
            part.setMotion((double)0.0F, motionY, (double)0.0F);
            world.m_6493_(part, true, posX + offsetX, posY + (double)1.0F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
         }

      }
   }
}
