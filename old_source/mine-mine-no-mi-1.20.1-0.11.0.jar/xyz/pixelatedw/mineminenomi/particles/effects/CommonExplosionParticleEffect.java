package xyz.pixelatedw.mineminenomi.particles.effects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class CommonExplosionParticleEffect extends ParticleEffect<Details> {
   public static final Details EXPLOSION1 = new Details((double)1.0F);
   public static final Details EXPLOSION2 = new Details((double)2.0F);
   public static final Details EXPLOSION4 = new Details((double)4.0F);
   public static final Details EXPLOSION5 = new Details((double)5.0F);
   public static final Details EXPLOSION6 = new Details((double)6.0F);
   public static final Details EXPLOSION8 = new Details((double)8.0F);
   public static final Details EXPLOSION10 = new Details((double)10.0F);

   public CommonExplosionParticleEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      for(int i = 0; (double)i < details.explosionSize * (double)2.0F; ++i) {
         double x = posX + WyHelper.randomWithRange(-details.explosionSize / (double)2.0F, details.explosionSize / (double)2.0F) + WyHelper.randomDouble();
         double y = posY + WyHelper.randomDouble();
         double z = posZ + WyHelper.randomWithRange(-details.explosionSize / (double)2.0F, details.explosionSize / (double)2.0F) + WyHelper.randomDouble();
         double motionX = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
         double motionY = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
         double motionZ = WyHelper.randomWithRange(-2, 2) + WyHelper.randomDouble();
         double middlePoint = (double)0.5F / ((double)5.0F / details.explosionSize + 0.1);
         middlePoint *= WyHelper.randomDouble() * (double)2.0F + (double)0.3F;
         double var10000 = motionX * (middlePoint / (double)2.0F);
         var10000 = motionY * (middlePoint / (double)2.0F);
         var10000 = motionZ * (middlePoint / (double)2.0F);
         world.m_7106_(ParticleTypes.f_123813_, x, y + (double)1.0F, z, (double)0.0F, (double)0.0F, (double)0.0F);
         world.m_7106_(ParticleTypes.f_123759_, posX, posY + (double)1.0F, posZ, (double)0.0F, (double)0.0F, (double)0.0F);
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
