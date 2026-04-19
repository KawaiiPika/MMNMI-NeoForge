package xyz.pixelatedw.mineminenomi.abilities.electro;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityAttributeModifier;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
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
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRaces;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class EleclawAbility extends PunchAbility {
   private static final int MAX_STACKS = 9;
   private static final int COOLDOWN = 200;
   private static final int DORIKI = 500;
   public static final RegistryObject<AbilityCore<EleclawAbility>> INSTANCE = ModRegistry.registerAbility("eleclaw", "Eleclaw", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user coats their hands and weapons with lightning, enabling the use of other electric skills and giving the chance to stun foes.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, EleclawAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ChangeStatsComponent.getTooltip(), StackComponent.getTooltip(9)).setSourceHakiNature(SourceHakiNature.IMBUING).setSourceType(SourceType.PHYSICAL).setSourceElement(SourceElement.LIGHTNING).setNodeFactories(EleclawAbility::createNode).build("mineminenomi");
   });
   private static final AbilityAttributeModifier ATTACK_SPEED_MODIFIER;
   private final StackComponent stackComponent = new StackComponent(this, 9);

   public EleclawAbility(AbilityCore<EleclawAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.stackComponent});
      this.statsComponent.addAttributeModifier(Attributes.f_22283_, ATTACK_SPEED_MODIFIER);
      this.continuousComponent.addTickEvent(this::duringContinuityEvent);
      this.continuousComponent.addEndEvent(this::endContinuityEvent);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_ && (entity.isEyeInFluidType((FluidType)ForgeMod.WATER_TYPE.get()) || this.stackComponent.getStacks() == 0)) {
         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.stackComponent.revertStacksToDefault(entity, this);
   }

   public float getPunchDamage() {
      return 5.0F;
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      if (entity.m_217043_().m_188503_(10) < 1) {
         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.PARALYSIS.get(), 10, 0, false, false, true));
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GENERIC_LIGHTNING_USE.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
      }

      return true;
   }

   public int getUseLimit() {
      return -1;
   }

   public float getPunchCooldown() {
      return 200.0F;
   }

   public boolean isParallel() {
      return true;
   }

   public void reduceUsage(LivingEntity entity, int number) {
      if (!entity.m_9236_().f_46443_) {
         SulongAbility sulongAbility = (SulongAbility)AbilityCapability.get(entity).map((props) -> (SulongAbility)props.getEquippedAbility((AbilityCore)SulongAbility.INSTANCE.get())).orElse((Object)null);
         boolean sulongEnabled = sulongAbility != null && sulongAbility.isContinuous();
         if (!sulongEnabled) {
            int newStacks = this.stackComponent.getStacks() - number;
            this.stackComponent.setStacks(entity, this, newStacks);
         }
      }

   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(7.0F, 8.0F));
      NodeUnlockCondition unlockCondition = RequiredRaceUnlockCondition.requires((Race)ModRaces.MINK.get()).and(DorikiUnlockCondition.atLeast((double)500.0F));
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }

   static {
      ATTACK_SPEED_MODIFIER = new AbilityAttributeModifier(UUID.fromString("1d78a133-8a0e-4b8f-8790-1360007d4741"), INSTANCE, "Eleclaw Attack Speed Modifier", (double)0.35F, Operation.ADDITION);
   }
}
