package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.blackleg;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class PoeleAFrireProjectile extends NuProjectileEntity {
   public PoeleAFrireProjectile(EntityType<? extends PoeleAFrireProjectile> type, Level world) {
      super(type, world);
   }

   public PoeleAFrireProjectile(Level world, LivingEntity player, IAbility abl) {
      super((EntityType)ModProjectiles.POELE_A_FRIRE.get(), world, player, (IAbility)null, SourceElement.FIRE, SourceHakiNature.HARDENING, SourceType.FIST);
      super.setDamage(10.0F);
      super.setMaxLife(10);
      this.addTickEvent(100, this::onTickEvent);
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
   }

   private void onTickEvent() {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.EXTRA_HACHI_DIABLE.get(), this, super.m_20185_(), super.m_20186_(), super.m_20189_());
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      result.m_82443_().m_20254_(2);
   }
}
