package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.zushi;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class MokoProjectile extends NuProjectileEntity {
   public MokoProjectile(EntityType<? extends MokoProjectile> type, Level world) {
      super(type, world);
   }

   public MokoProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.MOKO.get(), world, player, ability);
      this.setDamage(10.0F);
      this.setPassThroughEntities();
      this.setArmorPiercing(1.0F);
      this.setUnavoidable();
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         AbilityHelper.setDeltaMovement(target, (double)0.0F, (double)-5.0F, (double)0.0F);
         target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 100, 10));
      }

   }

   private void onTickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 25; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.GASU.get());
            data.setLife(10);
            data.setSize(1.0F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }
      }

   }
}
