package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.DropHitAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class ConcasseAbility extends DropHitAbility {
   private static final int COOLDOWN = 300;
   private static final float RANGE = 1.7F;
   private static final float DAMAGE = 15.0F;
   public static final RegistryObject<AbilityCore<ConcasseAbility>> INSTANCE = ModRegistry.registerAbility("concasse", "Concasse", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Leaps forward kicking all nearby enemies for moderate damage and knocking them down", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, ConcasseAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), DealDamageComponent.getTooltip(15.0F), RangeComponent.getTooltip(1.7F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setNodeFactories(ConcasseAbility::createNode).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private Interval particleInterval = new Interval(2);

   public ConcasseAbility(AbilityCore<ConcasseAbility> core) {
      super(core);
      super.addComponents(new AbilityComponent[]{this.dealDamageComponent, this.rangeComponent, this.animationComponent});
      super.continuousComponent.addStartEvent(100, this::startContinuityEvent);
      super.continuousComponent.addEndEvent(100, this::onContinuityEnd);
      super.continuousComponent.addTickEvent(100, this::tickContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      super.hitTrackerComponent.clearHits();
      this.particleInterval.restartIntervalToZero();
      Vec3 speed = entity.m_20154_();
      AbilityHelper.setDeltaMovement(entity, speed.f_82479_, 1.3, speed.f_82481_);
      this.animationComponent.start(entity, ModAnimations.PITCH_SPIN);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (entity instanceof Player) {
            Player player = (Player)entity;
            if (player.m_150110_().f_35935_) {
               this.continuousComponent.stopContinuity(entity);
               return;
            }
         }

         if (entity.f_19789_ > 0.0F) {
            boolean targetHurt = false;
            List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 1.7F);
            targets.remove(entity);

            for(LivingEntity target : targets) {
               if (super.hitTrackerComponent.canHit(target) && entity.m_20183_().m_123342_() > target.m_20183_().m_123342_() && this.dealDamageComponent.hurtTarget(entity, target, 15.0F)) {
                  target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.UNCONSCIOUS.get(), 20, 0, false, false));
                  AbilityHelper.setDeltaMovement(target, entity.m_20184_().f_82479_, (double)-1.5F, entity.m_20184_().f_82481_);
                  targetHurt = true;
               }
            }

            if (targetHurt) {
               if (!entity.m_9236_().f_46443_) {
                  ((ServerLevel)entity.m_9236_()).m_7726_().m_8394_(entity, new ClientboundAnimatePacket(entity, 0));
               }

               super.continuousComponent.stopContinuity(entity);
            }
         }

         if (this.particleInterval.canTick()) {
            boolean isAbilityEnabled = (Boolean)AbilityCapability.get(entity).map((props) -> (DiableJambeAbility)props.getEquippedAbility((AbilityCore)DiableJambeAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
            if (isAbilityEnabled && !entity.m_20096_()) {
               WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.CONCASSE_DIABLE.get(), entity, entity.m_20185_(), entity.m_20186_() + (double)1.5F, entity.m_20189_());
            }
         }

      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      super.cooldownComponent.startCooldown(entity, 300.0F);
   }

   public void onLanding(LivingEntity entity) {
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-11.0F, 10.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.BLACK_LEG.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)AntiMannerKickCourseAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
