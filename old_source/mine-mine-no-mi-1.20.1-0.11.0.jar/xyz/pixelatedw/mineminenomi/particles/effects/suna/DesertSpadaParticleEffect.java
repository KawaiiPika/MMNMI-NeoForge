package xyz.pixelatedw.mineminenomi.particles.effects.suna;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DesertSpadaParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 70; ++i) {
         double offsetX = WyHelper.randomDouble() * (double)0.5F;
         double offsetZ = WyHelper.randomDouble() * (double)0.5F;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.SUNA2.get());
         data.setLife(20);
         data.setSize(1.5F);
         data.setMotion(entity.m_20184_().f_82479_, entity.m_20184_().f_82480_ + (double)0.25F + Math.abs(WyHelper.randomDouble()) / (double)7.0F, entity.m_20184_().f_82481_);
         world.m_6493_(data, true, posX + offsetX, posY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
         data.setLife(60);
         data.setMotion((double)0.0F, 0.05 + Math.abs(WyHelper.randomDouble()) / (double)8.0F, (double)0.0F);
         world.m_6493_(data, true, posX + offsetX, posY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
