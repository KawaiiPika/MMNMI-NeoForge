package xyz.pixelatedw.mineminenomi.particles.effects.goro;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.config.ClientConfig;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class LightningExplosionParticleEffect extends ParticleEffect<ParticleEffect.NoDetails> {
   private int explosionSize;

   public LightningExplosionParticleEffect() {
      this(2);
   }

   public LightningExplosionParticleEffect(int explosionSize) {
      this.explosionSize = explosionSize;
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, ParticleEffect.NoDetails details) {
      ParticleType<SimpleParticleData> goro_particle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO.get() : (ParticleType)ModParticleTypes.GORO_YELLOW.get();
      ParticleType<SimpleParticleData> goro2_particle = ClientConfig.isGoroBlue() ? (ParticleType)ModParticleTypes.GORO2.get() : (ParticleType)ModParticleTypes.GORO2_YELLOW.get();

      for(int i = 0; i < this.explosionSize * 2; ++i) {
         double x = posX + WyHelper.randomWithRange(-this.explosionSize / 2, this.explosionSize / 2) + WyHelper.randomDouble();
         double y = posY + WyHelper.randomDouble();
         double z = posZ + WyHelper.randomWithRange(-this.explosionSize / 2, this.explosionSize / 2) + WyHelper.randomDouble();
         double motionX = WyHelper.randomWithRange(-3, 3) + WyHelper.randomDouble();
         double motionY = WyHelper.randomWithRange(-3, 3) + WyHelper.randomDouble();
         double motionZ = WyHelper.randomWithRange(-3, 3) + WyHelper.randomDouble();
         double middlePoint = (double)0.5F / ((double)5.0F / (double)this.explosionSize + 0.1);
         middlePoint *= WyHelper.randomDouble() * (double)2.0F + (double)0.3F;
         motionX *= middlePoint / (double)2.0F;
         motionY *= middlePoint / (double)2.0F;
         motionZ *= middlePoint / (double)2.0F;
         float scale = (float)((double)1.0F + WyHelper.randomWithRange(2, 5));
         ParticleType<SimpleParticleData> particle = goro_particle;
         if (i % 2 == 0) {
            particle = goro2_particle;
         }

         SimpleParticleData data = new SimpleParticleData(particle);
         data.setLife(30);
         data.setSize(scale);
         data.setMotion(motionX, motionY, motionZ);
         world.m_6493_(data, true, x, y + (double)1.0F, z, (double)0.0F, (double)0.0F, (double)0.0F);
         world.m_6493_(ParticleTypes.f_123759_, true, entity.m_20185_(), entity.m_20186_() + (double)1.0F, entity.m_20189_(), (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }
}
