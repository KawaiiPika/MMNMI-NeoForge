package xyz.pixelatedw.mineminenomi.abilities.rokushiki;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.DorikiUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredRaceUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.Race;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModSounds;

public class KamieAbility extends Ability {
   private static final float HOLD_TIME = 60.0F;
   private static final float MIN_COOLDOWN = 100.0F;
   private static final float MAX_COOLDOWN = 400.0F;
   private static final float PROTECTION_TIME = 10.0F;
   private static final int DORIKI = 3600;
   private static final int MARTIAL_ARTS_POINTS = 50;
   public static final RegistryObject<AbilityCore<KamieAbility>> INSTANCE = ModRegistry.registerAbility("kamie", "Kami-E", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Makes the user's body scalable in order to avoid attacks", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, KamieAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(100.0F, 400.0F), ContinuousComponent.getTooltip(60.0F)).setNodeFactories(KamieAbility::createNode).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this)).addStartEvent(this::onStartContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::onEndContinuityEvent);
   private final PoolComponent poolComponent;
   private final DamageTakenComponent damageTakenComponent;
   private final AnimationComponent animationComponent;
   private int hitsTaken;
   private float protTimer;

   public KamieAbility(AbilityCore<KamieAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.DODGE_ABILITY, new AbilityPool[0]);
      this.damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::onDamageTakenEvent);
      this.animationComponent = new AnimationComponent(this);
      this.hitsTaken = 0;
      this.protTimer = 10.0F;
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.poolComponent, this.damageTakenComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, 60.0F);
   }

   private void onStartContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hitsTaken = 0;
      this.protTimer = 0.0F;
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.protTimer > 0.0F) {
         --this.protTimer;
      }

   }

   private void onEndContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, 100.0F + this.continuousComponent.getContinueTime() * 5.0F + 5.0F * (float)Math.pow((double)this.hitsTaken, (double)this.hitsTaken));
   }

   public float onDamageTakenEvent(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (super.isContinuous() && !AbilityUseConditions.canUseMomentumAbilities(entity, this).isFail()) {
         IDamageSourceHandler handler = IDamageSourceHandler.getHandler(damageSource);
         boolean isUnavoidable = handler.isUnavoidable();
         boolean isExplosionOrShockwave = handler.hasElement(SourceElement.EXPLOSION) || handler.hasElement(SourceElement.SHOCKWAVE);
         if (!isUnavoidable && !isExplosionOrShockwave) {
            boolean isDamageTaken = true;
            if (!damageSource.m_276093_(DamageTypes.f_268566_) && !damageSource.m_276093_(DamageTypes.f_268464_) && !damageSource.m_269533_(DamageTypeTags.f_268524_)) {
               if (handler.hasType(SourceType.BLUNT) || handler.hasType(SourceType.SLASH) || handler.hasType(SourceType.FIST) || handler.hasType(SourceType.PHYSICAL) || handler.hasType(SourceType.PROJECTILE) || handler.hasType(SourceType.INDIRECT) || handler.hasType(SourceType.BULLET)) {
                  isDamageTaken = false;
               }
            } else {
               isDamageTaken = false;
            }

            if (this.protTimer <= 0.0F) {
               if (!isDamageTaken) {
                  SoundEvent sfx = (SoundEvent)ModSounds.DODGE_1.get();
                  if (entity.m_217043_().m_188499_()) {
                     sfx = (SoundEvent)ModSounds.DODGE_2.get();
                  }

                  this.animationComponent.start(entity, ModAnimations.DODGE, 5);
                  entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), sfx, SoundSource.PLAYERS, 1.0F, 0.75F + entity.m_217043_().m_188501_() / 2.0F);
                  ++this.hitsTaken;
                  this.protTimer = 10.0F;
               }

               return isDamageTaken ? damage : 0.0F;
            } else {
               return 0.0F;
            }
         } else {
            return damage;
         }
      } else {
         return damage;
      }
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode kamie = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(8.0F, 2.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.HUMAN.get()).and(DorikiUnlockCondition.atLeast((double)3600.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      kamie.setUnlockRule(unlockCondition, unlockAction);
      return kamie;
   }
}
