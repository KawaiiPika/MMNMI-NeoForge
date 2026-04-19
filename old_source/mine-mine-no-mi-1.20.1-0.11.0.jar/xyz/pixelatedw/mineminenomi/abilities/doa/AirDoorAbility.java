package xyz.pixelatedw.mineminenomi.abilities.doa;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class AirDoorAbility extends Ability {
   private static final int HOLD_TIME = 1200;
   private static final int MIN_COOLDOWN = 200;
   private static final int MAX_COOLDOWN = 800;
   public static final RegistryObject<AbilityCore<AirDoorAbility>> INSTANCE = ModRegistry.registerAbility("air_door", "Air Door", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user travels into an air dimension and is invincible during that time.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, AirDoorAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F, 800.0F), ContinuousComponent.getTooltip(1200.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::damageTakenEvent);

   public AirDoorAbility(AbilityCore<AirDoorAbility> ability) {
      super(ability);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.damageTakenComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 1200.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DOA_INVISIBILITY.get(), 1200, 0, false, false));
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DOA_IN_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_21195_((MobEffect)ModEffects.DOA_INVISIBILITY.get());
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.DOA_OUT_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      float cooldown = 200.0F + this.continuousComponent.getContinueTime() / 2.0F;
      this.cooldownComponent.startCooldown(entity, cooldown);
   }

   public float damageTakenEvent(LivingEntity user, IAbility ability, DamageSource damageSource, float damage) {
      return this.continuousComponent.isContinuous() ? 0.0F : damage;
   }
}
