package xyz.pixelatedw.mineminenomi.particles.effects.mera;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HibashiraParticleEffect extends ParticleEffect<Details> {
   public static final Details NO_DETAILS = new Details();

   public HibashiraParticleEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      double t = (double)0.0F;
      double size = (double)2.5F * (double)details.getRadiusScale();
      RandomSource rand = world.f_46441_;

      while(t < (double)1.0F) {
         ++t;

         for(double theta = (double)0.0F; theta <= (Math.PI * 2D); theta += Math.PI / ((double)8.0F * (double)details.getRadiusScale())) {
            double x = t * Math.cos(theta);
            double z = t * Math.sin(theta);
            double motionX = Math.sin(theta) / (double)10.0F * (double)details.getMotionScale();
            double motionY = 0.3 + rand.m_188500_() / (double)10.0F * (double)details.getMotionScale();
            double motionZ = -Math.cos(theta) / (double)10.0F * (double)details.getMotionScale();
            SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
            part.setLife((int)WyHelper.randomWithRange(40, 50));
            part.setSize((float)WyHelper.randomWithRange(7, 10) * details.getDensityScale());
            float c = WyHelper.colorTolerance(0.7F);
            part.setColor(c, c, c, 0.5F);
            part.setMotion(motionX, motionY, motionZ);
            world.m_6493_(part, true, posX + x * size + WyHelper.randomDouble() / (double)2.0F, posY - (double)10.0F, posZ + z * size + WyHelper.randomDouble() / (double)2.0F, (double)0.0F, (double)0.0F, (double)0.0F);
            world.m_6493_(part, true, posX + x * size + WyHelper.randomDouble() / (double)2.0F, posY - (double)5.0F, posZ + z * size + WyHelper.randomDouble() / (double)2.0F, (double)0.0F, (double)0.0F, (double)0.0F);
            world.m_6493_(part, true, posX + x * size + WyHelper.randomDouble() / (double)2.0F, posY, posZ + z * size + WyHelper.randomDouble() / (double)2.0F, (double)0.0F, (double)0.0F, (double)0.0F);
            world.m_6493_(part, true, posX + x * size + WyHelper.randomDouble() / (double)2.0F, posY + (double)5.0F + WyHelper.randomWithRange(-2, 0), posZ + z * size + WyHelper.randomDouble() / (double)2.0F, (double)0.0F, (double)0.0F, (double)0.0F);
            world.m_6493_(part, true, posX + x * size + WyHelper.randomDouble() / (double)2.0F, posY + (double)10.0F + WyHelper.randomWithRange(-2, 0), posZ + z * size + WyHelper.randomDouble() / (double)2.0F, (double)0.0F, (double)0.0F, (double)0.0F);
         }
      }

   }

   public static class Details extends ParticleEffect.Details {
      private float radiusScale = 1.0F;
      private float densityScale = 1.0F;
      private float motionScale = 1.0F;

      public Details() {
      }

      public Details(float radiusScale, float densityScale, float motionScale) {
         this.radiusScale = radiusScale;
         this.densityScale = densityScale;
         this.motionScale = motionScale;
      }

      public void save(CompoundTag nbt) {
         nbt.m_128350_("radiusScale", this.radiusScale);
         nbt.m_128350_("densityScale", this.densityScale);
         nbt.m_128350_("motionScale", this.motionScale);
      }

      public void load(CompoundTag nbt) {
         this.radiusScale = nbt.m_128457_("radiusScale");
         this.densityScale = nbt.m_128457_("densityScale");
         this.motionScale = nbt.m_128457_("motionScale");
      }

      public float getRadiusScale() {
         return this.radiusScale;
      }

      public float getDensityScale() {
         return this.densityScale;
      }

      public float getMotionScale() {
         return this.motionScale;
      }
   }
}
