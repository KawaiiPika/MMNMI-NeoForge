package xyz.pixelatedw.mineminenomi.particles.effects.blackleg;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ExtraHachiDiableParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 4; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)3.0F;
         double offsetY = WyHelper.randomDouble() / (double)3.0F;
         double offsetZ = WyHelper.randomDouble() / (double)3.0F;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
         data.setLife(10);
         data.setSize(1.0F);
         data.setMotion(offsetX / (double)12.0F, offsetY / (double)5.0F + 0.05, offsetZ / (double)12.0F);
         world.m_6493_(data, true, posX + offsetX, posY + (double)1.0F + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
