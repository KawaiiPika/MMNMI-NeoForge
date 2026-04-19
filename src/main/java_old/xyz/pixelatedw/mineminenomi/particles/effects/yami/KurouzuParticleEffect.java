package xyz.pixelatedw.mineminenomi.particles.effects.yami;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KurouzuParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 15; ++i) {
         double offsetX = WyHelper.randomDouble();
         double offsetY = WyHelper.randomDouble();
         double offsetZ = WyHelper.randomDouble();
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.DARKNESS.get());
         data.setLife(1);
         data.setSize(15.0F);
         world.m_6493_(data, true, posX + (double)0.5F + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
