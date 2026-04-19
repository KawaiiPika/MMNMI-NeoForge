package xyz.pixelatedw.mineminenomi.abilities.beta;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class HanamizuShinkenAbility extends Ability {
   private static final int HOLD_TIME = 60;
   private static final int COOLDOWN = 100;
   public static final RegistryObject<AbilityCore<HanamizuShinkenAbility>> INSTANCE = ModRegistry.registerAbility("hanamizu_shinken_shirahadori", "Hanamizu Shinken Shirahadori", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Creates a protecting wall of mucus, protecting the user from incoming physical attacks and physical projectiles.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, HanamizuShinkenAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F), ContinuousComponent.getTooltip(60.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnHurtEvent(this::damageTakenEvent);

   public HanamizuShinkenAbility(AbilityCore<HanamizuShinkenAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.damageTakenComponent});
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 60.0F);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 0, false, false));
      Vec3 lookVec = entity.m_20154_();
      int range = 2;
      double boxSize = 1.2;

      for(int i = 0; i < range * 2; ++i) {
         double distance = (double)i / (double)2.0F;
         Vec3 pos = new Vec3(entity.m_20185_() + lookVec.f_82479_ * distance, entity.m_20186_() + (double)entity.m_20192_() + lookVec.f_82480_ * distance, entity.m_20189_() + lookVec.f_82481_ * distance);

         for(Entity target : entity.m_9236_().m_6249_(entity, new AABB(pos.f_82479_ - boxSize, pos.f_82480_ - boxSize, pos.f_82481_ - boxSize, pos.f_82479_ + boxSize, pos.f_82480_ + boxSize * (double)2.0F, pos.f_82481_ + boxSize), (targetx) -> targetx != entity)) {
            if (target instanceof NuProjectileEntity projectile) {
               if (projectile.isPhysical()) {
                  this.reflectProjectile(projectile);
               }
            } else if (target instanceof ThrowableProjectile || target instanceof AbstractArrow) {
               this.reflectProjectile(target);
            }
         }
      }

      if (this.continuousComponent.getContinueTime() % 5.0F == 0.0F) {
         lookVec.m_82542_((double)2.5F, (double)1.0F, (double)2.5F);
         Vec3 particlesPos = entity.m_20182_().m_82549_(lookVec);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.HANAMIZU_SHINKEN_SHIRADORI.get(), entity, particlesPos.f_82479_, particlesPos.f_82480_, particlesPos.f_82481_);
      }

   }

   private void reflectProjectile(Entity target) {
      AbilityHelper.setDeltaMovement(target, -target.m_20184_().f_82479_ / (double)2.0F, target.m_20184_().f_82480_, -target.m_20184_().f_82481_ / (double)2.0F);
   }

   private float damageTakenEvent(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      IDamageSourceHandler handler = (IDamageSourceHandler)damageSource;
      if (this.continuousComponent.isContinuous() && (handler.hasType(SourceType.PHYSICAL) || handler.hasType(SourceType.FIST))) {
         Entity attacker = damageSource.m_7640_();
         if (attacker != null && attacker instanceof LivingEntity) {
            Vec3 lookVec = entity.m_20154_().m_82542_((double)4.0F, (double)4.0F, (double)4.0F);
            AbilityHelper.setDeltaMovement(attacker, lookVec);
         }

         return 0.0F;
      } else {
         return damage;
      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 100.0F);
   }
}
