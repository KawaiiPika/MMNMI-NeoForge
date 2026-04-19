package xyz.pixelatedw.mineminenomi.abilities.blackleg;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
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
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class BienCuitGrillShotAbility extends Ability {
   private static final float COOLDOWN = 400.0F;
   private static final float RANGE = 1.8F;
   private static final float DAMAGE = 30.0F;
   public static final RegistryObject<AbilityCore<BienCuitGrillShotAbility>> INSTANCE = ModRegistry.registerAbility("bien_cuit_grill_shot", "Bien Cuit: Grill Shot", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("A strong kick that launches the user forwards and creates a grill-patterened particle to appear, which sets anyone touching it on fire", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, BienCuitGrillShotAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(400.0F), RangeComponent.getTooltip(1.8F, RangeComponent.RangeType.AOE), DealDamageComponent.getTooltip(30.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setNodeFactories(BienCuitGrillShotAbility::createNode).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final RangeComponent rangeComponent = new RangeComponent(this);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(100, this::startContinuityEvent).addTickEvent(this::continuousTickEvent).addEndEvent(this::continuousEndsEvent);

   public BienCuitGrillShotAbility(AbilityCore<BienCuitGrillShotAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent, this.rangeComponent, this.hitTrackerComponent, this.continuousComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addCanUseCheck(this::hasDiableJambeActive);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity, 10.0F);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      Vec3 speed = entity.m_20154_().m_82542_((double)3.0F, (double)0.0F, (double)3.0F);
      AbilityHelper.setDeltaMovement(entity, speed.f_82479_, 0.3, speed.f_82481_);
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.BIEN_CUIT_GRILL_SHOT.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
      }

   }

   private void continuousTickEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6084_()) {
         List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, 1.8F);
         targets.remove(entity);
         Vec3 pushSpeed = entity.m_20154_().m_82542_((double)2.0F, (double)0.0F, (double)2.0F);

         for(LivingEntity target : targets) {
            if (this.hitTrackerComponent.canHit(target) && this.dealDamageComponent.hurtTarget(entity, target, 30.0F)) {
               AbilityHelper.setDeltaMovement(target, pushSpeed.f_82479_, 0.2, pushSpeed.f_82481_);
               target.m_20254_(2);
            }
         }
      }

   }

   private void continuousEndsEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 400.0F);
   }

   public Result hasDiableJambeActive(LivingEntity entity, IAbility ability) {
      IAbilityData props = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
      if (props == null) {
         return Result.fail((Component)null);
      } else {
         DiableJambeAbility diableJambeAbility = (DiableJambeAbility)props.getEquippedAbility((AbilityCore)DiableJambeAbility.INSTANCE.get());
         return diableJambeAbility != null && diableJambeAbility.isContinuous() ? Result.success() : Result.fail(ModI18nAbilities.MESSAGE_NEED_DIABLE_JAMBE);
      }
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-7.0F, 14.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.BLACK_LEG.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)DiableJambeAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
