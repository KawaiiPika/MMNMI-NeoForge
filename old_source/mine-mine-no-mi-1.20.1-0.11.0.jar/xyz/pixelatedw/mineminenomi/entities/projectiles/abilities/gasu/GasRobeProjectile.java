package xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.gasu;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import xyz.pixelatedw.mineminenomi.abilities.gasu.ShinokuniAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModProjectiles;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class GasRobeProjectile extends NuProjectileEntity {
   private boolean hasShinokuniEnabled = false;
   private ShinokuniAbility shinokuniAbility;

   public GasRobeProjectile(EntityType<? extends GasRobeProjectile> type, Level world) {
      super(type, world);
   }

   public GasRobeProjectile(Level world, LivingEntity player, IAbility ability) {
      super((EntityType)ModProjectiles.GAS_ROBE.get(), world, player, ability);
      this.setDamage(0.1F);
      this.setMaxLife(30);
      this.setPassThroughEntities();
      this.setArmorPiercing(0.5F);
      this.shinokuniAbility = (ShinokuniAbility)AbilityCapability.get(player).map((props) -> (ShinokuniAbility)props.getEquippedAbility((AbilityCore)ShinokuniAbility.INSTANCE.get())).orElse((Object)null);
      this.hasShinokuniEnabled = this.shinokuniAbility != null && this.shinokuniAbility.isContinuous();
      this.addEntityHitEvent(100, this::entityImpactEvent);
      this.addTickEvent(100, this::tickEvent);
   }

   private void entityImpactEvent(EntityHitResult result) {
      Entity var3 = result.m_82443_();
      if (var3 instanceof LivingEntity target) {
         if (this.hasShinokuniEnabled) {
            this.shinokuniAbility.applyEffects(this.getOwner(), target);
         } else {
            target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 200, 2));
            target.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 40, 0));
            target.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 200, 6));
         }
      }

   }

   private void tickEvent() {
      if (!this.m_9236_().f_46443_) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GAS_ROBE.get(), this, this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

   }
}
