package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.artofweather;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.abilities.artofweather.ArtOfWeatherHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ThunderLanceProjectile extends NuHorizontalLightningEntity {
   public ThunderLanceProjectile(EntityType<? extends ThunderLanceProjectile> type, Level world) {
      super(type, world);
   }

   public ThunderLanceProjectile(Level world, LivingEntity thrower, IAbility ability) {
      super((EntityType)ModProjectiles.THUNDER_LANCE.get(), thrower, 80.0F, 12.0F, ability);
      this.setTargetTimeToReset(20);
      this.setDamage(60.0F);
      this.disableExplosionKnockback();
      this.setColor(ArtOfWeatherHelper.AOW_LIGHTNING_COLOR, 100);
      this.setAngle(20);
      this.setBranches(2);
      this.setSegments(5);
      this.addBlockHitEvent(100, this::blockHitEvent);
   }

   private void blockHitEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), 1.0F);
         explosion.setDestroyBlocks(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
         explosion.m_46061_();
      });
   }
}
