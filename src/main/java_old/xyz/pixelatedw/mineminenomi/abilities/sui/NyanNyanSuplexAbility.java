package xyz.pixelatedw.mineminenomi.abilities.sui;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class NyanNyanSuplexAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final int CHARGE_TIME = 20;
   private static final float RANGE = 1.3F;
   private static final float DAMAGE = 20.0F;
   public static final RegistryObject<AbilityCore<NyanNyanSuplexAbility>> INSTANCE = ModRegistry.registerAbility("nyan_nyan_suplex", "Nyan Nyan Suplex", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("While swimming, grabs the nearest enemy from the back and launches them into the ground, dealing moderate damage and creating a small crater.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, NyanNyanSuplexAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ChargeComponent.getTooltip(20.0F), RangeComponent.getTooltip(1.3F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::tickContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(this::tickChargingEvent).addEndEvent(this::endChargingEvent);
   private final GrabEntityComponent grabEntityComponent = new GrabEntityComponent(this, true, true, 1.0F);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final PoolComponent poolComponent;
   private final ExplosionComponent explosionComponent;
   private int initialY;

   public NyanNyanSuplexAbility(AbilityCore<NyanNyanSuplexAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GRAB_ABILITY, new AbilityPool[0]);
      this.explosionComponent = new ExplosionComponent(this);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.grabEntityComponent, this.continuousComponent, this.rangeComponent, this.dealDamageComponent, this.animationComponent, this.poolComponent, this.explosionComponent});
      this.addCanUseCheck(SuiHelper::requiresFreeSwimming);
      this.addContinueUseCheck(SuiHelper::requiresFreeSwimming);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (this.grabEntityComponent.hasGrabbedEntity()) {
         this.grabEntityComponent.release(entity);
      }

      this.continuousComponent.triggerContinuity(entity);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (!this.grabEntityComponent.hasGrabbedEntity()) {
            for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 1.3F)) {
               if (this.grabEntityComponent.grabManually(entity, target)) {
                  Vec3 lookVec = target.m_20154_().m_82541_().m_82548_().m_82542_(1.1, (double)1.0F, 1.1);
                  Vec3 moveVec = target.m_20182_().m_82549_(lookVec).m_82546_(entity.m_20182_());
                  AbilityHelper.setDeltaMovement(entity, moveVec, true);
                  this.initialY = entity.m_146904_();
                  this.animationComponent.start(entity, ModAnimations.POINT_ARMS);
                  this.chargeComponent.startCharging(entity, 20.0F);
                  break;
               }
            }
         }

      }
   }

   private void tickChargingEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (!this.grabEntityComponent.canContinueGrab(entity)) {
            this.chargeComponent.stopCharging(entity);
         } else {
            LivingEntity target = this.grabEntityComponent.getGrabbedEntity();
            if (entity.m_146904_() < this.initialY - 1) {
               this.chargeComponent.stopCharging(entity);
            } else {
               entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.PARALYSIS.get(), 5, 1));
               target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.PARALYSIS.get(), 5, 1));
            }
         }
      }
   }

   private void endChargingEvent(LivingEntity entity, IAbility ability) {
      if (this.grabEntityComponent.canContinueGrab(entity)) {
         LivingEntity target = this.grabEntityComponent.getGrabbedEntity();
         this.dealDamageComponent.hurtTarget(entity, target, 20.0F);
         AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, target.m_20185_(), target.m_20186_(), target.m_20189_(), 2.0F);
         explosion.setStaticDamage(30.0F);
         explosion.setExplosionSound(true);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(true);
         explosion.setFireAfterExplosion(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION4);
         explosion.setDamageEntities(true);
         explosion.m_46061_();
         this.grabEntityComponent.release(entity);
      }

      this.continuousComponent.stopContinuity(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 200.0F);
   }
}
