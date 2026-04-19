package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.noro;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public class NoroNoroBeamProjectile extends NuProjectileEntity {
   public NoroNoroBeamProjectile(EntityType<? extends NoroNoroBeamProjectile> type, Level world) {
      super(type, world);
   }

   public NoroNoroBeamProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.NORO_NORO_BEAM.get(), world, player, ability);
      this.setMaxLife(10);
      this.setEntityCollisionSize((double)1.25F);
      this.setPassThroughEntities();
      this.setUnavoidable();
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         MobEffectInstance instance = target.m_21124_((MobEffect)ModEffects.NORO_SLOWNESS.get());
         if (instance == null) {
            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.NORO_SLOWNESS.get(), 240, 0));
         } else {
            ((IMobEffectInstanceMixin)instance).setDuration(instance.m_19557_() + 240);
            WyHelper.sendApplyEffectToAllNearby(target, target.m_20182_(), 100, instance);
         }
      }

   }
}
