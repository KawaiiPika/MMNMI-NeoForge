package xyz.pixelatedw.mineminenomi.api.abilities.components;

import java.util.UUID;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.AbilityMobEffectInstance;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.helpers.EffectsHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public class MobEffectComponent extends AbilityComponent<IAbility> {
   private static final UUID EFFECT_TIME_BONUS_MANAGER_UUID = UUID.fromString("2cd2e9a0-6085-49e0-a37a-f10bb88cf489");
   private static final UUID EFFECT_AMP_BONUS_MANAGER_UUID = UUID.fromString("288e05b5-71b7-420b-8ecc-f369cd567b3e");
   private ICheckMobEffectEvent checkMobEffectEvent = (player, effect) -> true;
   private final BonusManager timeBonusManager;
   private final BonusManager ampBonusManager;

   public MobEffectComponent(IAbility ability) {
      super((AbilityComponentKey)ModAbilityComponents.MOB_EFFECT.get(), ability);
      this.timeBonusManager = new BonusManager(EFFECT_TIME_BONUS_MANAGER_UUID);
      this.ampBonusManager = new BonusManager(EFFECT_AMP_BONUS_MANAGER_UUID);
      this.addBonusManager(this.timeBonusManager);
      this.addBonusManager(this.ampBonusManager);
   }

   public void applyEffect(LivingEntity entity, MobEffect effect, int duration, int amp) {
      duration = Math.round(this.timeBonusManager.applyBonus((float)duration));
      amp = Math.round(this.ampBonusManager.applyBonus((float)amp));
      AbilityMobEffectInstance inst = new AbilityMobEffectInstance(this.getAbility(), effect, duration, amp, false, false, true);
      entity.m_7292_(inst);
   }

   public void removeEffect(LivingEntity entity, MobEffect effect) {
      EffectsHelper.removeAbilityEffect(entity, effect, this.getAbility());
   }

   public void addTimeToEffect(LivingEntity entity, MobEffect effect, int extraTime) {
      MobEffectInstance inst = entity.m_21124_(effect);
      int duration = Math.max(inst.m_19557_() + extraTime, 0);
      duration = Math.round(this.timeBonusManager.applyBonus((float)duration));
      ((IMobEffectInstanceMixin)inst).setDuration(duration);
   }

   public void addAmplifierToEffect(LivingEntity entity, MobEffect effect, int extraAmp) {
      MobEffectInstance inst = entity.m_21124_(effect);
      int amp = Math.max(inst.m_19564_() + extraAmp, 0);
      amp = Math.round(this.ampBonusManager.applyBonus((float)amp));
      ((IMobEffectInstanceMixin)inst).setAmplifier(amp);
   }

   public void setEffectCheck(ICheckMobEffectEvent event) {
      this.checkMobEffectEvent = event;
   }

   public boolean checkEffect(LivingEntity user, MobEffectInstance effect) {
      return this.checkMobEffectEvent.checkPotion(user, effect);
   }

   public interface ICheckMobEffectEvent {
      boolean checkPotion(LivingEntity var1, MobEffectInstance var2);
   }
}
