package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.NuWorld;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.protection.DefaultProtectionRules;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FreshFireProjectile extends NuProjectileEntity {
   public FreshFireProjectile(EntityType<? extends FreshFireProjectile> type, Level world) {
      super(type, world);
   }

   public FreshFireProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.FRESH_FIRE.get(), world, player, ability);
      this.setDamage(1.0F);
      this.setPassThroughEntities();
      this.setMaxLife(15);
      this.setEntityCollisionSize((double)3.0F, (double)1.0F, (double)3.0F);
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addBlockHitEvent(100, this::onBlockImpactEvent);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      result.m_82443_().m_20254_(4);
   }

   private void onBlockImpactEvent(BlockHitResult result) {
      NuWorld.setBlockState((Entity)this.getOwner(), result.m_82425_().m_7918_(0, 1, 0), Blocks.f_50083_.m_49966_(), 3, DefaultProtectionRules.AIR);
   }

   private void onTickEvent() {
      if (!this.m_9236_().f_46443_) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.FRESH_FIRE.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

   }
}
