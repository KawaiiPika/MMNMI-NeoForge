package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.goro;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.abilities.goro.ElThorAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
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

public class SangoProjectile extends NuHorizontalLightningEntity {
   private float multiplier = 1.0F;

   public SangoProjectile(EntityType<? extends SangoProjectile> type, Level world) {
      super(type, world);
   }

   public SangoProjectile(Level world, LivingEntity thrower, IAbility ability) {
      super((EntityType)ModProjectiles.SANGO.get(), thrower, 80.0F, 20.0F, ability);
      float length = 80.0F;
      float damage = 20.0F;
      if (((MorphInfo)ModMorphs.VOLT_AMARU.get()).isActive(thrower)) {
         this.multiplier += 0.25F;
         length = 128.0F;
         damage = 30.0F;
      }

      float size = 0.28F * this.multiplier;
      super.offsetToHand(thrower);
      this.setFollowOwner();
      this.setFadeTime(40);
      this.setMaxLife(40);
      this.setLength(length);
      this.setDamage(damage * this.multiplier);
      this.setSize(size * this.multiplier);
      this.setColor(ElThorAbility.YELLOW_THUNDER, 100);
      this.setAngle(50);
      this.setTargetTimeToReset(60);
      this.disableExplosionKnockback();
      this.setBranches((int)((double)5.0F + (double)length / (double)100.0F));
      int segments = (int)((double)this.getLength() * (double)0.5F);
      this.setSegments((int)((double)segments + WyHelper.randomWithRange(-segments / 4, segments / 4)));
      this.addBlockHitEvent(100, this::blockHitEvent);
   }

   private void blockHitEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         float size = (float)((int)(3.0F * this.multiplier));
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), size);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), new CommonExplosionParticleEffect.Details((double)size));
         explosion.m_46061_();
      });
   }
}
