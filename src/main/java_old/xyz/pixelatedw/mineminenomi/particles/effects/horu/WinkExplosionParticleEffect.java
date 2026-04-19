package xyz.pixelatedw.mineminenomi.particles.effects.horu;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class WinkExplosionParticleEffect extends ParticleEffect<Details> {
   public static final Details EXPLOSION2 = new Details((double)2.0F);

   public WinkExplosionParticleEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      for(int i = 0; (double)i < details.explosionSize * (double)2.0F; ++i) {
         double x = posX + WyHelper.randomWithRange(-details.explosionSize / (double)2.0F, details.explosionSize / (double)2.0F) + WyHelper.randomDouble();
         double y = posY + WyHelper.randomDouble();
         double z = posZ + WyHelper.randomWithRange(-details.explosionSize / (double)2.0F, details.explosionSize / (double)2.0F) + WyHelper.randomDouble();
         double motionX = WyHelper.randomWithRange(-3, 3) + WyHelper.randomDouble();
         double motionY = WyHelper.randomWithRange(-3, 3) + WyHelper.randomDouble();
         double motionZ = WyHelper.randomWithRange(-3, 3) + WyHelper.randomDouble();
         double middlePoint = (double)0.5F / ((double)5.0F / details.explosionSize + 0.1);
         middlePoint *= WyHelper.randomDouble() * (double)2.0F + (double)0.3F;
         motionX *= middlePoint / (double)2.0F;
         motionY *= middlePoint / (double)2.0F;
         motionZ *= middlePoint / (double)2.0F;
         SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.HORU.get());
         part.setLife(20);
         part.setSize(6.0F);
         part.setMotion(motionX, motionY, motionZ);
         world.m_6493_(part, true, x, y + (double)1.0F, z, (double)0.0F, (double)0.0F, (double)0.0F);
         world.m_6493_(ParticleTypes.f_123813_, true, x, y, z, (double)0.0F, (double)0.0F, (double)0.0F);
         world.m_6493_(ParticleTypes.f_123759_, true, posX, posY + (double)1.0F, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }

   public static class Details extends ParticleEffect.Details {
      private double explosionSize = (double)1.0F;

      public Details() {
      }

      public Details(double size) {
         this.explosionSize = size;
      }

      public void save(CompoundTag nbt) {
         nbt.m_128347_("explosionSize", this.explosionSize);
      }

      public void load(CompoundTag nbt) {
         this.explosionSize = nbt.m_128459_("explosionSize");
      }
   }
}
