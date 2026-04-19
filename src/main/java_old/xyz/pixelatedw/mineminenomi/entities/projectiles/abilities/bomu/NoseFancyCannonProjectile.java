package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bomu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.abilities.bomu.NoseFancyCannonAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class NoseFancyCannonProjectile extends NuProjectileEntity {
   public NoseFancyCannonProjectile(EntityType<? extends NoseFancyCannonProjectile> type, Level world) {
      super(type, world);
   }

   public NoseFancyCannonProjectile(Level world, LivingEntity player, NoseFancyCannonAbility ability) {
      super((EntityType)ModProjectiles.NOSE_FANCY_CANNON.get(), world, player, ability);
      this.setDamage(10.0F);
      this.setMaxLife(32);
      this.addEntityHitEvent(100, this::entityHitEvent);
      this.addBlockHitEvent(100, this::blockHitEvent);
   }

   private void entityHitEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         this.explode(target.m_20183_());
      }

   }

   private void blockHitEvent(BlockHitResult result) {
      this.explode(result.m_82425_());
   }

   private void explode(BlockPos pos) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), 3.0F);
         explosion.setStaticDamage(15.0F);
         explosion.setExplosionSound(true);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(true);
         explosion.setFireAfterExplosion(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION4);
         explosion.setDamageEntities(true);
         explosion.m_46061_();
      });
   }
}
