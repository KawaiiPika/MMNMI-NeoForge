package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.horo;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class NegativeHollowProjectile extends NuProjectileEntity {
   public NegativeHollowProjectile(EntityType<? extends NegativeHollowProjectile> type, Level world) {
      super(type, world);
   }

   public NegativeHollowProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.NEGATIVE_HOLLOW.get(), world, player, ability);
      this.setDamage(10.0F);
      this.setPassThroughEntities();
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         target.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 200, 0));
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.NEGATIVE.get(), 40, 0));
      }

   }
}
