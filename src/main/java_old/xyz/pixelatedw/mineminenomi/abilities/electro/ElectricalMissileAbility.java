package xyz.pixelatedw.mineminenomi.abilities.electro;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.DashAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class ElectricalMissileAbility extends DashAbility {
   private static final float COOLDOWN_BONUS = 0.5F;
   private static final float DAMAGE_BONUS = 2.5F;
   private static final int COOLDOWN = 180;
   private static final float RANGE = 1.6F;
   private static final int DAMAGE = 20;
   private static final int ELECLAW_STACKS = 1;
   private static final int DORIKI = 800;
   public static final RegistryObject<AbilityCore<ElectricalMissileAbility>> INSTANCE = ModRegistry.registerAbility("electrical_missile", "Electrical Missile", (id, name) -> {
      Pair[] var10002 = new Pair[]{ImmutablePair.of("Powerful and fast forward dash that will stun enemies.", (Object)null), null};
      Object[] var10006 = new Object[]{MentionHelper.tryMentionObject(SulongAbility.INSTANCE), MentionHelper.mentionText(Math.round(50.0F) + "%"), null};
      float var10009 = Math.abs(-1.5F);
      var10006[2] = MentionHelper.mentionText(Math.round(var10009 * 100.0F) + "%");
      var10002[1] = ImmutablePair.of("While %s is active the cooldown of this ability is reduced by %s and the damage is increased by %s.", var10006);
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, var10002);
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, ElectricalMissileAbility::new)).addDescriptionLine(desc[0]).addAdvancedDescriptionLine(AbilityDescriptionLine.IDescriptionLine.of(desc[1])).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(180.0F), RangeComponent.getTooltip(1.6F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setSourceElement(SourceElement.LIGHTNING).setNodeFactories(ElectricalMissileAbility::createNode).build("mineminenomi");
   });
   private float dashSpeed = 4.0F;

   public ElectricalMissileAbility(AbilityCore<ElectricalMissileAbility> core) {
      super(core);
      this.addCanUseCheck(ElectroHelper.requireEleclaw(1));
      this.continuousComponent.addStartEvent(this::startContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      boolean hasSulongActive = ElectroHelper.hasSulongActive(entity);
      this.dashSpeed = hasSulongActive ? 5.0F : 4.0F;
      this.dealDamageComponent.getBonusManager().removeBonus(ElectroHelper.SULONG_DAMAGE_BONUS);
      this.cooldownComponent.getBonusManager().removeBonus(ElectroHelper.SULONG_COOLDOWN_BONUS);
      if (hasSulongActive) {
         this.dealDamageComponent.getBonusManager().addBonus(ElectroHelper.SULONG_DAMAGE_BONUS, "Sulong Damage Bonus", BonusOperation.MUL, 2.5F);
         this.cooldownComponent.getBonusManager().addBonus(ElectroHelper.SULONG_COOLDOWN_BONUS, "Sulong Cooldown Bonus", BonusOperation.MUL, 0.5F);
      }

      entity.m_21011_(InteractionHand.MAIN_HAND, true);
      EleclawAbility eleclawAbility = (EleclawAbility)AbilityCapability.getEquippedAbility(entity, (AbilityCore)EleclawAbility.INSTANCE.get());
      if (eleclawAbility != null) {
         eleclawAbility.reduceUsage(entity, 1);
      }

   }

   public void onTargetHit(LivingEntity entity, LivingEntity target, float damage, DamageSource source) {
      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.PARALYSIS.get(), 40, 0, false, false, true));
   }

   public float getDashCooldown() {
      return 180.0F;
   }

   public float getDamage() {
      return 20.0F;
   }

   public float getRange() {
      return 1.6F;
   }

   public float getSpeed() {
      return this.dashSpeed;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(9.0F, 8.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.MINK.get()).and(DorikiUnlockCondition.atLeast((double)800.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)EleclawAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
