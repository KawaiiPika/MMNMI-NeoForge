package xyz.pixelatedw.mineminenomi.particles.effects.gasu;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KoroParticleEffect extends ParticleEffect<Details> {
   public KoroParticleEffect() {
      super(Details::new);
   }

   public void spawn(Entity entity, Level world, double posX, double posY, double posZ, Details details) {
      for(int i = 0; i < 25; ++i) {
         double offsetX = WyHelper.randomDouble() / 1.3;
         double offsetY = WyHelper.randomDouble() / 1.3;
         double offsetZ = WyHelper.randomDouble() / 1.3;
         Vec3 targetPos = new Vec3(posX, posY, posZ);
         Vec3 dir = targetPos.m_82546_(entity.m_20182_()).m_82541_();
         int color = details.effect.m_19544_().m_19484_();
         float r = (float)(color >> 16 & 255) / 255.0F;
         float g = (float)(color >> 8 & 255) / 255.0F;
         float b = (float)(color >> 0 & 255) / 255.0F;
         SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU2.get());
         data.setLife(10);
         data.setSize(1.3F);
         data.setMotion(-dir.f_82479_ / (double)5.0F, -dir.f_82480_ / (double)5.0F, -dir.f_82481_ / (double)5.0F);
         data.setRotation(0.0F, 0.0F, 1.0F);
         data.setColor(r, g, b);
         world.m_6493_(data, true, targetPos.f_82479_ + offsetX, targetPos.f_82480_ + (double)1.0F + offsetY, targetPos.f_82481_ + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
      }

   }

   public static class Details extends ParticleEffect.Details {
      private MobEffectInstance effect;

      public void save(CompoundTag nbt) {
         this.effect.m_19555_(nbt);
      }

      public void load(CompoundTag nbt) {
         this.effect = MobEffectInstance.m_19560_(nbt);
      }

      public void setEffect(MobEffectInstance effect) {
         this.effect = effect;
      }

      public MobEffectInstance getEffect() {
         return this.effect;
      }
   }
}
