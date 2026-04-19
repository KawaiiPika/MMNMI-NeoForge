package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.sniper;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class NemuriBoshiProjectile extends NuProjectileEntity {
   public NemuriBoshiProjectile(EntityType<? extends NemuriBoshiProjectile> type, Level world) {
      super(type, world);
   }

   public NemuriBoshiProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.NEMURI_BOSHI.get(), world, player, ability);
      this.setDamage(2.0F);
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity living) {
         living.m_7292_(new MobEffectInstance((MobEffect)ModEffects.UNCONSCIOUS.get(), 40, 1));
      }

   }
}
