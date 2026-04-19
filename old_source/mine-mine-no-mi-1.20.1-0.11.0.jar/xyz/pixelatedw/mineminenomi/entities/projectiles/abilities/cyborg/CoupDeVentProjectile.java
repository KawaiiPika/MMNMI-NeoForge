package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.cyborg;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;

public class CoupDeVentProjectile extends NuProjectileEntity {
   public CoupDeVentProjectile(EntityType<? extends CoupDeVentProjectile> type, Level world) {
      super(type, world);
   }

   public CoupDeVentProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.COUP_DE_VENT.get(), world, player, ability);
      this.setDamage(50.0F);
      this.setPassThroughEntities();
      this.setMaxLife(15);
      this.setEntityCollisionSize((double)5.5F);
      this.addEntityHitEvent(100, this::onEntityImpactEvent);
      this.addTickEvent(100, this::onTickEvent);
   }

   private void onEntityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         double xPower = WyHelper.randomDouble() * (double)400.0F;
         if (xPower >= (double)0.0F) {
            xPower = Mth.m_14008_(xPower, (double)200.0F, (double)400.0F);
         } else {
            xPower = Mth.m_14008_(xPower, (double)-400.0F, (double)-200.0F);
         }

         double zPower = WyHelper.randomDouble() * (double)400.0F;
         if (zPower >= (double)0.0F) {
            zPower = Mth.m_14008_(zPower, (double)200.0F, (double)400.0F);
         } else {
            zPower = Mth.m_14008_(zPower, (double)-400.0F, (double)-200.0F);
         }

         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.LAUNCHED.get(), 1200, 0));
         AbilityHelper.setDeltaMovement(target, xPower, (double)10.0F, zPower);
         target.f_19789_ = 0.0F;
      }
   }

   private void onTickEvent() {
      for(int i = 0; i < 25; ++i) {
         double offsetX = WyHelper.randomDouble() * 1.2;
         double offsetY = WyHelper.randomDouble() * 1.2;
         double offsetZ = WyHelper.randomDouble() * 1.2;
         ((ServerLevel)this.m_9236_()).m_8767_(ParticleTypes.f_123810_, this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, -0.1);
         if (i % 5 == 0) {
            ((ServerLevel)this.m_9236_()).m_8767_(ParticleTypes.f_123747_, this.m_20185_() + offsetX, this.m_20186_() + offsetY, this.m_20189_() + offsetZ, 1, (double)0.0F, (double)0.0F, (double)0.0F, -0.1);
         }
      }

   }
}
