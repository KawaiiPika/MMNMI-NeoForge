package xyz.pixelatedw.mineminenomi.particles.effects.fishkarate;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.math.EasingDirection;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunction;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MizuTaihoParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      Vec3 lookVec1 = entity.m_20154_().m_82490_((double)2.0F);
      Vec3 lookVec2 = entity.m_20154_().m_82490_((double)3.0F);
      Vec3 lookVec3 = entity.m_20154_().m_82490_((double)4.0F);
      SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.DOUBLE_CIRCLE.get());
      data.setFunction(EasingFunction.ELASTIC_IN_OUT);
      data.setEaseDirection(EasingDirection.NEGATIVE);
      data.setLookVec(entity.m_146909_(), entity.m_146908_());
      data.setColor(0.2F, 0.7F, 0.7F, 0.75F);
      data.setLife(30);
      data.setSize(30.0F);
      world.m_6493_(data, true, lookVec3.f_82479_ + posX, lookVec3.f_82480_ + posY, lookVec3.f_82481_ + posZ, (double)0.0F, (double)0.0F, (double)0.0F);
      data.setLife(20);
      data.setSize(20.0F);
      world.m_6493_(data, true, lookVec2.f_82479_ + posX, lookVec2.f_82480_ + posY, lookVec2.f_82481_ + posZ, (double)0.0F, (double)0.0F, (double)0.0F);
      data.setLife(10);
      data.setSize(10.0F);
      world.m_6493_(data, true, lookVec1.f_82479_ + posX, lookVec1.f_82480_ + posY, lookVec1.f_82481_ + posZ, (double)0.0F, (double)0.0F, (double)0.0F);
   }
}
