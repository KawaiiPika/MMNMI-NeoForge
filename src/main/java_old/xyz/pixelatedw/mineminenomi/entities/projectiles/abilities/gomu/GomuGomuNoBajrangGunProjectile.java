package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gomu;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GomuGomuNoBajrangGunProjectile extends NuHorizontalLightningEntity {
   public GomuGomuNoBajrangGunProjectile(EntityType<? extends GomuGomuNoBajrangGunProjectile> type, Level world) {
      super(type, world);
   }

   public GomuGomuNoBajrangGunProjectile(Level world, LivingEntity entity, Ability ability) {
      super((EntityType)ModProjectiles.GOMU_GOMU_NO_BAJRANG_GUN.get(), entity, 120.0F, 12.0F, ability);
      this.setRetracting();
      this.setSize(0.05F);
      this.setOneTimeHit();
      this.setDepth(1);
      this.setDamage(80.0F);
      this.setPassThroughBlocks();
      this.setPassThroughEntities();
      this.setFist();
      this.setEntityCollisionSize((double)6.0F, (double)3.0F, (double)6.0F);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 12.0F);
         explosion.setExplosionSound(true);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(true);
         explosion.setFireAfterExplosion(false);
         explosion.setSmokeParticles((ParticleEffect)null);
         explosion.setDamageEntities(false);
         explosion.m_46061_();
      });
   }
}
