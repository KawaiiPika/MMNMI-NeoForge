package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gasu;

import java.awt.Color;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuHorizontalLightningEntity;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GastilleProjectile extends NuHorizontalLightningEntity {
   public GastilleProjectile(EntityType<? extends GastilleProjectile> type, Level world) {
      super(type, world);
   }

   public GastilleProjectile(Level world, LivingEntity thrower, IAbility ability) {
      super((EntityType)ModProjectiles.GASTILLE.get(), thrower, 64.0F, 5.5F, ability);
      float size = 0.15F;
      float damage = 30.0F;
      if (((MorphInfo)ModMorphs.SHINOKUNI.get()).isActive(thrower)) {
         this.setTravelSpeed(10.0F);
         size = 0.25F;
         damage = 50.0F;
      }

      this.setDamage(damage);
      this.setSize(size);
      this.setColor(new Color(13397929), 100);
      this.setOneTimeHit();
      this.disableExplosionKnockback();
      this.setTrailFade();
      this.setFadeTime(25);
      this.setSegmentLength(80);
      this.setPassThroughBlocks();
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
