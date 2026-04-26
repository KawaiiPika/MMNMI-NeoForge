package xyz.pixelatedw.mineminenomi.abilities.kilo;

import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;

public class KiloPress1Ability extends Ability {
   private static final float HOLD_TIME = 1200.0F;
   private static final float MIN_COOLDOWN = 20.0F;
   private static final float MAX_COOLDOWN = 1220.0F;
   public static final RegistryObject<AbilityCore<KiloPress1Ability>> INSTANCE = ModRegistry.registerAbility("1_kilo_press", "1 Kilo Press", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Makes the user become extremely light, their jumps become higher and they can use an Umbrella to float", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, KiloPress1Ability::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(20.0F, 1220.0F), ContinuousComponent.getTooltip(1200.0F), ChangeStatsComponent.getTooltip()).build("mineminenomi");
   });
   private static final AbilityAttributeModifier KILO_PRESS_JUMP_HEIGHT;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(100, this::startContinuityEvent).addTickEvent(100, this::tickContinuityEvent).addEndEvent(100, this::endContinuityEvent);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);
   private final ChangeStatsComponent statsComponent = new ChangeStatsComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public KiloPress1Ability(AbilityCore<KiloPress1Ability> core) {
      super(core);
      this.statsComponent.addAttributeModifier((Supplier)ModAttributes.JUMP_HEIGHT, KILO_PRESS_JUMP_HEIGHT, (entity) -> this.continuousComponent.isContinuous());
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.statsComponent, this.damageTakenComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 1200.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.KILO_PRESS);
      this.animationComponent.pause(entity);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.getContinueTime() > 10.0F && entity.m_20096_()) {
         this.continuousComponent.stopContinuity(entity);
      } else {
         boolean hasUmbrella = entity.m_21205_().m_41720_() == ModWeapons.UMBRELLA.get() || entity.m_21206_().m_41720_() == ModWeapons.UMBRELLA.get();
         boolean inAir = !entity.m_20096_() && entity.m_20184_().f_82480_ < (double)0.0F;
         if (hasUmbrella && inAir) {
            AbilityHelper.setDeltaMovement(entity, entity.m_20184_().f_82479_, entity.m_20184_().f_82480_ / (double)2.0F, entity.m_20184_().f_82481_);
         }

         if (!entity.m_9236_().f_46443_) {
            boolean shouldPause = entity.m_20096_() || entity.f_19789_ <= 0.0F || !hasUmbrella;
            if (this.animationComponent.isPlaying() && shouldPause) {
               this.animationComponent.pause(entity);
            } else if (this.animationComponent.isPaused() && !shouldPause) {
               this.animationComponent.resume(entity);
            }
         }

      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      float cooldown = 20.0F + this.continuousComponent.getContinueTime();
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      return this.continuousComponent.isContinuous() && damageSource.m_276093_(DamageTypes.f_268671_) ? 0.0F : damage;
   }

   static {
      KILO_PRESS_JUMP_HEIGHT = new AbilityAttributeModifier(UUID.fromString("8626d3b9-c2ed-470e-94aa-280e2ceff116"), INSTANCE, "Kilo Press Jump Height Modifier", 4.8, Operation.ADDITION);
   }
}
