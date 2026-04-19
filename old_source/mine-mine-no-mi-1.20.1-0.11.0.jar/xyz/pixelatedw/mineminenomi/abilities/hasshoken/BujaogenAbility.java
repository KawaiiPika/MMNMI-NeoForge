package xyz.pixelatedw.mineminenomi.abilities.hasshoken;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BujaogenAbility extends Ability {
   private static final float DAMAGE = 30.0F;
   private static final float CHARGE_TIME = 10.0F;
   private static final float COOLDOWN = 200.0F;
   public static final RegistryObject<AbilityCore<BujaogenAbility>> INSTANCE = ModRegistry.registerAbility("bujaogen", "Bujaogen", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user kicks their target while emitting the Hasshoken shock wave. This allows them to shatter defense while kicking through their target.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, BujaogenAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ChargeComponent.getTooltip(10.0F), DealDamageComponent.getTooltip(30.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.SHOCKWAVE).setNodeFactories(BujaogenAbility::createNode).setUnlockCheck(BujaogenAbility::canUnlock).build("mineminenomi");
   });
   private static final int DORIKI = 8000;
   private static final int MARTIAL_ARTS_POINTS = 50;
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addEndEvent(this::onChargeEnd);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);

   public BujaogenAbility(AbilityCore<BujaogenAbility> core) {
      super(core);
      super.addComponents(this.animationComponent, this.chargeComponent, this.rangeComponent, this.dealDamageComponent);
      super.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.chargeComponent.startCharging(entity, 10.0F);
   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.HAND_STAND_SPIN);
   }

   private void onChargeEnd(LivingEntity entity, IAbility ability) {
      float range = (float)entity.m_21051_((Attribute)ForgeMod.ENTITY_REACH.get()).m_22135_();
      float width = entity.m_20205_() / 2.0F + 0.5F;
      List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, range, width);
      targets.addAll(this.rangeComponent.getTargetsInArea(entity, width));
      Stream var10000 = targets.stream();
      Objects.requireNonNull(entity);
      LivingEntity target = (LivingEntity)var10000.min(Comparator.comparingDouble(entity::m_20270_)).orElse((Object)null);
      if (target != null && this.dealDamageComponent.hurtTarget(entity, target, 30.0F)) {
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 20, 0, false, false));
         AbilityHelper.setDeltaMovement(target, entity.m_20184_().f_82479_, (double)-5.0F, entity.m_20184_().f_82481_);
      }

      this.animationComponent.stop(entity);
      super.cooldownComponent.startCooldown(entity, 200.0F);
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode bujaogen = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-22.0F, 0.0F));
      NodeUnlockCondition unlockCondition = DorikiUnlockCondition.atLeast((double)8000.0F).and(TrainingPointsUnlockCondition.martialArts((double)50.0F));
      NodeUnlockAction unlockAction = SubtractPointsAction.martialArts(50).andThen(UnlockAbilityAction.unlock(INSTANCE));
      bujaogen.addPrerequisites(((AbilityCore)HasshokenPassiveBonusesAbility.INSTANCE.get()).getNode(entity));
      bujaogen.setUnlockRule(unlockCondition, unlockAction);
      return bujaogen;
   }

   private static boolean canUnlock(LivingEntity entity) {
      return ((AbilityCore)INSTANCE.get()).getNode(entity).isUnlocked(entity);
   }
}
