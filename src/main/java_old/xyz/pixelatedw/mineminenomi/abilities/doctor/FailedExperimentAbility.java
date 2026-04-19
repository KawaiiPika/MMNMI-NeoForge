package xyz.pixelatedw.mineminenomi.abilities.doctor;

import com.google.common.collect.Lists;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AltModeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class FailedExperimentAbility extends Ability {
   private static final int COOLDOWN = 240;
   public static final RegistryObject<AbilityCore<FailedExperimentAbility>> INSTANCE = ModRegistry.registerAbility("failed_experiment", "Failed Experiment", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Throws a random splash potion with a debuff effect at the enemy.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, FailedExperimentAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F)).setNodeFactories(FailedExperimentAbility::createNode).build("mineminenomi");
   });
   private final AltModeComponent<Mode> altModeComponent;
   private ItemStack stack;

   public FailedExperimentAbility(AbilityCore<FailedExperimentAbility> core) {
      super(core);
      this.altModeComponent = (new AltModeComponent<Mode>(this, Mode.class, FailedExperimentAbility.Mode.ATTACK_SPEED)).addChangeModeEvent(this::onAltModeChange);
      this.stack = new ItemStack(Items.f_42736_);
      this.addCanUseCheck(AbilityUseConditions::requiresMedicBag);
      super.addComponents(this.altModeComponent);
      super.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity livingEntity, IAbility ability) {
      if (this.altModeComponent.getCurrentMode() == FailedExperimentAbility.Mode.ATTACK_SPEED) {
         this.stack = PotionUtils.m_43552_(this.stack, Lists.newArrayList(new MobEffectInstance[]{new MobEffectInstance(MobEffects.f_19599_, 200, 1)}));
      }

      ThrownPotion potion = new ThrownPotion(livingEntity.m_9236_(), livingEntity);
      potion.m_37446_(this.stack);
      potion.m_146926_(potion.m_146909_() - 20.0F);
      potion.m_37251_(livingEntity, livingEntity.m_146909_(), livingEntity.m_146908_(), -20.0F, 1.0F, 0.0F);
      livingEntity.m_9236_().m_7967_(potion);
      if (livingEntity instanceof Player player) {
         ItemStack medicBag = (ItemStack)player.m_150109_().f_35975_.get(2);
         medicBag.m_41622_(10, player, (user) -> user.m_21166_(EquipmentSlot.MAINHAND));
      }

      super.cooldownComponent.startCooldown(livingEntity, 240.0F);
   }

   private boolean onAltModeChange(LivingEntity entity, IAbility ability, Mode mode) {
      this.stack = PotionUtils.m_43552_(this.stack, Lists.newArrayList(new MobEffectInstance[]{new MobEffectInstance(mode.effect, 200, 1)}));
      return true;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-15.0F, -4.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.DOCTOR.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }

   public static enum Mode {
      CONFUSION(MobEffects.f_19604_),
      ATTACK_SPEED(MobEffects.f_19599_),
      POISON(MobEffects.f_19614_),
      HUNGER(MobEffects.f_19612_),
      SLOWNESS(MobEffects.f_19597_),
      BLINDNESS(MobEffects.f_19610_);

      private MobEffect effect;

      private Mode(MobEffect effect) {
         this.effect = effect;
      }

      public MobEffect getEffect() {
         return this.effect;
      }

      // $FF: synthetic method
      private static Mode[] $values() {
         return new Mode[]{CONFUSION, ATTACK_SPEED, POISON, HUNGER, SLOWNESS, BLINDNESS};
      }
   }
}
