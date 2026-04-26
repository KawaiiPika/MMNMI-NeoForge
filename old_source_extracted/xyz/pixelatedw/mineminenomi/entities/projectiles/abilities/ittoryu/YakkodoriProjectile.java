package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.ittoryu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class YakkodoriProjectile extends NuProjectileEntity {
   public YakkodoriProjectile(EntityType<? extends YakkodoriProjectile> type, Level world) {
      super(type, world);
   }

   public YakkodoriProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.YAKKODORI.get(), world, player, ability);
      this.setDamage(15.0F);
      this.setMaxLife(40);
      this.setEntityCollisionSize((double)0.5F, (double)3.5F, (double)0.5F);
      this.addBlockHitEvent(100, this::blockHitEvent);
   }

   private void blockHitEvent(BlockHitResult result) {
      BlockPos hit = result.m_82425_();
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)hit.m_123341_(), (double)hit.m_123342_(), (double)hit.m_123343_(), 1.0F);
         explosion.setStaticDamage(5.0F);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), new CommonExplosionParticleEffect.Details((double)2.0F));
         explosion.m_46061_();
      });
   }
}
