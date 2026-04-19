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

public class MeroMeroMellowProjectile extends NuProjectileEntity {
   public MeroMeroMellowProjectile(EntityType<? extends MeroMeroMellowProjectile> type, Level world) {
      super(type, world);
   }

   public MeroMeroMellowProjectile(Level world, LivingEntity player, Ability ability) {
      super((EntityType)ModProjectiles.MERO_MERO_MELLOW.get(), world, player, ability);
      this.setDamage(10.0F);
      this.setPassThroughEntities();
      this.setMaxLife(30);
      this.setUnavoidable();
      this.setBlockCollisionSize((double)3.0F, (double)2.0F, (double)3.0F);
      this.addEntityHitEvent(100, this::onEntityHitEvent);
   }

   private void onEntityHitEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity living) {
         living.m_7292_(new MobEffectInstance((MobEffect)ModEffects.LOVESTRUCK.get(), 200, 1));
      }

   }
}
