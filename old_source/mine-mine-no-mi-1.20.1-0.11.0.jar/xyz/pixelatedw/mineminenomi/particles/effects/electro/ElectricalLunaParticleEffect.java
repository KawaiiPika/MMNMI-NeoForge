package xyz.pixelatedw.mineminenomi.particles.effects.electro;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElectricalLunaParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 20; ++i) {
         double offsetX = WyHelper.randomDouble();
         double offsetY = WyHelper.randomDouble();
         double offsetZ = WyHelper.randomDouble();
         SimpleParticleData data;
         if (i % 2 == 0) {
            data = new SimpleParticleData((ParticleType)ModParticleTypes.GORO2.get());
         } else {
            data = new SimpleParticleData((ParticleType)ModParticleTypes.GORO.get());
         }

         data.setLife(5);
         data.setSize(7.0F);
         data.setColor(1.0F, 1.0F, 1.0F, 0.5F);
         data.setMotion((double)0.0F, WyHelper.randomDouble() / (double)3.0F, (double)0.0F);
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
