package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.rokushiki;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import xyz.pixelatedw.mineminenomi.abilities.rokushiki.RankyakuAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RankyakuProjectile extends NuProjectileEntity {
   public RankyakuProjectile(EntityType<? extends RankyakuProjectile> type, Level world) {
      super(type, world);
   }

   public RankyakuProjectile(Level world, LivingEntity entity, RankyakuAbility parent) {
      super((EntityType)ModProjectiles.RANKYAKU.get(), world, entity, parent);
      this.setDamage(40.0F);
      this.setMaxLife(40);
      this.setPassThroughEntities();
      this.addBlockHitEvent(100, this::blockHitEvent);
   }

   private void blockHitEvent(BlockHitResult result) {
      ExplosionComponent.createExplosion((IAbility)((IAbility)this.getParent().orElse((Object)null)), (comp) -> {
         AbilityExplosion explosion = comp.createExplosion(this, this.getOwner(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 3.0F);
         explosion.setStaticDamage(15.0F);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), new CommonExplosionParticleEffect.Details((double)3.0F));
         explosion.m_46061_();
      });
   }
}
