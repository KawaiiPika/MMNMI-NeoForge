package xyz.pixelatedw.mineminenomi.abilities.mandemontactics;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.DropHitAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.ItemsHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.BreakingBlocksParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DemonicDanceAbility extends DropHitAbility {
   private static final int COOLDOWN = 260;
   private static final float RANGE = 1.25F;
   private static final float DAMAGE = 30.0F;
   private static final float CHARGE_TIME = 40.0F;
   public static final TargetPredicate TARGET_TEST = (new TargetPredicate()).testEnemyFaction().testSimpleInView();
   public static final RegistryObject<AbilityCore<DemonicDanceAbility>> INSTANCE = ModRegistry.registerAbility("demonic_dance", "Demonic Dance", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, DemonicDanceAbility::new)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(260.0F), ChargeComponent.getTooltip(40.0F), RangeComponent.getTooltip(1.25F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(30.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.BLUNT).build("mineminenomi"));
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addEndEvent(this::endChargeEvent);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private BreakingBlocksParticleEffect.Details details = new BreakingBlocksParticleEffect.Details(40);
   @Nullable
   private Entity target;
   private boolean isStandard;

   public DemonicDanceAbility(AbilityCore<DemonicDanceAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.dealDamageComponent, this.rangeComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresBluntWeapon);
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
      this.continuousComponent.addEndEvent(100, this::endContinuityEvent);
      this.continuousComponent.addTickEvent(100, this::tickContinuityEvent);
   }

   protected void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 40.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.DEMONIC_DANCE_CHARGE, (int)this.chargeComponent.getMaxChargeTime());
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.continuousComponent.startContinuity(entity);
      this.animationComponent.start(entity, ModAnimations.DEMONIC_DANCE_LEAP);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      if (this.target != null) {
         Vec3 speed = this.target.m_20182_().m_82546_(entity.m_20182_()).m_82541_().m_82542_((double)2.0F, (double)1.0F, (double)2.0F);
         AbilityHelper.setDeltaMovement(entity, speed.f_82479_, (double)0.5F, speed.f_82481_);
      } else {
         Vec3 speed = entity.m_20154_().m_82542_((double)2.0F, (double)0.0F, (double)2.0F);
         AbilityHelper.setDeltaMovement(entity, speed.f_82479_, (double)0.5F, speed.f_82481_);
      }

   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.f_19789_ > 0.0F && !this.hasLanded()) {
         for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 1.25F, TARGET_TEST)) {
            if (this.hitTrackerComponent.canHit(target)) {
               ItemsHelper.stopShieldAndStartCooldown(target, 100);
               this.dealDamageComponent.hurtTarget(entity, target, 15.0F);
               this.setLanded();
            }
         }

         for(Entity target : this.hitTrackerComponent.getHits()) {
            if (AbilityHelper.getDifferenceToFloor(target) > (double)1.5F) {
               target.m_6021_(entity.m_20185_(), entity.m_20186_() - (double)1.0F, entity.m_20189_());
            }
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.target = null;
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 260.0F);
   }

   public void onLanding(LivingEntity entity) {
      for(Entity target : this.hitTrackerComponent.getHits()) {
         if (target instanceof LivingEntity livingTarget) {
            if (!this.isStandard) {
               livingTarget.m_7292_(new MobEffectInstance((MobEffect)ModEffects.UNCONSCIOUS.get(), 20, 0, false, false));
            }

            this.dealDamageComponent.hurtTarget(entity, livingTarget, 15.0F);
         }
      }

      List<Vec3> positions = new ArrayList();
      int range = (int)Math.ceil((double)1.25F);

      for(int x = -range; x < range; ++x) {
         for(int z = -range; z < range; ++z) {
            double posX = entity.m_20185_() + (double)x;
            double posY = entity.m_20186_() - (double)1.0F;
            double posZ = entity.m_20189_() + (double)z;
            Vec3 pos = new Vec3(posX, posY, posZ);
            positions.add(pos);
         }
      }

      if (positions.size() > 0) {
         this.details.setVecPositions(positions);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BREAKING_BLOCKS.get(), entity, (double)0.0F, (double)0.0F, (double)0.0F, this.details);
      }

      this.continuousComponent.stopContinuity(entity);
   }

   public void setStandardMode() {
      this.isStandard = true;
   }

   public void setTarget(Entity target) {
      this.target = target;
   }

   public Entity getTarget() {
      return this.target;
   }
}
