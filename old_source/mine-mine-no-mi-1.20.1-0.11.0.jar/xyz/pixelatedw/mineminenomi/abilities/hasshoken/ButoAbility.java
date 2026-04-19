package xyz.pixelatedw.mineminenomi.abilities.hasshoken;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.DashAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ButoAbility extends DashAbility {
   private static final float COOLDOWN = 400.0F;
   private static final float HOLD_TIME = 10.0F;
   private static final float RANGE = 1.8F;
   private static final float DAMAGE = 30.0F;
   private static final int DORIKI = 7000;
   private static final int MARTIAL_ARTS_POINTS = 50;
   public static final RegistryObject<AbilityCore<ButoAbility>> INSTANCE = ModRegistry.registerAbility("buto", "Buto", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user jumps in reverse and headbutts the enemy.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, ButoAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), ContinuousComponent.getTooltip(10.0F), RangeComponent.getTooltip(1.8F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(30.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.SHOCKWAVE).setNodeFactories(ButoAbility::createNode).setUnlockCheck(ButoAbility::canUnlock).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);

   public ButoAbility(AbilityCore<ButoAbility> core) {
      super(core);
      super.addComponents(new AbilityComponent[]{this.continuousComponent, this.animationComponent, this.rangeComponent, this.dealDamageComponent, this.hitTrackerComponent});
      super.continuousComponent.addStartEvent(this::onContinuityStart);
      super.continuousComponent.addEndEvent(this::onContinuityEnd);
   }

   public void onTargetHit(LivingEntity entity, LivingEntity target, float damage, DamageSource source) {
      AbilityHelper.setDeltaMovement(target, (double)this.getSpeed(), 0.2, (double)this.getSpeed());
   }

   public float getDashCooldown() {
      return 400.0F;
   }

   public float getDamage() {
      return 30.0F;
   }

   public float getSpeed() {
      return -2.0F;
   }

   public float getYSpeed() {
      return 0.0F;
   }

   public float getYBump() {
      return 0.3F;
   }

   private void onContinuityStart(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.BUTO);
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode buto = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-12.0F, 0.0F));
      NodeUnlockCondition unlockCondition = DorikiUnlockCondition.atLeast((double)7000.0F).and(TrainingPointsUnlockCondition.martialArts((double)50.0F));
      NodeUnlockAction unlockAction = SubtractPointsAction.martialArts(50).andThen(UnlockAbilityAction.unlock(INSTANCE));
      buto.addPrerequisites(((AbilityCore)HasshokenPassiveBonusesAbility.INSTANCE.get()).getNode(entity));
      buto.setUnlockRule(unlockCondition, unlockAction);
      return buto;
   }

   private static boolean canUnlock(LivingEntity entity) {
      return ((AbilityCore)INSTANCE.get()).getNode(entity).isUnlocked(entity);
   }
}
