package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mera;

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

public class DaiEnkaiOnibiProjectile extends NuProjectileEntity {
   public DaiEnkaiOnibiProjectile(EntityType<? extends DaiEnkaiOnibiProjectile> type, Level world) {
      super(type, world);
   }

   public DaiEnkaiOnibiProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.DAI_ENKAI_ONIBI.get(), world, player, ability);
      this.setMaxLife(60);
      this.setDamage(75.0F);
      this.setArmorPiercing(0.75F);
      this.setUnavoidable();
      this.setPassThroughEntities();
      this.addBlockHitEvent(100, this::blockHitEvent);
   }

   private void blockHitEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)result.m_82425_().m_123341_(), (double)result.m_82425_().m_123342_(), (double)result.m_82425_().m_123343_(), 10.0F);
         explosion.setStaticDamage(22.5F);
         explosion.setStaticBlockResistance(0.25F);
         explosion.setFireAfterExplosion(true);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION10);
         explosion.m_46061_();
      });
   }
}
