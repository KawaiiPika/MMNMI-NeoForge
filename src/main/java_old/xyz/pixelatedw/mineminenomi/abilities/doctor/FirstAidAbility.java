package xyz.pixelatedw.mineminenomi.abilities.doctor;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HealComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModArmors;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModNetwork;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SPinCameraPacket;
import xyz.pixelatedw.mineminenomi.packets.server.entity.SUnpinCameraPacket;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class FirstAidAbility extends Ability {
   private static final int MIN_HEAL = 5;
   private static final int MAX_HEAL = 50;
   private static final float HOLD_TIME = 1000.0F;
   private static final float MIN_COOLDOWN = 300.0F;
   private final HealComponent healComponent = new HealComponent(this);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(this::tryHitEvent).addOnHitEvent(this::hitEvent);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnDamageEvent(this::onDamageTaken);
   public static final RegistryObject<AbilityCore<FirstAidAbility>> INSTANCE = ModRegistry.registerAbility("first_aid", "First Aid", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Heals the user by shift-clicking or an ally by punching them.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, FirstAidAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), HealComponent.getTooltip(5.0F, 50.0F)).setSourceType(SourceType.FIST, SourceType.FRIENDLY).setNodeFactories(FirstAidAbility::createNode).build("mineminenomi");
   });
   private LivingEntity target;
   private Interval particleInterval = Interval.startAtMax(20);
   private boolean pinnedCamera = false;

   public FirstAidAbility(AbilityCore<FirstAidAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.hitTriggerComponent, this.healComponent, this.damageTakenComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresMedicBag);
      this.addUseEvent(this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6047_()) {
         this.target = entity;
         this.continuousComponent.startContinuity(entity, 1000.0F);
      } else {
         this.continuousComponent.triggerContinuity(entity);
      }

   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.particleInterval.restartIntervalToZero();
      this.pinnedCamera = false;
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.target != null) {
         if (this.continuousComponent.isInfinite()) {
            this.continuousComponent.setThresholdTime(entity, 1000.0F);
         } else if (this.target != null) {
            if (!this.pinnedCamera && entity instanceof ServerPlayer) {
               ServerPlayer player = (ServerPlayer)entity;
               ModNetwork.sendTo(SPinCameraPacket.pinClampedYawAndPitch(entity.m_146908_(), 10.0F, entity.m_146909_(), 10.0F), player);
               this.pinnedCamera = true;
            }

            this.target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1));
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1));
         }

         if (this.particleInterval.canTick()) {
            this.applyFirstAid(entity, this.target, this);
         }

      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.target = null;
      if (entity instanceof ServerPlayer player) {
         ModNetwork.sendTo(new SUnpinCameraPacket(), player);
      }

      this.cooldownComponent.startCooldown(entity, 300.0F + this.continuousComponent.getContinueTime());
   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      if (this.continuousComponent.isContinuous() && this.continuousComponent.isInfinite() && entity.m_21205_().m_41619_()) {
         this.target = target;
         return HitTriggerComponent.HitResult.FAIL;
      } else {
         return HitTriggerComponent.HitResult.PASS;
      }
   }

   private boolean hitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      return true;
   }

   private float onDamageTaken(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (this.continuousComponent.isContinuous() && damage >= 10.0F) {
         this.continuousComponent.stopContinuity(entity);
      }

      return damage;
   }

   private void applyFirstAid(LivingEntity entity, LivingEntity target, FirstAidAbility ability) {
      ItemStack medicBag = entity.m_6844_(EquipmentSlot.CHEST);
      boolean hasMedicBag = !medicBag.m_41619_() && medicBag.m_41720_() == ModArmors.MEDIC_BAG.get();
      if (hasMedicBag) {
         float healAmount = (float)WyHelper.percentage((double)1.0F, (double)target.m_21233_());
         ability.healComponent.healTarget(entity, target, healAmount);
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.FIRST_AID.get(), target, target.m_20185_(), target.m_20186_(), target.m_20189_());
         if (target.m_21223_() >= target.m_21233_()) {
            ability.continuousComponent.stopContinuity(entity);
         }

      }
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-9.0F, -4.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.DOCTOR.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
