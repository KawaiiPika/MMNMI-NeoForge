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

public class MizuOsuParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      Vec3 lookVec1 = entity.m_20154_();
      SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.SIMPLE_CIRCLE.get());
      data.setFunction(EasingFunction.SINE_IN_OUT);
      data.setEaseDirection(EasingDirection.POSITIVE);
      data.setEaseStrength(10.0F);
      data.setLookVec(entity.m_146909_(), entity.m_146908_());
      data.setColor(0.2F, 0.7F, 0.7F, 0.75F);
      data.setLife(12);
      data.setSize(1.0F);
      data.setMotion(lookVec1.f_82479_ / (double)9.0F, (double)0.0F, lookVec1.f_82481_ / (double)9.0F);
      world.m_6493_(data, true, lookVec1.f_82479_ + posX, lookVec1.f_82480_ + posY, lookVec1.f_82481_ + posZ, (double)0.0F, (double)0.0F, (double)0.0F);
      data.setLife(10);
      data.setSize(2.0F);
      data.setMotion(lookVec1.f_82479_ / (double)7.0F, (double)0.0F, lookVec1.f_82481_ / (double)7.0F);
      world.m_6493_(data, true, lookVec1.f_82479_ + posX, lookVec1.f_82480_ + posY, lookVec1.f_82481_ + posZ, (double)0.0F, (double)0.0F, (double)0.0F);
      data.setLife(8);
      data.setSize(3.0F);
      data.setMotion(lookVec1.f_82479_ / (double)5.0F, (double)0.0F, lookVec1.f_82481_ / (double)5.0F);
      world.m_6493_(data, true, lookVec1.f_82479_ + posX, lookVec1.f_82480_ + posY, lookVec1.f_82481_ + posZ, (double)0.0F, (double)0.0F, (double)0.0F);
   }
}
