package xyz.pixelatedw.mineminenomi.abilities.doctor;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.PunchAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class AntidoteShotAbility extends PunchAbility {
   private static final int COOLDOWN = 300;
   private static final int EFFECT_TIME = 200;
   public static final RegistryObject<AbilityCore<AntidoteShotAbility>> INSTANCE = ModRegistry.registerAbility("antidote_shot", "Antidote Shot", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Injects the target with an antidote making them immune to certain negative effects.", (Object)null), ImmutablePair.of("§aSHIFT-USE§r: User injects themselves", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, AntidoteShotAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F)).setSourceType(SourceType.FRIENDLY).setNodeFactories(AntidoteShotAbility::createNode).build("mineminenomi");
   });

   public AntidoteShotAbility(AbilityCore<AntidoteShotAbility> core) {
      super(core);
      this.addCanUseCheck(AbilityUseConditions::requiresMedicBag);
      this.continuousComponent.addStartEvent(100, this::startContinuityEvent);
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      if (entity.m_6047_()) {
         this.applyAntidoteEffect(entity, entity);
      }
   }

   public boolean onHitEffect(LivingEntity entity, LivingEntity target, DamageSource source) {
      this.applyAntidoteEffect(entity, target);
      return true;
   }

   private void applyAntidoteEffect(LivingEntity entity, LivingEntity target) {
      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.ANTIDOTE.get(), 200, 0));
      WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.FIRST_AID.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
      this.continuousComponent.stopContinuity(entity);
   }

   public float getPunchCooldown() {
      return 300.0F;
   }

   public int getUseLimit() {
      return 1;
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-9.0F, -2.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.DOCTOR.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
