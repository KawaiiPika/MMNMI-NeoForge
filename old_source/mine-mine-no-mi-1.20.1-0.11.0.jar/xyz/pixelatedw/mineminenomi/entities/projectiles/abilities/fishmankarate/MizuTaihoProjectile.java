package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.fishmankarate;

import net.minecraft.core.particles.ParticleTypes;
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
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class MizuTaihoProjectile extends NuProjectileEntity {
   public MizuTaihoProjectile(EntityType<? extends MizuTaihoProjectile> type, Level world) {
      super(type, world);
   }

   public MizuTaihoProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.MIZU_TAIHO.get(), world, player, ability);
      this.setDamage(50.0F);
      this.setPassThroughEntities();
      this.setMaxLife(30);
      this.setEntityCollisionSize((double)2.0F);
      this.addBlockHitEvent(100, this::blockHitEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void blockHitEvent(BlockHitResult hit) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)hit.m_82425_().m_123341_(), (double)hit.m_82425_().m_123342_(), (double)hit.m_82425_().m_123343_(), 3.0F);
         explosion.setStaticDamage(15.0F);
         explosion.setExplosionSound(false);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(true);
         explosion.setFireAfterExplosion(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.WATER_EXPLOSION.get());
         explosion.setDamageEntities(false);
         explosion.m_46061_();
      });
   }

   private void tickEvent() {
      if (!this.m_9236_().f_46443_) {
         if (this.f_19797_ > 2) {
            this.m_8060_(BlockHitResult.m_82426_(this.m_20182_(), this.m_6350_(), this.m_20183_()));
         }

         for(int i = 0; i < 15; ++i) {
            double offsetX = WyHelper.randomDouble() / (double)2.0F;
            double offsetY = WyHelper.randomDouble() / (double)2.0F;
            double offsetZ = WyHelper.randomDouble() / (double)2.0F;
            ((ServerLevel)this.m_9236_()).m_8767_(ParticleTypes.f_123816_, this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, -0.1);
            ((ServerLevel)this.m_9236_()).m_8767_(ParticleTypes.f_123795_, this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, 0.1);
         }
      }

   }
}
