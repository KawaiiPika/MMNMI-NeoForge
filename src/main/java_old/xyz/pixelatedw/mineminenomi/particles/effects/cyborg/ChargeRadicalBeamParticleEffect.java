package xyz.pixelatedw.mineminenomi.particles.effects.cyborg;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ChargeRadicalBeamParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      Vec3 spawnPoint = entity.m_20154_().m_82542_((double)1.0F, (double)1.0F, (double)1.0F).m_82520_((double)0.0F, (double)entity.m_20192_(), (double)0.0F);
      double r = 0.8;

      for(double phi = (double)0.0F; phi <= (Math.PI * 2D); phi += (Math.PI / 8D)) {
         if (entity.m_9236_().m_213780_().m_188503_(20) == 0) {
            double x = r * Math.sin(phi) + WyHelper.randomDouble() / (double)2.0F;
            double y = r * Math.cos(phi) + WyHelper.randomDouble() / (double)2.0F;
            double z = (double)0.0F;
            Vec3 vec = new Vec3(x, y, z);
            vec = VectorHelper.rotateAroundX(vec, Math.toRadians((double)entity.m_146909_()));
            vec = VectorHelper.rotateAroundY(vec, Math.toRadians((double)(-entity.m_146908_())));
            Vec3 spawnPos = entity.m_20182_().m_82549_(spawnPoint.m_82520_((double)0.0F, (double)(-entity.m_20192_()), (double)0.0F)).m_82549_(vec);
            Vec3 moveVec = entity.m_20182_().m_82546_(spawnPos);
            double motionX = moveVec.f_82479_ * 0.03;
            double motionY = moveVec.f_82480_ * 0.03;
            double motionZ = moveVec.f_82481_ * 0.03;
            SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.PIKA.get());
            part.setLife(20);
            part.setSize(1.0F);
            part.setRotation(0.0F, 1.0F, 0.0F);
            part.setMotion(motionX, motionY, motionZ);
            world.m_6493_(part, true, posX + spawnPoint.f_82479_ + vec.f_82479_, posY + spawnPoint.f_82480_ + vec.f_82480_, posZ + spawnPoint.f_82481_ + vec.f_82481_, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }
}
