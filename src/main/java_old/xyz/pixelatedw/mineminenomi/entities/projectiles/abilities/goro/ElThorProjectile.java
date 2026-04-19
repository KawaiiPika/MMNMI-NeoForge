package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.abilities.goro.ElThorAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuVerticalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElThorProjectile extends NuVerticalLightningEntity {
   private float multiplier = 1.0F;

   public ElThorProjectile(EntityType<? extends ElThorProjectile> type, Level world) {
      super(type, world);
   }

   public ElThorProjectile(Level world, LivingEntity thrower, double posX, double posY, double posZ, int height, float maxTravelDistance, float multiplier, IAbility ability) {
      super((EntityType)ModProjectiles.EL_THOR.get(), thrower, posX, posY, posZ, height, maxTravelDistance, 24.0F, ability);
      this.multiplier = multiplier;
      this.setSegments(1);
      this.setSize(2.0F * this.multiplier);
      this.setLightningMovement(false);
      this.setColor(ElThorAbility.YELLOW_THUNDER, 100);
      this.setMaxLife(100);
      this.setDamage(50.0F * this.multiplier);
      this.setTargetTimeToReset(60);
      this.setPassThroughBlocks();
      this.addBlockHitEvent(100, this::blockHitEvent);
   }

   private void blockHitEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), 10.0F * this.multiplier);
         explosion.setSmokeParticles((ParticleEffect)null);
         explosion.m_46061_();
      });
   }
}
