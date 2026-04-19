package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.bari;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class BarrierCrashProjectile extends NuProjectileEntity {
   public BarrierCrashProjectile(EntityType<BarrierCrashProjectile> type, Level world) {
      super(type, world);
   }

   public BarrierCrashProjectile(Level world, LivingEntity player, IAbility parent) {
      super((EntityType)ModProjectiles.BARRIER_CRASH.get(), world, player, parent);
      this.setDamage(20.0F);
      this.setMaxLife(10);
      this.setPassThroughEntities();
      this.setPhysical();
      this.addEntityHitEvent(100, this::entityHitEvent);
   }

   private void entityHitEvent(EntityHitResult hit) {
      AbilityHelper.setDeltaMovement(hit.m_82443_(), this.m_20184_());
   }
}
