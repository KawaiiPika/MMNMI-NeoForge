package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.moku;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class WhiteBlowRushProjectile extends NuProjectileEntity {
   public WhiteBlowRushProjectile(EntityType<? extends WhiteBlowRushProjectile> type, Level world) {
      super(type, world);
   }

   public WhiteBlowRushProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.WHITE_BLOW.get(), world, player, ability);
      this.setDamage(2.0F);
      this.setMaxLife(24);
      this.addEntityHitEvent(100, this::onEntityHit);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onEntityHit(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.SMOKE.get(), 200, 0));
      }

   }

   private void onTickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 10; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MOKU.get());
            data.setLife(10);
            data.setSize(1.3F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }
      }

   }
}
