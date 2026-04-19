package xyz.pixelatedw.mineminenomi.particles.effects.hana;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BloomParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 15; ++i) {
         double offsetX = WyHelper.randomDouble() / (double)2.0F;
         double offsetY = WyHelper.randomDouble() / (double)2.0F;
         double offsetZ = WyHelper.randomDouble() / (double)2.0F;
         double motionX = WyHelper.randomDouble();
         double motionY = WyHelper.randomDouble();
         double motionZ = WyHelper.randomDouble();
         double middlePoint = 0.15;
         middlePoint *= WyHelper.randomDouble() * (double)2.0F + (double)0.3F;
         motionX *= middlePoint / (double)5.0F;
         motionY *= middlePoint / (double)8.0F;
         motionZ *= middlePoint / (double)5.0F;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.HANA.get());
         data.setLife(20);
         data.setSize(3.0F);
         data.setMotion(Math.sin(motionX), motionY - 0.015, Math.sin(motionZ));
         world.m_6493_(data, true, posX + offsetX, posY + offsetY + (double)1.25F, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
