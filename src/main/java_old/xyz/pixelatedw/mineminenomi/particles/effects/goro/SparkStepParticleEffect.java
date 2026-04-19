package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SparkStepParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      ParticleType<SimpleParticleData> goro_particle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO.get() : (ParticleType)ModParticleTypes.GORO_YELLOW.get();
      Vec3 originalPos = new Vec3(posX, posY, posZ);
      double r = (double)2.5F;

      for(double phi = (double)0.0F; phi <= (Math.PI * 2D); phi += (Math.PI / 8D)) {
         double x = r * Math.sin(phi) + WyHelper.randomDouble() / (double)2.0F;
         double y = r * Math.cos(phi) + WyHelper.randomDouble() / (double)2.0F;
         double z = (double)0.0F;
         Vec3 vec = new Vec3(x, y, z);
         vec = VectorHelper.rotateAroundX(vec, Math.toRadians((double)entity.m_146909_()));
         vec = VectorHelper.rotateAroundY(vec, Math.toRadians((double)(-entity.m_146908_())));
         Vec3 spawnPos = originalPos.m_82549_(vec);
         Vec3 moveVec = originalPos.m_82546_(spawnPos);
         double motionX = moveVec.f_82479_ * 0.18;
         double motionY = moveVec.f_82480_ * 0.18;
         double motionZ = moveVec.f_82481_ * 0.18;
         SimpleParticleData part = new SimpleParticleData(goro_particle);
         part.setLife(7);
         part.setSize(8.0F);
         part.setMotion(motionX, motionY, motionZ);
         world.m_7106_(part, posX + vec.f_82479_, posY + vec.f_82480_, posZ + vec.f_82481_, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
