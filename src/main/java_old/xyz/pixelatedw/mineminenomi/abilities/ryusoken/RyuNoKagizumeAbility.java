package xyz.pixelatedw.mineminenomi.abilities.ryusoken;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
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
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.quest.IQuestData;
import xyz.pixelatedw.mineminenomi.data.entity.quest.QuestCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class RyuNoKagizumeAbility extends Ability {
   private static final int COOLDOWN = 400;
   private static final int CHARGE_TIME = 20;
   private static final int DAMAGE = 50;
   public static final RegistryObject<AbilityCore<RyuNoKagizumeAbility>> INSTANCE = ModRegistry.registerAbility("ryu_no_kagizume", "Ryu no Kagizume", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user attacks the enemy with a three-fingered claw hand strike, targeting their weak points to deal heavy damage and damaging their armour.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, RyuNoKagizumeAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), ChargeComponent.getTooltip(20.0F), DealDamageComponent.getTooltip(50.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setUnlockCheck(RyuNoKagizumeAbility::canUnlock).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addEndEvent(this::onContinuityEnd);
   private final GrabEntityComponent grabComponent = new GrabEntityComponent(this, true, false, 1.0F);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(this::tryHitEvent).addOnHitEvent(this::onHitEvent);
   private final PoolComponent poolComponent;
   private final ExplosionComponent explosionComponent;

   public RyuNoKagizumeAbility(AbilityCore<RyuNoKagizumeAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GRAB_ABILITY, new AbilityPool[0]);
      this.explosionComponent = new ExplosionComponent(this);
      super.addComponents(this.chargeComponent, this.animationComponent, this.dealDamageComponent, this.continuousComponent, this.grabComponent, this.hitTriggerComponent, this.poolComponent, this.explosionComponent);
      super.addCanUseCheck(AbilityUseConditions::requiresEmptyHand);
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!this.chargeComponent.isCharging()) {
         if (this.continuousComponent.isContinuous()) {
            this.grabComponent.release(entity);
            this.continuousComponent.stopContinuity(entity);
         } else if (this.grabComponent.getState() == GrabEntityComponent.GrabState.IDLE && this.grabComponent.grabNearest(entity, false)) {
            this.chargeComponent.startCharging(entity, 20.0F);
         } else {
            this.continuousComponent.startContinuity(entity);
         }

      }
   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      return this.continuousComponent.isContinuous() && !this.grabComponent.hasGrabbedEntity() ? HitTriggerComponent.HitResult.HIT : HitTriggerComponent.HitResult.PASS;
   }

   private boolean onHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      if (this.continuousComponent.isContinuous() && !this.grabComponent.hasGrabbedEntity()) {
         if (this.grabComponent.grabManually(entity, target)) {
            this.continuousComponent.stopContinuity(entity);
            this.chargeComponent.startCharging(entity, 20.0F);
         }

         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.ANTI_KNOCKBACK.get(), 1));
         return false;
      } else {
         return true;
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.grabComponent.getState() != GrabEntityComponent.GrabState.GRABBED) {
            this.grabComponent.release(entity);
         }

         if (!this.grabComponent.canContinueGrab(entity)) {
            super.cooldownComponent.startCooldown(entity, 400.0F);
         }

      }
   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.animationComponent.start(entity, ModAnimations.POINT_RIGHT_ARM);
         LivingEntity grabbedTarget = this.grabComponent.getGrabbedEntity();
         if (grabbedTarget != null) {
            AbilityHelper.setDeltaMovement(grabbedTarget, (double)0.0F, (double)0.0F, (double)0.0F);
         }

      }
   }

   private void onChargeTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (!super.canUse(entity).isFail() && this.grabComponent.canContinueGrab(entity)) {
            LivingEntity target = this.grabComponent.getGrabbedEntity();
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1));
            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.GRABBED.get(), 2, 3));
         } else {
            this.chargeComponent.stopCharging(entity);
         }
      }
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.grabComponent.canContinueGrab(entity)) {
            LivingEntity grabbedTarget = this.grabComponent.getGrabbedEntity();
            grabbedTarget.m_7292_(new MobEffectInstance((MobEffect)ModEffects.FRAGILE.get(), 40, 0));
            DamageSource source = this.dealDamageComponent.getDamageSource(entity);
            IDamageSourceHandler.getHandler(source).addAbilityPiercing(0.25F, this.getCore());
            this.dealDamageComponent.hurtTarget(entity, grabbedTarget, 50.0F, source);
            AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, grabbedTarget.m_20185_(), grabbedTarget.m_20186_(), grabbedTarget.m_20189_(), 1.0F);
            explosion.setDestroyBlocks(false);
            explosion.disableExplosionKnockback();
            explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
            explosion.m_46061_();
            ItemStack stack = grabbedTarget.m_6844_(EquipmentSlot.HEAD);
            stack.m_41622_(15, grabbedTarget, (user) -> user.m_21166_(EquipmentSlot.HEAD));
            this.grabComponent.release(entity);
         }

         this.animationComponent.stop(entity);
         super.cooldownComponent.startCooldown(entity, 400.0F);
      }
   }

   private static boolean canUnlock(LivingEntity entity) {
      if (entity instanceof Player player) {
         IEntityStats props = (IEntityStats)EntityStatsCapability.get(player).orElse((Object)null);
         IQuestData questProps = (IQuestData)QuestCapability.get(player).orElse((Object)null);
         return props != null && questProps != null ? false : false;
      } else {
         return false;
      }
   }
}
