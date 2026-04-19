package xyz.pixelatedw.mineminenomi.entities.projectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CannonBallProjectile extends NuProjectileEntity {
   public CannonBallProjectile(EntityType<? extends CannonBallProjectile> type, Level world) {
      super(type, world);
   }

   public CannonBallProjectile(Level world, LivingEntity thrower) {
      this(world, thrower, SourceElement.NONE, SourceHakiNature.IMBUING, SourceType.PROJECTILE, SourceType.PHYSICAL, SourceType.BLUNT);
   }

   public CannonBallProjectile(Level world, LivingEntity thrower, SourceElement element, SourceHakiNature hakiNature, SourceType... types) {
      super((EntityType)ModProjectiles.CANNON_BALL.get(), world, thrower, (IAbility)null, element, hakiNature, types);
      this.setDamage(14.0F);
      this.setMaxLife(40);
      this.setGravity(0.01F);
      this.setPhysical();
      this.setEntityCollisionSize((double)1.5F, (double)1.5F, (double)1.5F);
      this.addBlockHitEvent(100, this::blockHitEvent);
   }

   public CannonBallProjectile(Level world, LivingEntity thrower, IAbility ability) {
      this(world, thrower, ability.getCore().getSourceElement(), ability.getCore().getSourceHakiNature(), ability.getCore().getSourceTypesArray());
   }

   private void blockHitEvent(BlockHitResult hit) {
      AbilityExplosion explosion = new AbilityExplosion(this.getOwner(), (IAbility)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), 2.0F);
      explosion.setStaticDamage(8.0F);
      explosion.setDestroyBlocks(true);
      explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
      explosion.m_46061_();
   }
}
