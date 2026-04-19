package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class BerserkerPowerupParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      Vec3 playerPos = new Vec3(posX, posY, posZ);
      double r = (double)2.0F;

      for(double phi = (double)0.0F; phi <= (Math.PI * 2D); phi += 0.09817477042468103) {
         double x = r * Math.cos(phi) + WyHelper.randomDouble() / (double)5.0F;
         double z = r * Math.sin(phi) + WyHelper.randomDouble() / (double)5.0F;
         Vec3 pos = playerPos.m_82549_(new Vec3(x, posY, z));
         Vec3 dirVec = playerPos.m_82546_(pos).m_82541_().m_82542_((double)1.25F, (double)1.0F, (double)1.25F);
         SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU.get());
         part.setLife(15);
         part.setSize(7.0F);
         part.setMotion(-dirVec.f_82479_, (double)0.0F, -dirVec.f_82481_);
         part.setRotation(0.0F, 0.0F, 1.0F);
         part.setRotationSpeed(1.0F);
         world.m_6493_(part, true, posX + x, posY + 0.3, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
         part = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU2.get());
         part.setLife(10);
         part.setSize(5.0F);
         part.setMotion(-dirVec.f_82479_, (double)0.0F, -dirVec.f_82481_);
         world.m_6493_(part, true, posX + x, posY + 0.3, posZ + z, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
