package xyz.pixelatedw.mineminenomi.particles.effects.haki;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.math.EasingDirection;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class InternalDestructionBurstParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      Vec3 lookVec = entity.m_20154_().m_82490_(-1.2);
      Vec3 lookVec2 = entity.m_20154_().m_82490_(-1.6);
      SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.SIMPLE_CIRCLE.get());
      part.setFunction(EasingFunction.ELASTIC_OUT);
      part.setEaseDirection(EasingDirection.POSITIVE);
      part.setLookVec(entity.m_146909_(), entity.m_146908_());
      part.setColor(0.9F, 0.7F, 0.15F, 0.25F);
      Vec3 moveVec = entity.m_20154_().m_82541_().m_82548_().m_82542_(0.08, 0.08, 0.08);
      part.setLife(10);
      part.setSize(10.0F);
      part.setMotion(moveVec.f_82479_, moveVec.f_82480_, moveVec.f_82481_);
      world.m_6493_(part, true, lookVec.f_82479_ + posX, lookVec.f_82480_ + posY, lookVec.f_82481_ + posZ, (double)0.0F, (double)0.0F, (double)0.0F);
      part = new SimpleParticleData((ParticleType)ModParticleTypes.DOUBLE_CIRCLE.get());
      part.setFunction(EasingFunction.ELASTIC_OUT);
      part.setEaseDirection(EasingDirection.POSITIVE);
      part.setLookVec(entity.m_146909_(), entity.m_146908_());
      part.setColor(0.9F, 0.7F, 0.15F, 0.25F);
      part.setLife(15);
      part.setSize(4.0F);
      world.m_6493_(part, true, lookVec2.f_82479_ + posX, lookVec2.f_82480_ + posY, lookVec2.f_82481_ + posZ, (double)0.0F, (double)0.0F, (double)0.0F);
   }
}
