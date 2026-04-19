package xyz.pixelatedw.mineminenomi.particles.effects.doku;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DokuGumoParticleEffect extends ParticleEffect<DokuParticleEffectDetails> {
   public DokuGumoParticleEffect() {
      super(DokuParticleEffectDetails::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, DokuParticleEffectDetails details) {
      boolean hasVenomDemon = details.hasVenomDemon();
      int width = hasVenomDemon ? 5 : 3;
      int height = hasVenomDemon ? 6 : 3;

      for(int i = 0; i < (hasVenomDemon ? 50 : 30); ++i) {
         double offsetX = WyHelper.randomWithRange(-width, width) + WyHelper.randomDouble();
         double offsetY = WyHelper.randomWithRange(-height, height) + WyHelper.randomDouble();
         double offsetZ = WyHelper.randomWithRange(-width, width) + WyHelper.randomDouble();
         int age = (int)((double)5.0F + WyHelper.randomWithRange(0, 5));
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.DOKU.get());
         if (hasVenomDemon) {
            data.setColor(1.0F, 0.0F, 0.0F);
         }

         data.setLife(age);
         data.setSize(2.0F);
         entity.m_9236_().m_6493_(data, true, posX + offsetX, posY + offsetY, posZ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
