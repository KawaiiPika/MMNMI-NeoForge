package xyz.pixelatedw.mineminenomi.particles.effects.suna;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DesertGirasoleParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      for(int i = 0; i < 64; ++i) {
         double offsetX = WyHelper.randomWithRange(-15, 15) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-15, 15) + WyHelper.randomDouble();
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.SUNA2.get());
         data.setLife((int)((double)80.0F + 0.2 * (double)i));
         data.setSize(4.0F);
         data.setMotion((double)0.0F, 0.3, (double)0.0F);
         world.m_6493_(data, true, posX + offsetX, posY + (double)0.5F - 0.15 * (double)i, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
