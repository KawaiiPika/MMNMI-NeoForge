package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.beta;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class StickyProjectile extends NuProjectileEntity {
   private boolean causeExplosion = false;

   public StickyProjectile(EntityType<? extends StickyProjectile> type, Level world) {
      super(type, world);
   }

   public StickyProjectile(Level world, LivingEntity player, Ability ability) {
      super((EntityType)ModProjectiles.STICKY_PROJECTILE.get(), world, player, ability);
      this.setDamage(2.0F);
      this.setMaxLife(20);
      this.addEntityHitEvent(100, this::entityHitEvent);
      this.addBlockHitEvent(100, this::blockHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void tickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 4; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)5.0F;
            double offsetY = WyHelper.randomDouble() / (double)5.0F;
            double offsetZ = WyHelper.randomDouble() / (double)5.0F;
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.BETA.get());
            data.setLife(10);
            data.setSize(1.3F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + (double)0.25F + offsetY, this.m_20189_() + offsetZ);
         }

         if (this.causeExplosion) {
            for(int i = 0; i < 2; ++i) {
               double offsetX = WyHelper.randomDouble() / (double)2.0F;
               double offsetY = WyHelper.randomDouble() / (double)2.0F;
               double offsetZ = WyHelper.randomDouble() / (double)2.0F;
               SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.MERA.get());
               data.setLife(7);
               data.setSize(1.2F);
               WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
            }
         }
      }

   }

   private void entityHitEvent(EntityHitResult hit) {
      if (!this.causeExplosion) {
         Entity var3 = hit.m_82443_();
         if (var3 instanceof LivingEntity) {
            LivingEntity living = (LivingEntity)var3;
            living.m_7292_(new MobEffectInstance((MobEffect)ModEffects.STICKY.get(), 300, 0, false, false));
         }
      }

      this.defaultOnHitBlock(hit);
   }

   private void blockHitEvent(BlockHitResult result) {
      if (this.causeExplosion) {
         ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
            AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), 2.0F);
            explosion.setStaticDamage(10.0F);
            explosion.setExplosionSound(true);
            explosion.setDamageOwner(false);
            explosion.setDestroyBlocks(true);
            explosion.setFireAfterExplosion(true);
            explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
            explosion.setDamageEntities(true);
            explosion.m_46061_();
         });
      } else {
         for(int i = 0; i < 20; ++i) {
            int offsetX = (int)WyHelper.randomWithRange(-2, 2);
            int offsetZ = (int)WyHelper.randomWithRange(-2, 2);
            BlockPos location = this.m_20183_().m_7918_(offsetX, 0, offsetZ);
            if (this.m_9236_().m_8055_(location.m_7495_()).m_60815_()) {
               NuWorld.setBlockState((Entity)this.getOwner(), location, ((Block)ModBlocks.MUCUS.get()).m_49966_(), 2, DefaultProtectionRules.AIR_FOLIAGE);
            }
         }
      }

   }

   public void setCauseExplosion() {
      this.causeExplosion = true;
   }
}
