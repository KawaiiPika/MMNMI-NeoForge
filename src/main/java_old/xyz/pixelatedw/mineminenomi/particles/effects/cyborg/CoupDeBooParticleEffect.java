package xyz.pixelatedw.mineminenomi.particles.effects.cyborg;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CoupDeBooParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 200; ++i) {
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU.get());
         data.setColor(0.48F, 0.36F, 0.0F);
         data.setLife(30);
         data.setSize(3.0F);
         data.setMotion(WyHelper.randomDouble() / (double)4.0F, WyHelper.randomDouble(), WyHelper.randomDouble() / (double)4.0F);
         double offsetX = WyHelper.randomWithRange(-3, 3) * WyHelper.randomDouble();
         double offsetY = WyHelper.randomWithRange(-2, 2) * WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-3, 3) * WyHelper.randomDouble();
         world.m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
