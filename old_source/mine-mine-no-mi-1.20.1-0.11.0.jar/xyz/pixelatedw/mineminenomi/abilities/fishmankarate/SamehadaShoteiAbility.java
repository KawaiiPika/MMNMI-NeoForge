package xyz.pixelatedw.mineminenomi.abilities.fishmankarate;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SamehadaShoteiAbility extends GuardAbility {
   private static final float HOLD_TIME = 200.0F;
   private static final float INTRERUPT_COOLDOWN = 200.0F;
   private static final float MIN_COOLDOWN = 100.0F;
   private static final float MAX_COOLDOWN = 400.0F;
   private static final float DAMAGE = 15.0F;
   private static final int DORIKI = 3000;
   private static final int MARTIAL_ARTS_POINTS = 50;
   private static final GuardAbility.GuardValue GUARD_VALUE = GuardAbility.GuardValue.percentage(1.0F);
   public static final RegistryObject<AbilityCore<SamehadaShoteiAbility>> INSTANCE = ModRegistry.registerAbility("samehada_shotei", "Samehada Shotei", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user concentrates their power to their palms, sending melee damage back to its owner and pushing them a few blocks back", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, SamehadaShoteiAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 400.0F), ContinuousComponent.getTooltip(200.0F)).setNodeFactories(SamehadaShoteiAbility::createNode).build("mineminenomi");
   });
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);

   public SamehadaShoteiAbility(AbilityCore<SamehadaShoteiAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.animationComponent, this.dealDamageComponent});
      this.continuousComponent.addStartEvent(100, this::onStartContinuityEvent).addTickEvent(100, this::onTickContinuityEvent).addEndEvent(100, this::onEndContinuityEvent);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
   }

   private void onStartContinuityEvent(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.CROSSED_ARMS);
      this.blockMovement(entity);
   }

   private void onTickContinuityEvent(LivingEntity entity, IAbility ability) {
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.SAMEHADA.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
   }

   private void onEndContinuityEvent(LivingEntity entity, IAbility ability) {
      this.removeMovementBlock(entity);
      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 100.0F + this.continuousComponent.getContinueTime() * 2.0F);
   }

   public void onGuard(LivingEntity entity, DamageSource source, float originalDamage, float newDamage) {
      Entity sourceEntity = source.m_7639_();
      if (sourceEntity != null) {
         float damage = Math.max(15.0F, originalDamage / 2.0F);
         if (this.dealDamageComponent.hurtTarget(entity, (LivingEntity)sourceEntity, damage)) {
            Vec3 knockback = entity.m_20182_().m_82546_(sourceEntity.m_20182_()).m_82541_().m_82490_((double)-2.0F);
            sourceEntity.m_20256_(knockback);
            sourceEntity.f_19864_ = true;
         }

         this.continuousComponent.stopContinuity(entity);
         this.cooldownComponent.startCooldown(entity, 200.0F);
      }
   }

   public GuardAbility.GuardValue getGuard(LivingEntity entity) {
      return GUARD_VALUE;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(9.0F, -8.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.FISHMAN.get()).and(DorikiUnlockCondition.atLeast((double)3000.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)KachiageHaisokuAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
