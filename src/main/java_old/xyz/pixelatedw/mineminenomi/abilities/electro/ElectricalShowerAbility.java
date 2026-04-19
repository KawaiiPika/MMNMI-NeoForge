package xyz.pixelatedw.mineminenomi.abilities.electro;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ProjectileComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RepeaterComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.MentionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.entities.LightningDischargeEntity;
import xyz.pixelatedw.mineminenomi.entities.projectiles.abilities.electro.ElectricalShowerProjectile;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ElectricalShowerAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.3F;
   private static final float DAMAGE_BONUS = 1.25F;
   private static final int COOLDOWN = 240;
   private static final int CHARGE_TIME = 60;
   private static final int DAMAGE = 20;
   private static final int ELECLAW_STACKS = 2;
   private static final int DORIKI = 7000;
   public static final RegistryObject<AbilityCore<ElectricalShowerAbility>> INSTANCE = ModRegistry.registerAbility("electrical_shower", "Electrical Shower", (id, name) -> {
      Pair[] var10002 = new Pair[]{ImmutablePair.of("Launches the user into the air and showers down lightning bolts underneath.", (Object)null), null};
      Object[] var10006 = new Object[]{MentionHelper.tryMentionObject(SulongAbility.INSTANCE), MentionHelper.mentionText(Math.round(70.0F) + "%"), null};
      float var10009 = Math.abs(-0.25F);
      var10006[2] = MentionHelper.mentionText(Math.round(var10009 * 100.0F) + "%");
      var10002[1] = ImmutablePair.of("While %s is active the cooldown of this ability is reduced by %s and the damage is increased by %s.", var10006);
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, var10002);
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, ElectricalShowerAbility::new)).addDescriptionLine(desc[0]).addAdvancedDescriptionLine(AbilityDescriptionLine.IDescriptionLine.of(desc[1])).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ChargeComponent.getTooltip(60.0F)).addAdvancedDescriptionLine(ProjectileComponent.getProjectileTooltips()).setSourceHakiNature(SourceHakiNature.SPECIAL).setSourceElement(SourceElement.LIGHTNING).setNodeFactories(ElectricalShowerAbility::createNode).build("mineminenomi");
   });
   private final StackComponent stackComponent = new StackComponent(this);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addTickEvent(this::duringChargeEvent).addEndEvent(this::endChargeEvent);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTaken);
   private final ProjectileComponent projectileComponent = new ProjectileComponent(this, this::createProjectile);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final RepeaterComponent repeaterComponent = (new RepeaterComponent(this)).addTriggerEvent(this::triggerRepeaterEvent).addStopEvent(this::stopRepeaterEvent);
   private LightningDischargeEntity ballEntity = null;
   boolean hasFallDamage = true;

   public ElectricalShowerAbility(AbilityCore<ElectricalShowerAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.stackComponent, this.chargeComponent, this.damageTakenComponent, this.projectileComponent, this.continuousComponent, this.repeaterComponent});
      this.addCanUseCheck(ElectroHelper.requireEleclaw(2));
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (this.isContinuous()) {
         this.continuousComponent.stopContinuity(entity);
      } else {
         this.chargeComponent.startCharging(entity, 60.0F);
      }
   }

   private void duringChargeEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 0, false, false));
      if (this.chargeComponent.getChargeTime() % 5.0F == 0.0F) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.ELECTRO_CHARGING.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

      float percentage = 1.0F - this.chargeComponent.getChargeTime() / this.chargeComponent.getMaxChargeTime();
      if (this.ballEntity == null) {
         LightningDischargeEntity ball = new LightningDischargeEntity(entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146908_(), entity.m_146909_());
         this.ballEntity = ball;
      } else {
         float distance = percentage * 2.0F;
         Vec3 lookVec = entity.m_20154_();
         double px = entity.m_20185_() + lookVec.f_82479_ * (double)distance;
         double py = entity.m_20188_() * 0.85 + lookVec.f_82480_ * (double)distance;
         double pz = entity.m_20189_() + lookVec.f_82481_ * (double)distance;
         Vec3 pos = new Vec3(px, py, pz);
         this.ballEntity.setSize(percentage * 0.3F);
         this.ballEntity.setLightningLength(3.0F);
         this.ballEntity.m_7678_(pos.m_7096_(), pos.m_7098_(), pos.m_7094_(), entity.m_146908_(), entity.m_146909_());
      }

      if ((double)percentage > 0.65) {
         Vec3 startVec = entity.m_20182_();
         boolean blockUnder = entity.m_9236_().m_45547_(new ClipContext(startVec, startVec.m_82520_((double)0.0F, (double)-15.0F, (double)0.0F), Block.COLLIDER, Fluid.NONE, entity)).m_6662_().equals(Type.BLOCK);
         if (blockUnder) {
            AbilityHelper.setDeltaMovement(entity, entity.m_20184_().f_82479_, (double)1.0F, entity.m_20184_().f_82481_);
         }
      }

      AbilityHelper.slowEntityFall(entity);
   }

   private void endChargeEvent(LivingEntity entity, IAbility ability) {
      if (this.ballEntity != null) {
         this.ballEntity.m_146870_();
         this.ballEntity = null;
      }

      EleclawAbility eleclaw = (EleclawAbility)AbilityCapability.getEquippedAbility(entity, (AbilityCore)EleclawAbility.INSTANCE.get());
      if (eleclaw != null) {
         eleclaw.reduceUsage(entity, 2);
      }

      this.continuousComponent.startContinuity(entity);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hasFallDamage = false;
      this.repeaterComponent.start(entity, 10, 5);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      AbilityHelper.slowEntityFall(entity);
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 240.0F);
   }

   private void triggerRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.projectileComponent.shoot(entity, 3.0F, 2.0F);
      entity.m_21011_(InteractionHand.MAIN_HAND, true);
   }

   private void stopRepeaterEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (!this.hasFallDamage && damageSource.m_276093_(DamageTypes.f_268671_)) {
         this.hasFallDamage = true;
         return 0.0F;
      } else {
         return damage;
      }
   }

   private ElectricalShowerProjectile createProjectile(LivingEntity entity) {
      ElectricalShowerProjectile proj = new ElectricalShowerProjectile(entity.m_9236_(), entity, this);
      proj.setDamage(20.0F);
      proj.m_37251_(entity, entity.m_146909_(), entity.m_146908_(), 0.0F, 3.0F, 0.0F);
      return proj;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(11.0F, 11.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.MINK.get()).and(DorikiUnlockCondition.atLeast((double)7000.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)ElectricalTempestaAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
