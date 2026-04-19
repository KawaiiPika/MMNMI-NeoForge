package xyz.pixelatedw.mineminenomi.abilities.mandemontactics;

import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class DemonicDashAbility extends Ability {
   private static final int COOLDOWN = 160;
   private static final float RANGE = 1.6F;
   private static final float DAMAGE = 30.0F;
   private static final int CHARGE_TIME = 20;
   public static final TargetPredicate TARGET_TEST = (new TargetPredicate()).testEnemyFaction().testSimpleInView();
   public static final RegistryObject<AbilityCore<DemonicDashAbility>> INSTANCE = ModRegistry.registerAbility("demonic_dash", "Demonic Dash", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, DemonicDashAbility::new)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(160.0F), ChargeComponent.getTooltip(20.0F), RangeComponent.getTooltip(1.6F, RangeComponent.RangeType.LINE), DealDamageComponent.getTooltip(30.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.BLUNT).build("mineminenomi"));
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::startChargeEvent).addEndEvent(this::endChargeEvent);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addTickEvent(this::tickContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   @Nullable
   private LivingEntity target;

   public DemonicDashAbility(AbilityCore<DemonicDashAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.rangeComponent, this.dealDamageComponent, this.animationComponent, this.continuousComponent, this.hitTrackerComponent, this.chargeComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresBluntWeapon);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 20.0F);
   }

   private void startChargeEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.DEMONIC_DASH);
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      Vec3 speed;
      if (this.target == null) {
         speed = entity.m_20154_().m_82542_((double)5.5F, (double)0.0F, (double)5.5F);
      } else {
         speed = entity.m_20182_().m_82546_(this.target.m_20182_()).m_82541_().m_82542_((double)5.5F, (double)2.5F, (double)5.5F);
      }

      AbilityHelper.setDeltaMovement(entity, speed.f_82479_, 0.15, speed.f_82481_);
      this.continuousComponent.startContinuity(entity, 10.0F);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6084_()) {
         for(LivingEntity target : this.rangeComponent.getTargetsInLine(entity, 1.6F, 1.5F)) {
            if (this.hitTrackerComponent.canHit(target)) {
               this.dealDamageComponent.hurtTarget(entity, target, 30.0F);
            }
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.target = null;
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 160.0F);
   }

   public void setTarget(LivingEntity entity) {
      this.target = entity;
   }
}
