package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.toriphoenix.BlueBirdParticleEffect;

public class BlueBirdAbility extends Ability {
   private static final int CHARGE_TIME = 20;
   private static final int HOLD_TIME = 10;
   private static final int COOLDOWN = 200;
   private static final int RANGE = 2;
   private static final int DAMAGE = 25;
   public static final RegistryObject<AbilityCore<BlueBirdAbility>> INSTANCE = ModRegistry.registerAbility("blue_bird", "Blue Bird", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user builds up momentum through blue flames, to deliver a devastating kick.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BlueBirdAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.PHOENIX_ASSAULT)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ChargeComponent.getTooltip(20.0F), DealDamageComponent.getTooltip(25.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private static final BlueBirdParticleEffect.Details DETAILS = new BlueBirdParticleEffect.Details();
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);

   public BlueBirdAbility(AbilityCore<BlueBirdAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.continuousComponent, this.animationComponent, this.rangeComponent, this.dealDamageComponent, this.hitTrackerComponent});
      this.addCanUseCheck(ToriPhoenixHelper::requiresAssaultPoint);
      this.addContinueUseCheck(ToriPhoenixHelper::requiresAssaultPoint);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (!this.continuousComponent.isContinuous()) {
         this.chargeComponent.startCharging(entity, 20.0F);
      }

   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      DETAILS.setMaxChargeTime((double)this.chargeComponent.getMaxChargeTime());
      DETAILS.setCurrentChargeTime((double)this.chargeComponent.getChargeTime());
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BLUE_BIRD.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), DETAILS);
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity, 10.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.PHOENIX_KICK);
      this.hitTrackerComponent.clearHits();
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      float time = this.continuousComponent.getContinueTime();
      Vec3 velocity = entity.m_20154_().m_82559_(new Vec3((double)5.0F * ((double)time / (double)10.0F), (double)5.0F, (double)5.0F * ((double)time / (double)10.0F)));
      AbilityHelper.setDeltaMovement(entity, velocity);

      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 2.0F)) {
         if (this.hitTrackerComponent.canHit(target)) {
            this.dealDamageComponent.hurtTarget(entity, target, 25.0F);
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 200.0F);
   }
}
