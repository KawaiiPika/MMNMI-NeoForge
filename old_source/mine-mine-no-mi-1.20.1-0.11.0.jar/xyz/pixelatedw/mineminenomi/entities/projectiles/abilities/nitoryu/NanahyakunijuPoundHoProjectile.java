package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.nitoryu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class NanahyakunijuPoundHoProjectile extends NuProjectileEntity {
   public NanahyakunijuPoundHoProjectile(EntityType type, Level world) {
      super(type, world);
   }

   public NanahyakunijuPoundHoProjectile(Level world, LivingEntity player, Ability ability) {
      super((EntityType)ModProjectiles.NANAHYAKUNIJU_POUND_HO.get(), world, player, ability);
      super.setDamage(25.0F);
      super.setMaxLife(40);
      super.setPassThroughEntities();
      super.addBlockHitEvent(100, this::blockHitEvent);
   }

   private void blockHitEvent(BlockHitResult result) {
      BlockPos hit = result.m_82425_();
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), (double)hit.m_123341_(), (double)hit.m_123342_(), (double)hit.m_123343_(), 2.0F);
         explosion.setStaticDamage(10.0F);
         explosion.setExplosionSound(true);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(true);
         explosion.setFireAfterExplosion(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), new CommonExplosionParticleEffect.Details((double)2.0F));
         explosion.setDamageEntities(true);
         explosion.m_46061_();
      });
   }
}
