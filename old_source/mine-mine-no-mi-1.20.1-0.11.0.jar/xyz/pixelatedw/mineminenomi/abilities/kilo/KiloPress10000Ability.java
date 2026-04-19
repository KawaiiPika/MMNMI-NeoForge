package xyz.pixelatedw.mineminenomi.abilities.kilo;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KiloPress10000Ability extends Ability {
   private static final float HOLD_TIME = 1200.0F;
   private static final float MIN_COOLDOWN = 20.0F;
   private static final float MAX_COOLDOWN = 1220.0F;
   private static final float MIN_DAMAGE = 1.0F;
   private static final float MAX_DAMAGE = 80.0F;
   private static final float RANGE = 5.0F;
   public static final RegistryObject<AbilityCore<KiloPress10000Ability>> INSTANCE = ModRegistry.registerAbility("10000_kilo_press", "10000 Kilo Press", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Makes the user become extremely heavy, crashing down on enemies from above crushes them, damage is calculated based on the fall distance.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KiloPress10000Ability::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(20.0F, 1220.0F), ContinuousComponent.getTooltip(1200.0F), DealDamageComponent.getTooltip(1.0F, 80.0F), RangeComponent.getTooltip(5.0F, RangeComponent.RangeType.AOE), ChangeStatsComponent.getTooltip()).setSourceHakiNature(SourceHakiNature.HARDENING).build("mineminenomi");
   });
   private static final AbilityAttributeModifier KILO_PRESS_JUMP_HEIGHT;
   private static final AbilityAttributeModifier KILO_PRESS_KNOCKBACK;
   private static final AbilityAttributeModifier KILO_PRESS_MOVEMENT_SPEED;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuityEvent).addTickEvent(100, this::tickContinuityEvent).addEndEvent(100, this::endContinuityEvent);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final ChangeStatsComponent changeStatsComponent = new ChangeStatsComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private double initialPosY = (double)0.0F;

   public KiloPress10000Ability(AbilityCore<KiloPress10000Ability> core) {
      super(core);
      Predicate<LivingEntity> isContinuityActive = (entity) -> this.continuousComponent.isContinuous();
      this.changeStatsComponent.addAttributeModifier((Supplier)ModAttributes.JUMP_HEIGHT, KILO_PRESS_JUMP_HEIGHT, isContinuityActive);
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22278_, KILO_PRESS_KNOCKBACK, isContinuityActive);
      this.changeStatsComponent.addAttributeModifier(Attributes.f_22279_, KILO_PRESS_MOVEMENT_SPEED, isContinuityActive);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.damageTakenComponent, this.dealDamageComponent, this.rangeComponent, this.changeStatsComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresAirTime);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 1200.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.KILO_PRESS);
      this.changeStatsComponent.applyModifiers(entity);
      AbilityHelper.setDeltaMovement(entity, entity.m_20184_().f_82479_, (double)-5.0F, entity.m_20184_().f_82481_);
      if (!entity.m_20096_()) {
         this.initialPosY = entity.m_20186_();
      } else {
         this.initialPosY = (double)0.0F;
      }

   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_20096_()) {
         if (this.initialPosY > (double)0.0F && entity.m_20186_() < this.initialPosY) {
            float damage = (float)Mth.m_14008_(this.initialPosY - entity.m_20186_(), (double)1.0F, (double)80.0F);
            if (damage > 0.0F) {
               List<LivingEntity> nearTargets = this.rangeComponent.getTargetsInArea(entity, 1.0F);

               for(LivingEntity target : nearTargets) {
                  this.dealDamageComponent.hurtTarget(entity, target, damage);
               }

               List<LivingEntity> farTargets = this.rangeComponent.getTargetsInArea(entity, 5.0F);
               farTargets.removeAll(nearTargets);
               DamageSource farSource = this.dealDamageComponent.getDamageSource(entity);

               for(LivingEntity target : farTargets) {
                  this.dealDamageComponent.hurtTarget(entity, target, damage, farSource);
               }

               this.initialPosY = (double)0.0F;
            }

            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GREAT_STOMP.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         }

         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      this.changeStatsComponent.removeModifiers(entity);
      float cooldown = 20.0F + this.continuousComponent.getContinueTime();
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      return this.continuousComponent.isContinuous() && damageSource.m_276093_(DamageTypes.f_268671_) ? 0.0F : damage;
   }

   static {
      KILO_PRESS_JUMP_HEIGHT = new AbilityAttributeModifier(UUID.fromString("692759d2-5d8d-4809-912d-86ad362f8f95"), INSTANCE, "Kilo Press Jump Height Modifier", (double)-10.0F, Operation.ADDITION);
      KILO_PRESS_KNOCKBACK = new AbilityAttributeModifier(UUID.fromString("f3597992-9268-4a40-9363-555cf06c7771"), INSTANCE, "Kilo Press Knockback Modifier", (double)1.0F, Operation.ADDITION);
      KILO_PRESS_MOVEMENT_SPEED = new AbilityAttributeModifier(UUID.fromString("d668cefb-4e31-4e7b-842b-7a1c8de82f69"), INSTANCE, "Kilo Press Movement Speed Modifier", -0.02, Operation.ADDITION);
   }
}
