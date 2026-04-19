package xyz.pixelatedw.mineminenomi.abilities.toriphoenix;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class TenseiNoSoenAbility extends Ability {
   private static final int CHARGE_TIME = 60;
   private static final int COOLDOWN = 600;
   private static final int RANGE = 30;
   private static final int DAMAGE = 50;
   public static final RegistryObject<AbilityCore<TenseiNoSoenAbility>> INSTANCE = ModRegistry.registerAbility("tensei_no_soen", "Tensei no Soen", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("While in the air, the user amasses spiraling flames, then slams into the ground, releasing a massive shockwave.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, TenseiNoSoenAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.PHOENIX_FLY)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(600.0F), ChargeComponent.getTooltip(60.0F), RangeComponent.getTooltip(30.0F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(50.0F)).setSourceHakiNature(SourceHakiNature.SPECIAL).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private Interval particleInterval = new Interval(2);

   public TenseiNoSoenAbility(AbilityCore<TenseiNoSoenAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.rangeComponent, this.dealDamageComponent, this.continuousComponent});
      this.addCanUseCheck(ToriPhoenixHelper::requiresFlyPoint);
      this.addContinueUseCheck(ToriPhoenixHelper::requiresFlyPoint);
      this.addCanUseCheck(AbilityUseConditions::requiresInAir);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (!this.continuousComponent.isContinuous()) {
         this.particleInterval.restartIntervalToZero();
         this.chargeComponent.startCharging(entity, 60.0F);
      }

   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      if (this.particleInterval.canTick()) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.TENSEI_NO_SOEN_1.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_20096_()) {
         AbilityHelper.setDeltaMovement(entity, entity.m_20184_().f_82479_, (double)-10.0F, entity.m_20184_().f_82481_);
         entity.f_19789_ = 0.0F;
      } else if (entity.m_20096_() || AbilityHelper.getDifferenceToFloor(entity) < (double)2.0F) {
         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.TENSEI_NO_SOEN_2.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());

      for(LivingEntity target : this.rangeComponent.getTargetsInArea(entity, 30.0F)) {
         this.dealDamageComponent.hurtTarget(entity, target, 50.0F);
      }

      this.cooldownComponent.startCooldown(entity, 600.0F);
   }
}
