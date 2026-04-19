package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.mero;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class PistolKissProjectile extends NuProjectileEntity {
   public PistolKissProjectile(EntityType<? extends PistolKissProjectile> type, Level world) {
      super(type, world);
   }

   public PistolKissProjectile(Level world, LivingEntity player, Ability ability) {
      super((EntityType)ModProjectiles.PISTOL_KISS.get(), world, player, ability);
      this.setDamage(4.0F);
      this.setMaxLife(30);
      this.addEntityHitEvent(100, this::onEntityHitEvent);
   }

   private void onEntityHitEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity living) {
         living.m_7292_(new MobEffectInstance((MobEffect)ModEffects.LOVESTRUCK.get(), 20, 0));
      }

   }
}
