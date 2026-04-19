package xyz.pixelatedw.mineminenomi.particles.effects.doku;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ChloroBallCloudParticleEffect extends ParticleEffect<DokuParticleEffectDetails> {
   public ChloroBallCloudParticleEffect() {
      super(DokuParticleEffectDetails::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, DokuParticleEffectDetails details) {
      for(int i = 0; i < 32; ++i) {
         double offsetX = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
         double offsetY = WyHelper.randomWithRange(-1, 2) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.DOKU.get());
         if (details.hasVenomDemon()) {
            data.setColor(1.0F, 0.0F, 0.0F);
         }

         data.setLife(10);
         data.setSize(2.5F);
         data.setMotion((double)0.0F, 0.02, (double)0.0F);
         entity.m_9236_().m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
