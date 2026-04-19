package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.pika;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.abilities.goro.ElThorAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class AmaterasuProjectile extends NuHorizontalLightningEntity {
   public AmaterasuProjectile(EntityType<? extends AmaterasuProjectile> type, Level world) {
      super(type, world);
   }

   public AmaterasuProjectile(Level world, LivingEntity thrower, IAbility ability) {
      super((EntityType)ModProjectiles.AMATERASU.get(), thrower, 128.0F, 12.0F, ability);
      this.setMaxLife(20);
      this.setDamage(70.0F);
      this.setSize(0.25F);
      this.setColor(ElThorAbility.YELLOW_THUNDER, 100);
      this.setAngle(100);
      this.setOneTimeHit();
      this.disableExplosionKnockback();
      this.setBranches(1);
      this.setSegments(1);
      this.addBlockHitEvent(100, this::blockHitEvent);
   }

   private void blockHitEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), 5.0F);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION5);
         explosion.m_46061_();
      });
   }
}
