package xyz.pixelatedw.mineminenomi.abilities.saraaxolotl;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class PlayDeadAbility extends Ability {
   private static final int HOLD_TIME = 200;
   private static final int MIN_COOLDOWN = 200;
   private static final int MAX_COOLDOWN = 400;
   public static final RegistryObject<AbilityCore<PlayDeadAbility>> INSTANCE = ModRegistry.registerAbility("play_dead", "Play Dead", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("While playing dead the user focuses all of their power into regeneration.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, PlayDeadAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.AXOLOTL_WALK)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F, 400.0F), ContinuousComponent.getTooltip(200.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnHurtEvent(this::onHurtEvent);

   public PlayDeadAbility(AbilityCore<PlayDeadAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.animationComponent, this.damageTakenComponent});
      this.addCanUseCheck(SaraAxolotlHelper::requiresWalkPoint);
      this.addContinueUseCheck(SaraAxolotlHelper::requiresWalkPoint);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 200.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.PLAY_DEAD, 200);
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.PLAY_DEAD.get(), 200, 0, false, false));
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 2, false, false));
      AbilityHelper.disableAbilities(entity, 2, (abl) -> !abl.equals(this) && !abl.getCore().equals(AxolotlWalkPointAbility.INSTANCE.get()));
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      entity.m_21195_((MobEffect)ModEffects.PLAY_DEAD.get());
      this.cooldownComponent.startCooldown(entity, 200.0F + this.continuousComponent.getContinueTime());
   }

   private float onHurtEvent(LivingEntity target, IAbility ability, DamageSource source, float amount) {
      return this.continuousComponent.isContinuous() ? amount * 0.75F : amount;
   }
}
