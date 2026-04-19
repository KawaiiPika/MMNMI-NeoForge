package xyz.pixelatedw.mineminenomi.particles.effects.doku;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ChloroBallParticleEffect extends ParticleEffect<DokuParticleEffectDetails> {
   public ChloroBallParticleEffect() {
      super(DokuParticleEffectDetails::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, DokuParticleEffectDetails details) {
      for(int i = 0; i < 12; ++i) {
         double motionX = WyHelper.randomWithRange(-3, 3) + WyHelper.randomDouble();
         double motionY = WyHelper.randomWithRange(-3, 3) + WyHelper.randomDouble();
         double motionZ = WyHelper.randomWithRange(-3, 3) + WyHelper.randomDouble();
         double middlePoint = (double)0.25F;
         middlePoint *= WyHelper.randomDouble() * (double)2.0F + (double)0.3F;
         motionX *= middlePoint / (double)2.0F;
         motionY *= middlePoint / (double)2.0F;
         motionZ *= middlePoint / (double)2.0F;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.DOKU.get());
         if (details.hasVenomDemon()) {
            data.setColor(1.0F, 0.0F, 0.0F);
         }

         data.setLife(8);
         data.setSize(0.8F);
         data.setMotion(motionX, motionY, motionZ);
         entity.m_9236_().m_6493_(data, true, posX, posY, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
