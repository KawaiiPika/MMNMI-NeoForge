package xyz.pixelatedw.mineminenomi.particles.effects.doku;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class VenomDemonParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 3; ++i) {
         double offsetX = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
         double offsetY = WyHelper.randomWithRange(0, 2) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
         int age = (int)((double)5.0F + WyHelper.randomWithRange(0, 5));
         double motionY = 0.015 + Math.abs(WyHelper.randomDouble() / (double)8.0F);
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.DOKU.get());
         data.setLife(age);
         data.setSize(3.0F);
         data.setColor(1.0F, 0.0F, 0.0F);
         data.setMotion((double)0.0F, motionY, (double)0.0F);
         entity.m_9236_().m_6493_(data, true, posX + offsetX, posY + (double)0.25F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
