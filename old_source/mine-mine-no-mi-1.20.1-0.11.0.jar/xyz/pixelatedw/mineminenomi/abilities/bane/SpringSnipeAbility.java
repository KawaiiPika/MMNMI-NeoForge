package xyz.pixelatedw.mineminenomi.abilities.bane;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SpringSnipeAbility extends Ability {
   private static final int CHARGE_TIME = 10;
   private static final int COOLDOWN = 280;
   private static final float RANGE = 1.6F;
   private static final float DAMAGE = 50.0F;
   public static final RegistryObject<AbilityCore<SpringSnipeAbility>> INSTANCE = ModRegistry.registerAbility("spring_snipe", "Spring Snipe", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Turning the user's forelegs into springs, they can launch themselves directly at the opponent", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, SpringSnipeAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(280.0F), ChargeComponent.getTooltip(10.0F), RangeComponent.getTooltip(1.6F, RangeComponent.RangeType.LINE), DealDamageComponent.getTooltip(50.0F)).setSourceType(SourceType.PHYSICAL).setSourceHakiNature(SourceHakiNature.HARDENING).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuousEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);

   public SpringSnipeAbility(AbilityCore<SpringSnipeAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.rangeComponent, this.dealDamageComponent, this.animationComponent, this.hitTrackerComponent, this.continuousComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 10.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      IAbilityData abilityDataProps = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (abilityDataProps != null) {
         SpringHopperAbility springHopper = (SpringHopperAbility)abilityDataProps.getEquippedAbility((AbilityCore)SpringHopperAbility.INSTANCE.get());
         if (springHopper != null && !springHopper.canUse(entity).isFail()) {
            springHopper.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get()).ifPresent((comp) -> {
               if (!comp.isContinuous()) {
                  comp.startContinuity(entity);
               }

            });
            this.hitTrackerComponent.clearHits();
         }
      }
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      AbilityHelper.setDeltaMovement(entity, (double)0.0F, (double)0.0F, (double)0.0F);
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity, 10.0F);
      this.animationComponent.start(entity, ModAnimations.SHOOT_SELF_FORWARD);
   }

   private void startContinuousEvent(LivingEntity entity, IAbility ability) {
      Vec3 speed = entity.m_20154_().m_82542_((double)6.0F, (double)6.0F, (double)6.0F);
      AbilityHelper.setDeltaMovement(entity, speed.f_82479_, speed.f_82480_, speed.f_82481_);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      for(LivingEntity target : this.rangeComponent.getTargetsInLine(entity, 1.6F, 1.25F)) {
         if (this.hitTrackerComponent.canHit(target)) {
            this.dealDamageComponent.hurtTarget(entity, target, 50.0F);
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 280.0F);
   }
}
