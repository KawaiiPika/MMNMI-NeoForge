package xyz.pixelatedw.mineminenomi.particles.effects.sabi;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RustBreakParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 55; ++i) {
         double offsetX = (double)0.5F + WyHelper.randomDouble() / (double)2.0F;
         double offsetY = (double)0.5F + WyHelper.randomDouble() / (double)2.0F;
         double offsetZ = (double)0.5F + WyHelper.randomDouble() / (double)2.0F;
         double motionX = world.m_213780_().m_188583_() * 0.02;
         double motionY = world.m_213780_().m_188583_() * 0.02;
         double motionZ = world.m_213780_().m_188583_() * 0.02;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.RUST.get());
         data.setMotion(motionX, motionY, motionZ);
         data.setLife(30);
         data.setSize(4.0F);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
         world.m_6493_(ParticleTypes.f_123759_, true, posX, posY, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
