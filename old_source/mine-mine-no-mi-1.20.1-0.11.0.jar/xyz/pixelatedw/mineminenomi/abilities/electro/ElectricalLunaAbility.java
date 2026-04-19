package xyz.pixelatedw.mineminenomi.abilities.electro;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SwingTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalLunaProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElectricalLunaAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.3F;
   private static final float DAMAGE_BONUS = 2.0F;
   private static final int COOLDOWN = 200;
   private static final int CHARGE_TIME = 40;
   public static final int RANGE = 6;
   private static final int DAMAGE = 40;
   private static final int ELECLAW_STACKS = 2;
   private static final int DORIKI = 3600;
   public static final RegistryObject<AbilityCore<ElectricalLunaAbility>> INSTANCE = ModRegistry.registerAbility("electrical_luna", "Electrical Luna", (id, name) -> {
      Pair[] var10002 = new Pair[]{ImmutablePair.of("Charges a lightning ball in the user's hand which will be used whenever the user swings their arm, shooting the ball of electricity towards their target. On impact the ball will cause a small area of effect that stuns all nearby enemies but only damages the main target.", (Object)null), null};
      Object[] var10006 = new Object[]{MentionHelper.tryMentionObject(SulongAbility.INSTANCE), MentionHelper.mentionText(Math.round(70.0F) + "%"), null};
      float var10009 = Math.abs(-1.0F);
      var10006[2] = MentionHelper.mentionText(Math.round(var10009 * 100.0F) + "%");
      var10002[1] = ImmutablePair.of("While %s is active the cooldown of this ability is reduced by %s and the damage is increased by %s.", var10006);
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, var10002);
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, ElectricalLunaAbility::new)).addDescriptionLine(desc[0]).addAdvancedDescriptionLine(AbilityDescriptionLine.IDescriptionLine.of(desc[1])).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ChargeComponent.getTooltip(40.0F), RangeComponent.getTooltip(6.0F, RangeComponent.RangeType.AOE)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.LIGHTNING).setNodeFactories(ElectricalLunaAbility::createNode).build("mineminenomi");
   });
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addEndEvent(this::endContinuityEvent);
   private final SwingTriggerComponent swingTriggerComponent = (new SwingTriggerComponent(this)).addSwingEvent(this::swingEvent);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);

   public ElectricalLunaAbility(AbilityCore<ElectricalLunaAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.chargeComponent, this.continuousComponent, this.swingTriggerComponent, this.projectileComponent});
      this.addCanUseCheck(ElectroHelper.requireEleclaw(2));
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (!this.chargeComponent.isCharging() && !this.continuousComponent.isContinuous()) {
         this.chargeComponent.startCharging(entity, 40.0F);
      }
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 0, false, false));
      if (this.chargeComponent.getChargeTime() % 2.0F == 0.0F) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.ELECTRO_CHARGING.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      boolean hasSulongActive = ElectroHelper.hasSulongActive(entity);
      this.projectileComponent.getDamageBonusManager().removeBonus(ElectroHelper.SULONG_DAMAGE_BONUS);
      this.cooldownComponent.getBonusManager().removeBonus(ElectroHelper.SULONG_COOLDOWN_BONUS);
      if (hasSulongActive) {
         this.projectileComponent.getDamageBonusManager().addBonus(ElectroHelper.SULONG_DAMAGE_BONUS, "Sulong Damage Bonus", BonusOperation.MUL, 2.0F);
         this.cooldownComponent.getBonusManager().addBonus(ElectroHelper.SULONG_COOLDOWN_BONUS, "Sulong Cooldown Bonus", BonusOperation.MUL, 0.3F);
      }

      this.continuousComponent.startContinuity(entity);
      EleclawAbility eleclawAbility = (EleclawAbility)AbilityCapability.getEquippedAbility(entity, (AbilityCore)EleclawAbility.INSTANCE.get());
      if (eleclawAbility != null) {
         eleclawAbility.reduceUsage(entity, 2);
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 200.0F);
   }

   private void swingEvent(LivingEntity entity, IAbility ability) {
      if (this.continuousComponent.isContinuous()) {
         this.continuousComponent.stopContinuity(entity);
         this.projectileComponent.shoot(entity, 3.0F, 0.0F);
      }

   }

   private ElectricalLunaProjectile createProjectile(LivingEntity entity) {
      ElectricalLunaProjectile proj = new ElectricalLunaProjectile(entity.m_9236_(), entity, this);
      proj.setDamage(40.0F);
      proj.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 3.0F, 0.0F);
      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(11.0F, 7.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.MINK.get()).and(DorikiUnlockCondition.atLeast((double)3600.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)ElectricalMissileAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
