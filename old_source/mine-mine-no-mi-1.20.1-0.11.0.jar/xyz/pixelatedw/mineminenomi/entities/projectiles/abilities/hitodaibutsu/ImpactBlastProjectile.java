package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.hitodaibutsu;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ImpactBlastProjectile extends NuProjectileEntity {
   public ImpactBlastProjectile(EntityType<? extends ImpactBlastProjectile> type, Level world) {
      super(type, world);
   }

   public ImpactBlastProjectile(Level world, LivingEntity player, Ability ability) {
      super((EntityType)ModProjectiles.IMPACT_BLAST.get(), world, player, ability);
      this.setDamage(60.0F);
      this.setMaxLife(20);
      this.setEntityCollisionSize((double)5.0F);
      this.setPassThroughBlocks();
      this.setPassThroughEntities();
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onTickEvent() {
      if (this.f_19797_ > 0) {
         if (this.f_19797_ % 2 == 0) {
            ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
               AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 5.0F);
               explosion.setStaticDamage(40.0F * (float)(this.getLife() / this.getMaxLife()));
               explosion.m_46061_();
            });
         }

         if (this.f_19797_ % 5 == 0) {
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.IMPACT_WAVE.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
         }
      }

   }
}
