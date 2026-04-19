package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
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
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class PartyTableKickCourseAbility extends Ability {
   private static final float COOLDOWN = 240.0F;
   private static final float RANGE = 2.5F;
   private static final float DAMAGE = 25.0F;
   public static final RegistryObject<AbilityCore<PartyTableKickCourseAbility>> INSTANCE = ModRegistry.registerAbility("party_table_kick_course", "Party-Table Kick Course", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user does a hand stand on the ground, legs spread out spinning and dealing damage to all nearby enemies", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, PartyTableKickCourseAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), DealDamageComponent.getTooltip(25.0F), RangeComponent.getTooltip(2.5F, RangeComponent.RangeType.AOE)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setNodeFactories(PartyTableKickCourseAbility::createNode).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::onStartContinuityEvent).addEndEvent(this::onStopContinuityEvent).addTickEvent(this::duringContinuityEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);

   public PartyTableKickCourseAbility(AbilityCore<PartyTableKickCourseAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.animationComponent, this.dealDamageComponent, this.rangeComponent});
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 20.0F);
   }

   private boolean onStartContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.HAND_STAND_SPIN);
      return true;
   }

   private boolean onStopContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.stop(entity);
      super.cooldownComponent.startCooldown(entity, 240.0F);
      return true;
   }

   private boolean duringContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1));
      List<LivingEntity> list = this.rangeComponent.getTargetsInArea(entity, 2.5F);

      for(LivingEntity target : list) {
         if (this.dealDamageComponent.hurtTarget(entity, target, 25.0F)) {
            Vec3 speed = entity.m_20154_().m_82542_((double)1.5F, (double)1.0F, (double)1.5F);
            AbilityHelper.setDeltaMovement(target, speed.f_82479_, (double)1.5F, speed.f_82481_);
         }
      }

      if (!entity.m_9236_().f_46443_ && list.size() > 0) {
         ((ServerLevel)entity.m_9236_()).m_7726_().m_8394_(entity, new ClientboundAnimatePacket(entity, 0));
      }

      boolean isAbilityEnabled = (Boolean)AbilityCapability.get(entity).map((props) -> (DiableJambeAbility)props.getEquippedAbility((AbilityCore)DiableJambeAbility.INSTANCE.get())).map((abl) -> abl.isContinuous()).orElse(false);
      if (isAbilityEnabled) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.PARTY_TABLE_KICK.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

      return true;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-7.0F, 10.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.BLACK_LEG.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)AntiMannerKickCourseAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
