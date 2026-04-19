package xyz.pixelatedw.mineminenomi.abilities.hana;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.MorphComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.morph.MorphInfo;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class CienFleurWingAbility extends Ability {
   private static final int HOLD_TIME = 200;
   private static final int MIN_COOLDOWN = 40;
   private static final int MAX_COOLDOWN = 200;
   public static final RegistryObject<AbilityCore<CienFleurWingAbility>> INSTANCE = ModRegistry.registerAbility("cien_fleur_wing", "Cien Fleur: Wing", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Sprouts two big wings made out of limbs on the user's back and allows them to gently float down taking no fall damage.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, CienFleurWingAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(40.0F, 200.0F), ContinuousComponent.getTooltip(200.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::damageTakenEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final MorphComponent morphComponent = new MorphComponent(this);
   private Vec3 initialMovement;

   public CienFleurWingAbility(AbilityCore<CienFleurWingAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.damageTakenComponent, this.animationComponent, this.morphComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresAirTime);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 200.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      HanaHelper.spawnBlossomEffect(entity);
      this.initialMovement = entity.m_20184_();
      this.animationComponent.start(entity, ModAnimations.CROSSED_ARMS);
      this.morphComponent.startMorph(entity, (MorphInfo)ModMorphs.HANA_WINGS.get());
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_20096_() || this.initialMovement == null) {
         this.continuousComponent.stopContinuity(entity);
      }

      entity.f_19789_ = 0.0F;
      AbilityHelper.setDeltaMovement(entity, this.initialMovement.f_82479_ * 0.8, entity.m_20184_().f_82480_ * 0.7, this.initialMovement.f_82481_ * 0.8);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.morphComponent.stopMorph(entity);
      float cooldown = Math.max(40.0F, this.continuousComponent.getContinueTime());
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   private float damageTakenEvent(LivingEntity user, IAbility ability, DamageSource damageSource, float damage) {
      return this.continuousComponent.isContinuous() && damageSource.m_276093_(DamageTypes.f_268671_) ? 0.0F : damage;
   }
}
