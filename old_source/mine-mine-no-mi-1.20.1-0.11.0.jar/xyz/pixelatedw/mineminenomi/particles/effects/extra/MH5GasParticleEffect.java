package xyz.pixelatedw.mineminenomi.particles.effects.extra;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MH5GasParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 2048; ++i) {
         double offsetX = WyHelper.randomWithRange(-100, 100) + WyHelper.randomDouble();
         double offsetY = WyHelper.randomWithRange(0, 20) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-100, 100) + WyHelper.randomDouble();
         int age = (int)((double)10.0F + WyHelper.randomWithRange(0, 15));
         double motionY = WyHelper.randomDouble() / (double)50.0F;
         if (motionY < (double)0.0F) {
            motionY = 0.02;
         }

         SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.DOKU.get());
         if (world.m_213780_().m_188503_(3) == 0) {
            part = new SimpleParticleData((ParticleType)ModParticleTypes.AWA_FOAM.get());
            part.setColor(1.0F, 0.0F, 1.0F, 0.5F);
         }

         part.setLife(age);
         part.setSize(2.5F);
         part.setMotion((double)0.0F, motionY / (double)2.0F, (double)0.0F);
         part.setRotation(entity.m_9236_().m_213780_().m_188499_() ? new Vector3f(0.0F, 0.0F, 1.0F) : new Vector3f(0.0F, 0.0F, -1.0F));
         part.setRotationSpeed(0.05F);
         world.m_7106_(part, posX + offsetX / (double)2.0F, posY + (double)1.0F + offsetY / (double)2.0F, posZ + offsetZ / (double)2.0F, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
