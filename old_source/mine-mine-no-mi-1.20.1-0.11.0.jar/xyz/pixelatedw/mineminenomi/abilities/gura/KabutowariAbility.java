package xyz.pixelatedw.mineminenomi.abilities.gura;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.math.VectorHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KabutowariAbility extends Ability {
   private static final int COOLDOWN = 200;
   private static final int CHARGE_TIME = 40;
   private static final int DAMAGE = 60;
   private static final int PULL_TIME = 200;
   public static final RegistryObject<AbilityCore<KabutowariAbility>> INSTANCE = ModRegistry.registerAbility("kabutowari", "Kabutowari", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user grabs their opponent's head and concentrates their vibrations around it, resulting in crushing damage", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KabutowariAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ChargeComponent.getTooltip(40.0F), DealDamageComponent.getTooltip(60.0F)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceElement(SourceElement.SHOCKWAVE).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addEndEvent(this::onContinuityEnd);
   private final GrabEntityComponent grabComponent = (new GrabEntityComponent(this, true, false, true, 1.0F)).addPullStartEvent(this::onPullStart).addPullEndEvent(this::onPullEnd);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(this::tryHitEvent).addOnHitEvent(this::onHitEvent);
   private final PoolComponent poolComponent;
   private final ExplosionComponent explosionComponent;

   public KabutowariAbility(AbilityCore<KabutowariAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GRAB_ABILITY, new AbilityPool[0]);
      this.explosionComponent = new ExplosionComponent(this);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.animationComponent, this.dealDamageComponent, this.continuousComponent, this.grabComponent, this.hitTriggerComponent, this.poolComponent, this.explosionComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresEmptyHand);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!this.chargeComponent.isCharging()) {
         if (this.continuousComponent.isContinuous()) {
            this.grabComponent.release(entity);
            this.continuousComponent.stopContinuity(entity);
         } else if (this.grabComponent.getState() == GrabEntityComponent.GrabState.IDLE && this.grabComponent.grabNearest(entity, false)) {
            this.grabComponent.triggerPulling(entity);
         } else {
            this.continuousComponent.startContinuity(entity);
         }

      }
   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      return this.continuousComponent.isContinuous() && !this.grabComponent.hasGrabbedEntity() ? HitTriggerComponent.HitResult.HIT : HitTriggerComponent.HitResult.PASS;
   }

   private boolean onHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      if (this.grabComponent.grabManually(entity, target)) {
         this.grabComponent.startPulling(entity);
      }

      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.ANTI_KNOCKBACK.get(), 1));
      return false;
   }

   public void onPullStart(LivingEntity entity, IAbility ability) {
      this.continuousComponent.setThresholdTime(entity, 200.0F);
   }

   public void onPullEnd(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      if (this.grabComponent.canContinueGrab(entity)) {
         this.chargeComponent.startCharging(entity, 40.0F);
      }

   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.grabComponent.getState() != GrabEntityComponent.GrabState.GRABBED) {
            this.grabComponent.release(entity);
         }

         if (!this.grabComponent.canContinueGrab(entity)) {
            super.cooldownComponent.startCooldown(entity, 200.0F);
         }

      }
   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.animationComponent.start(entity, ModAnimations.POINT_RIGHT_ARM);
         LivingEntity grabbedTarget = this.grabComponent.getGrabbedEntity();
         int chargeTime = (int)this.chargeComponent.getMaxChargeTime();
         if (grabbedTarget != null) {
            entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GURA_SFX.get(), SoundSource.PLAYERS, 5.0F, 1.0F);
            grabbedTarget.m_7292_(new MobEffectInstance((MobEffect)ModEffects.SEISMIC_BUBBLE.get(), chargeTime, 0));
            AbilityHelper.setDeltaMovement(grabbedTarget, (double)0.0F, (double)0.0F, (double)0.0F);
         }

      }
   }

   private void onChargeTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (!super.canUse(entity).isFail() && this.grabComponent.canContinueGrab(entity)) {
            LivingEntity target = this.grabComponent.getGrabbedEntity();
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 1));
            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.GRABBED.get(), 2, 3));
            Vec3 pos = VectorHelper.getRelativeOffset(entity.m_20182_(), entity.f_20883_, new Vec3((double)0.5F, (double)(-target.m_20192_()), (double)target.m_20205_() - 0.2));
            AbilityHelper.setDeltaMovement(target, pos.m_82546_(target.m_20182_()), true);
         } else {
            this.chargeComponent.stopCharging(entity);
         }
      }
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.grabComponent.canContinueGrab(entity)) {
            LivingEntity grabbedTarget = this.grabComponent.getGrabbedEntity();
            DamageSource source = this.dealDamageComponent.getDamageSource(entity);
            IDamageSourceHandler.getHandler(source).addAbilityPiercing(0.75F, this.getCore());
            this.dealDamageComponent.hurtTarget(entity, grabbedTarget, 60.0F, source);
            AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, grabbedTarget.m_20185_(), grabbedTarget.m_20186_(), grabbedTarget.m_20189_(), 2.0F);
            explosion.setStaticDamage(20.0F);
            explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION4);
            explosion.m_46061_();
            ItemStack stack = grabbedTarget.m_6844_(EquipmentSlot.HEAD);
            stack.m_41622_(15, grabbedTarget, (user) -> user.m_21166_(EquipmentSlot.HEAD));
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.AIR_CRACKS.get(), entity, grabbedTarget.m_20185_(), grabbedTarget.m_20188_(), grabbedTarget.m_20189_());
            entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.GURA_SFX.get(), SoundSource.PLAYERS, 5.0F, 1.0F);
            grabbedTarget.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 40, 0));
            this.grabComponent.release(entity);
         }

         this.animationComponent.stop(entity);
         this.cooldownComponent.startCooldown(entity, 200.0F);
      }
   }
}
