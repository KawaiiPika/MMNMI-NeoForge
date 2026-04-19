package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.supa;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SparklingDaisyAftereffectProjectile extends NuProjectileEntity {
   public SparklingDaisyAftereffectProjectile(EntityType<? extends SparklingDaisyAftereffectProjectile> type, Level world) {
      super(type, world);
   }

   public SparklingDaisyAftereffectProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.SPARKLING_DAISY_AFTER.get(), world, player, ability);
      this.setDamage(10.0F);
      this.setMaxLife(20);
      this.setPassThroughEntities();
      this.addTickEvent(100, this::tickEvent);
   }

   public void tickEvent() {
      if (!this.m_9236_().f_46443_ && this.f_19797_ > 5) {
         ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
            AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 2.0F);
            explosion.setStaticDamage(5.0F);
            explosion.setFireAfterExplosion(false);
            explosion.setExplosionSound(false);
            explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION4);
            explosion.m_46061_();
         });
      }

   }
}
