package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MobsHelper;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MizuShuryudanProjectile extends NuProjectileEntity {
   private Optional<LivingEntity> target;

   public MizuShuryudanProjectile(EntityType<? extends MizuShuryudanProjectile> type, Level world) {
      super(type, world);
   }

   public MizuShuryudanProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.MIZU_SHURYUDAN.get(), world, player, ability);
      this.setDamage(7.0F);
      this.setMaxLife(400);
      this.addEntityHitEvent(100, this::entityHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void entityHitEvent(EntityHitResult hit) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 3.0F);
         explosion.setExplosionSound(false);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(false);
         explosion.setFireAfterExplosion(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.WATER_EXPLOSION.get());
         explosion.setDamageEntities(false);
         explosion.m_46061_();
      });
   }

   public void setTarget(Optional<LivingEntity> target) {
      this.target = target;
   }

   private void tickEvent() {
      if (this.target != null && this.target.isPresent() && ModEntityPredicates.IS_ALIVE_AND_SURVIVAL.test((Entity)this.target.get())) {
         Vec3 dist = this.m_20182_().m_82546_(((LivingEntity)this.target.get()).m_20182_().m_82520_((double)0.0F, (double)1.0F, (double)0.0F)).m_82541_().m_82542_(0.4, (double)1.0F, 0.4);
         AbilityHelper.setDeltaMovement(this, -dist.f_82479_, -dist.f_82480_, -dist.f_82481_);
      } else {
         if (this.getOwner() != null) {
            List<LivingEntity> list = WyHelper.<LivingEntity>getNearbyLiving(this.m_20182_(), this.m_9236_(), (double)8.0F, ModEntityPredicates.getEnemyFactions(this.getOwner()));
            list.remove(this.getOwner());
            list.sort(MobsHelper.ENTITY_THREAT);
            if (list.size() > 0) {
               this.target = list.stream().findAny();
            }
         }

         if (this.f_19797_ % 5 == 0) {
            AbilityHelper.setDeltaMovement(this, this.m_20184_().m_82490_(0.7));
         }
      }

      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 1; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            WyHelper.spawnParticles(ParticleTypes.f_123816_, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }
      }

   }
}
