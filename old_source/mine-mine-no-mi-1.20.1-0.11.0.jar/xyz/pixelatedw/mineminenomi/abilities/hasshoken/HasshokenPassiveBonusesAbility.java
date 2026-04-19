package xyz.pixelatedw.mineminenomi.abilities.hasshoken;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityType;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PassiveAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.SubtractPointsAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.TrainingPointsUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;

public class HasshokenPassiveBonusesAbility extends PassiveAbility {
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(this::tryHitEvent).addOnHitEvent(this::onHitEvent);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnHurtEvent(this::onHurtEvent);
   private static final int DORIKI = 6500;
   private static final int MARTIAL_ARTS_POINTS = 50;
   public static final RegistryObject<AbilityCore<HasshokenPassiveBonusesAbility>> INSTANCE = ModRegistry.registerAbility("hasshoken_passive_bonuses", "Hassoken Passive Bonuses", (id, name) -> (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, AbilityType.PASSIVE, HasshokenPassiveBonusesAbility::new)).setIcon(ModResources.PERK_ICON).addDescriptionLine(ChangeStatsComponent.getTooltip()).setNodeFactories(HasshokenPassiveBonusesAbility::createNode).setUnlockCheck(HasshokenPassiveBonusesAbility::canUnlock).setSourceType(SourceType.INTERNAL).build("mineminenomi"));

   public HasshokenPassiveBonusesAbility(AbilityCore<HasshokenPassiveBonusesAbility> core) {
      super(core);
      super.addComponents(this.hitTriggerComponent, this.dealDamageComponent, this.damageTakenComponent);
   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      return !super.isAbilityPaused() && AbilityUseConditions.requiresEmptyHand(entity, this).isSuccess() ? HitTriggerComponent.HitResult.HIT : HitTriggerComponent.HitResult.PASS;
   }

   private boolean onHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
      sourceHandler.bypassLogia();
      sourceHandler.addAbilityPiercing(1.0F, this.getCore());
      sourceHandler.addAbilityDamage(7.0F, ability.getCore());
      AbilityHelper.setDeltaMovement(target, target.m_20182_().m_82546_(entity.m_20182_()).m_82541_().m_82490_(0.6).m_82520_((double)0.0F, (double)0.5F, (double)0.0F));
      return true;
   }

   private float onHurtEvent(LivingEntity user, IAbility ability, DamageSource damageSource, float damage) {
      if (AbilityHelper.isGuardBlocking(user)) {
         Entity attacker = damageSource.m_7640_();
         AbilityHelper.setDeltaMovement(attacker, attacker.m_20182_().m_82546_(user.m_20182_()).m_82541_().m_82490_(0.6).m_82520_((double)0.0F, (double)0.5F, (double)0.0F));
      }

      return damage;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-17.0F, -2.0F));
      NodeUnlockCondition unlockCondition = DorikiUnlockCondition.atLeast((double)6500.0F).and(TrainingPointsUnlockCondition.martialArts((double)50.0F));
      NodeUnlockAction unlockAction = SubtractPointsAction.martialArts(50).andThen(UnlockAbilityAction.unlock(INSTANCE));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }

   private static boolean canUnlock(LivingEntity entity) {
      return ((AbilityCore)INSTANCE.get()).getNode(entity).isUnlocked(entity);
   }
}
