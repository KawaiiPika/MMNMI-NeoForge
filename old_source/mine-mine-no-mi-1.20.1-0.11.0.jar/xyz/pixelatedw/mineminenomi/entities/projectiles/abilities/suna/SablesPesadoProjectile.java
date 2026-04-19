package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.suna;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SablesPesadoProjectile extends NuProjectileEntity {
   public SablesPesadoProjectile(EntityType<? extends SablesPesadoProjectile> type, Level world) {
      super(type, world);
   }

   public SablesPesadoProjectile(Level world, LivingEntity entity, IAbility ability) {
      super((EntityType)ModProjectiles.SABLES_PESADO.get(), world, entity, ability);
      this.setDamage(50.0F);
      this.setMaxLife(50);
      this.setPhysical();
      this.setArmorPiercing(0.5F);
      this.addBlockHitEvent(100, this::blockImpactEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void blockImpactEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), result.m_82450_().m_7096_(), result.m_82450_().m_7098_(), result.m_82450_().m_7094_(), 6.0F);
         explosion.setStaticDamage(35.0F);
         explosion.setExplosionSound(true);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(true);
         explosion.setFireAfterExplosion(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION8);
         explosion.setDamageEntities(true);
         explosion.m_46061_();
      });
   }

   private void tickEvent() {
      if (!this.m_9236_().f_46443_) {
         for(int i = 0; i < 20; ++i) {
            double offsetX = WyHelper.randomDouble();
            double offsetY = WyHelper.randomDouble();
            double offsetZ = WyHelper.randomDouble();
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.SUNA.get());
            data.setLife(6);
            data.setSize(1.3F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }

         for(int i = 0; i < 2; ++i) {
            double offsetX = WyHelper.randomDouble();
            double offsetY = WyHelper.randomDouble();
            double offsetZ = WyHelper.randomDouble();
            SimpleParticleData data = new SimpleParticleData((ParticleType)ModParticleTypes.SUNA2.get());
            data.setLife(4);
            data.setSize(1.2F);
            WyHelper.spawnParticles(data, (ServerLevel)this.m_9236_(), this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ);
         }
      }

   }
}
